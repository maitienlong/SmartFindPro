package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.poly.smartfindpro.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
public class CameraView extends FrameLayout {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final double ASPECT_RATIO_TOLERANCE = 0.005d;
    public static final int CAMERA_TYPE_BACK = 1;
    public static final int CAMERA_TYPE_FRONT = 0;
    public static final int MODE_CAPTURE = 1;
    public static final int MODE_PREVIEW = 0;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final int STATE_PICTURE_TAKEN = 4;
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAITING_LOCK = 1;
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;
    private static final int STATE_WAITING_PRECAPTURE = 2;
    public int cameraMaxBufferHeight = 480;
    public int cameraMaxBufferWidth = 640;
    private int cameraMaxPreviewHeight = 1080;
    private int cameraMaxPreviewWidth = 1920;
    public int cameraType = 1;
    public int imageFormat = 256;
    private boolean isHighQuality = false;
    public boolean isStretchToFill = false;
    private boolean mAutoFocusSupported;
    /* access modifiers changed from: private */
    public Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    /* access modifiers changed from: private */
    public CameraViewEventListener mBufferListener;
    /* access modifiers changed from: private */
    public CameraDevice mCameraDevice;
    /* access modifiers changed from: private */
    public String mCameraId;
    private int mCameraMode = 0;
    /* access modifiers changed from: private */
    public Semaphore mCameraOpenCloseLock = new Semaphore(1);
    private Size mCameraSize;
    /* access modifiers changed from: private */
    public final Object mCameraStateLock = new Object();
    /* access modifiers changed from: private */
    public CaptureCallback mCaptureCallback = new CaptureCallback() {
        private void process(CaptureResult result) {
            switch (CameraView.this.mState) {
                case 1:
                    Integer afState = (Integer) result.get(CaptureResult.CONTROL_AF_STATE);
                    if (afState == null) {
                        CameraView.this.captureStillPicture();
                        return;
                    } else if (CameraView.STATE_PICTURE_TAKEN == afState.intValue() || 5 == afState.intValue() || afState.intValue() == 0) {
                        Integer aeState = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
                        if (aeState == null || aeState.intValue() == CameraView.STATE_WAITING_PRECAPTURE) {
                            CameraView.this.mState = CameraView.STATE_PICTURE_TAKEN;
                            CameraView.this.captureStillPicture();
                            return;
                        }
                        CameraView.this.runPrecaptureSequence();
                        return;
                    } else {
                        return;
                    }
                case CameraView.STATE_WAITING_PRECAPTURE /*2*/:
                    Integer aeState2 = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState2 == null || aeState2.intValue() == 5 || aeState2.intValue() == CameraView.STATE_PICTURE_TAKEN) {
                        CameraView.this.mState = CameraView.STATE_WAITING_NON_PRECAPTURE;
                        return;
                    }
                    return;
                case CameraView.STATE_WAITING_NON_PRECAPTURE /*3*/:
                    Integer aeState3 = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState3 == null || aeState3.intValue() != 5) {
                        CameraView.this.mState = CameraView.STATE_PICTURE_TAKEN;
                        CameraView.this.capture();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            process(result);
        }
    };
    private final OnImageAvailableListener mCaptureListener = new OnImageAvailableListener() {
        public void onImageAvailable(ImageReader reader) {
            Image image = reader.acquireLatestImage();
            CameraView.this.saveImage(image);
            image.close();
        }
    };
    private ImageReader mCaptureReader;
    /* access modifiers changed from: private */
    public CameraCaptureSession mCaptureSession;
    private CameraCharacteristics mCharacteristics;
    private boolean mFlashSupported;
    private final OnImageAvailableListener mImageListener = new OnImageAvailableListener() {
        public void onImageAvailable(ImageReader reader) {
            Image image = reader.acquireLatestImage();
            if (CameraView.this.mBufferListener != null && image != null) {
                CameraView.this.mBufferListener.onBufferReady(image);
                image.close();
            }
        }
    };
    private ImageReader mImageReader;
    /* access modifiers changed from: private */
    public CaptureRequest mPreviewRequest;
    /* access modifiers changed from: private */
    public Builder mPreviewRequestBuilder;
    /* access modifiers changed from: private */
    public Size mPreviewSize;
    private int mSensorOrientation;
    /* access modifiers changed from: private */
    public int mState;
    /* access modifiers changed from: private */
    public final StateCallback mStateCallback = new StateCallback() {
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            CameraView.this.mCameraOpenCloseLock.release();
            CameraView.this.mCameraDevice = cameraDevice;
            CameraView.this.createCameraPreviewSession();
        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            CameraView.this.mCameraOpenCloseLock.release();
            cameraDevice.close();
            CameraView.this.mCameraDevice = null;
        }

        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            CameraView.this.mCameraOpenCloseLock.release();
            cameraDevice.close();
            CameraView.this.mCameraDevice = null;
        }
    };
    private final SurfaceTextureListener mSurfaceTextureListener = new SurfaceTextureListener() {
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            CameraView.this.openCamera(width, height);
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            CameraView.this.configureTransform(width, height);
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            synchronized (CameraView.this.mCameraStateLock) {
                CameraView.this.mPreviewSize = null;
            }
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }
    };
    /* access modifiers changed from: private */
    public PreviewView mTextureView;
    private Handler openCameraHandler;
    private Runnable openCameraRunnable;

    static {
        boolean z;
        if (!CameraView.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        $assertionsDisabled = z;
        ORIENTATIONS.append(0, 90);
        ORIENTATIONS.append(1, 0);
        ORIENTATIONS.append(STATE_WAITING_PRECAPTURE, 270);
        ORIENTATIONS.append(STATE_WAITING_NON_PRECAPTURE, 180);
    }

    public void setCameraMode(int cameraMode) {
        this.mCameraMode = cameraMode;
    }

    public CameraView(Context context) {
        super(context);
        init(null, 0);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.camera_view, this);
        this.mTextureView = (PreviewView) findViewById(R.id.surfaceView);
    }

    public void startCamera() {
        startBackgroundThread();
        if (this.mTextureView.isAvailable()) {
            openCamera(this.mTextureView.getWidth(), this.mTextureView.getHeight());
        } else {
            this.mTextureView.setSurfaceTextureListener(this.mSurfaceTextureListener);
        }
    }

    public void stopCamera() {
        closeCamera();
        stopBackgroundThread();
    }

    public Size getCameraSize() {
        return new Size(this.mTextureView.getMeasuredWidth(), this.mTextureView.getMeasuredHeight());
    }

    public void pauseCamera() {
    }

    public void resumeCamera() {
        this.mState = 0;
    }

    public void setHighQuality(boolean highQuality) {
        this.isHighQuality = highQuality;
    }

    public void setBufferListener(CameraViewEventListener bufferListener) {
        this.mBufferListener = bufferListener;
    }

    public void switchMode(int mode) {
        this.cameraType = mode;
        stopCamera();
        startCamera();
    }

    /* access modifiers changed from: private */
    public void saveImage(Image image) {
        if (this.mBufferListener != null) {
            this.mBufferListener.onCapturedImage(TVSDKUtil.convertImageToBitmap(image));
        }
    }

    /* access modifiers changed from: private */
    public void createCameraPreviewSession() {
        try {
            this.mTextureView.setStretchToFill(this.isStretchToFill);
            SurfaceTexture texture = this.mTextureView.getSurfaceTexture();
            if ($assertionsDisabled || texture != null) {
                texture.setDefaultBufferSize(this.mPreviewSize.getWidth(), this.mPreviewSize.getHeight());
                Surface surface = new Surface(texture);
                this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                this.mPreviewRequestBuilder.addTarget(surface);
                ArrayList<Surface> listSurfaces = new ArrayList<>();
                listSurfaces.add(surface);
                if (this.mCameraMode == 0) {
                    this.mPreviewRequestBuilder.addTarget(this.mImageReader.getSurface());
                    listSurfaces.add(this.mImageReader.getSurface());
                } else {
                    listSurfaces.add(this.mCaptureReader.getSurface());
                }
                this.mCameraDevice.createCaptureSession(listSurfaces, new CameraCaptureSession.StateCallback() {
                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                        if (CameraView.this.mCameraDevice != null) {
                            CameraView.this.mCaptureSession = cameraCaptureSession;
                            try {
                                CameraView.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(CameraView.STATE_PICTURE_TAKEN));
                                CameraView.this.mPreviewRequest = CameraView.this.mPreviewRequestBuilder.build();
                                CameraView.this.mCaptureSession.setRepeatingRequest(CameraView.this.mPreviewRequest, CameraView.this.mCaptureCallback, CameraView.this.mBackgroundHandler);
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (CameraView.this.mBufferListener != null) {
                                    CameraView.this.mBufferListener.onCameraError(e.getLocalizedMessage());
                                }
                            }
                        }
                    }

                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                        CameraView.this.handleSetupFailure();
                    }
                }, null);
                return;
            }
            throw new AssertionError();
        } catch (Exception e) {
            e.printStackTrace();
            if (this.mBufferListener != null) {
                this.mBufferListener.onCameraError(e.getLocalizedMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleSetupFailure() {
        Activity activity = (Activity) getContext();
        Toast.makeText(activity, "Không thể thiết lập máy ảnh", Toast.LENGTH_SHORT).show();
        activity.finish();
    }

    /* access modifiers changed from: private */
    public void unlockFocus() {
        try {
            if (this.mCaptureSession != null) {
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(STATE_WAITING_PRECAPTURE));
                this.mPreviewRequest = this.mPreviewRequestBuilder.build();
                this.mCaptureSession.setRepeatingRequest(this.mPreviewRequest, null, this.mBackgroundHandler);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (this.mBufferListener != null) {
                this.mBufferListener.onCameraError(e.getLocalizedMessage());
            }
        }
    }

    @SuppressLint("WrongConstant")
    private void setUpCameraOutputs(int width, int height) {
        Activity activity = (Activity) this.getContext();
        this.mTextureView.setStretchToFill(this.isStretchToFill);
        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);

        try {
            String[] var5 = manager.getCameraIdList();
            int var6 = var5.length;

            for (String cameraId : var5) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                Integer facing;
                if (this.cameraType == 0) {
                    facing = (Integer) characteristics.get(CameraCharacteristics.LENS_FACING);
                    if (facing == null || facing != 0) {
                        continue;
                    }
                } else {
                    facing = (Integer) characteristics.get(CameraCharacteristics.LENS_FACING);
                    if (facing != null && facing == 0) {
                        continue;
                    }
                }

                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map != null) {
                    Size largest = Collections.max(Arrays.asList(map.getOutputSizes(this.imageFormat)), new CompareSizesByArea());
                    int displayRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                    mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    boolean swappedDimensions = false;
                    switch (displayRotation) {
                        case Surface.ROTATION_0:
                        case Surface.ROTATION_180:
                            if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                                swappedDimensions = true;
                            }
                            break;
                        case Surface.ROTATION_90:
                        case Surface.ROTATION_270:
                            if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                                swappedDimensions = true;
                            }
                    }

                    Point displaySize = new Point();
                    activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
                    int rotatedPreviewWidth = width;
                    int rotatedPreviewHeight = height;
                    int maxPreviewWidth = displaySize.x;
                    int maxPreviewHeight = displaySize.y;
                    if (swappedDimensions) {
                        rotatedPreviewWidth = height;
                        rotatedPreviewHeight = width;
                        maxPreviewWidth = displaySize.y;
                        maxPreviewHeight = displaySize.x;
                    }

                    if (maxPreviewWidth > this.cameraMaxPreviewWidth) {
                        maxPreviewWidth = this.cameraMaxPreviewWidth;
                    }

                    if (maxPreviewHeight > this.cameraMaxPreviewHeight) {
                        maxPreviewHeight = this.cameraMaxPreviewHeight;
                    }

                    this.mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth, maxPreviewHeight, largest);
                    Size readerSize = this.mPreviewSize;
                    if (this.mPreviewSize.getWidth() > this.cameraMaxBufferWidth || this.mPreviewSize.getHeight() > this.cameraMaxBufferHeight) {
                        readerSize = new Size(this.cameraMaxBufferWidth, this.cameraMaxBufferHeight);
                    }
                    this.mImageReader = ImageReader.newInstance(readerSize.getWidth(), readerSize.getHeight(), this.imageFormat, 2);
                    this.mImageReader.setOnImageAvailableListener(this.mImageListener, this.mBackgroundHandler);
                    this.mCaptureReader = ImageReader.newInstance(readerSize.getWidth(), readerSize.getHeight(), this.imageFormat, 2);
                    this.mCaptureReader.setOnImageAvailableListener(this.mCaptureListener, this.mBackgroundHandler);
                    int orientation = this.getResources().getConfiguration().orientation;
                    if (orientation == 2) {
                        this.mTextureView.setAspectRatio(this.mPreviewSize.getWidth(), this.mPreviewSize.getHeight());
                    } else {
                        this.mTextureView.setAspectRatio(this.mPreviewSize.getHeight(), this.mPreviewSize.getWidth());
                    }

                    int[] afAvailableModes = (int[]) characteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
                    if (afAvailableModes.length != 0 && (afAvailableModes.length != 1 || afAvailableModes[0] != 0)) {
                        this.mAutoFocusSupported = true;
                    } else {
                        this.mAutoFocusSupported = false;
                    }

                    Boolean available = (Boolean) characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    this.mFlashSupported = available == null ? false : available;
                    this.mCameraId = cameraId;
                    if (this.mBufferListener != null) {
                        this.mBufferListener.onPreviewReady();
                    }

                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    private void openCamera(int width, int height) {
        this.setUpCameraOutputs(width, height);
        this.configureTransform(width, height);
        final Context activity = this.getContext();
        this.openCameraHandler = new Handler();
        this.openCameraRunnable = new Runnable() {
            public void run() {
                CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);

                try {
                    if (!CameraView.this.mCameraOpenCloseLock.tryAcquire(2500L, TimeUnit.MILLISECONDS)) {
                        throw new RuntimeException("Time out waiting to lock camera opening.");
                    }
                    if (manager != null) {
                        manager.openCamera(CameraView.this.mCameraId, CameraView.this.mStateCallback, CameraView.this.mBackgroundHandler);
                    } else {
                        throw new RuntimeException("CameraManager is null.");
                    }
                } catch (CameraAccessException var3) {
                    var3.printStackTrace();
                } catch (InterruptedException var4) {
                    throw new RuntimeException("Interrupted while trying to lock camera opening.", var4);
                }

            }
        };
        this.openCameraHandler.postDelayed(this.openCameraRunnable, 100L);
    }

    private void closeCamera() {
        if (this.mCameraDevice != null) {
            try {
                this.mCameraOpenCloseLock.acquire();
                if (null != this.mCaptureSession) {
                    this.mCaptureSession.close();
                    this.mCaptureSession = null;
                }

                if (null != this.mCameraDevice) {
                    this.mCameraDevice.close();
                    this.mCameraDevice = null;
                }

                if (null != this.mImageReader) {
                    this.mImageReader.close();
                    this.mImageReader = null;
                }

                if (null != this.mCaptureReader) {
                    this.mCaptureReader.close();
                    this.mCaptureReader = null;
                }
            } catch (InterruptedException var5) {
                throw new RuntimeException("Interrupted while trying to lock camera closing.", var5);
            } finally {
                this.mCameraOpenCloseLock.release();
            }
        }

    }

    private void startBackgroundThread() {
        this.mBackgroundThread = new HandlerThread("CameraBackground");
        this.mBackgroundThread.start();
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        if (this.mBackgroundHandler != null) {
            this.mBackgroundThread.quitSafely();
            try {
                this.mBackgroundThread.join();
                this.mBackgroundThread = null;
                this.mBackgroundHandler = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.openCameraHandler != null && this.openCameraRunnable != null) {
            this.openCameraHandler.removeCallbacks(this.openCameraRunnable);
        }
    }

    public void takePicture() {
        if (this.mCameraMode == 0) {
            capture();
        } else {
            captureStillPicture();
        }
    }

    /* access modifiers changed from: private */
    public void capture() {
        this.mBackgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (CameraView.this.mCameraStateLock) {
                    if (CameraView.this.mPreviewSize != null && CameraView.this.mTextureView.isAvailable()) {
                        Bitmap bitmap = CameraView.this.mTextureView.getBitmap(CameraView.this.cameraMaxBufferWidth, (int) (((float) (CameraView.this.cameraMaxBufferWidth * CameraView.this.mPreviewSize.getWidth())) / ((float) CameraView.this.mPreviewSize.getHeight())));
                        if (!(bitmap == null || CameraView.this.mBufferListener == null)) {
                            CameraView.this.mBufferListener.onCapturedImage(bitmap);
                        }
                    }
                }
            }
        });
    }

    private void lockFocus() {
        try {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(1));
            this.mState = 1;
            this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, this.mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            if (this.mBufferListener != null) {
                this.mBufferListener.onCameraError(e.getLocalizedMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public void runPrecaptureSequence() {
        try {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(1));
            this.mState = STATE_WAITING_PRECAPTURE;
            this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, this.mBackgroundHandler);
        } catch (Exception e) {
            e.printStackTrace();
            if (this.mBufferListener != null) {
                this.mBufferListener.onCameraError(e.getLocalizedMessage());
            }
        }
    }

    private int getOrientation(int rotation) {
        return ((ORIENTATIONS.get(rotation) + this.mSensorOrientation) + 270) % 360;
    }

    /* access modifiers changed from: private */
    public void captureStillPicture() {
        try {
            if (getContext() != null && this.mCameraDevice != null) {
                Builder captureBuilder = this.mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, getFPSRange());
                captureBuilder.addTarget(this.mCaptureReader.getSurface());
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getOrientation(((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation()));
                captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, STATE_PICTURE_TAKEN);
                setAutoFlash(captureBuilder);
                CaptureCallback CaptureCallback = new CaptureCallback() {
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                        CameraView.this.unlockFocus();
                    }
                };
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                    mCaptureSession.stopRepeating();
                    mCaptureSession.abortCaptures();
                }
                mCaptureSession.capture(captureBuilder.build(), CaptureCallback, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (this.mBufferListener != null) {
                this.mBufferListener.onCameraError(e.getLocalizedMessage());
            }
        }
    }

    private void setAutoFlash(Builder requestBuilder) {
        if (this.mFlashSupported) {
            requestBuilder.set(CaptureRequest.CONTROL_AE_MODE, STATE_WAITING_PRECAPTURE);
        }
    }

    private double exposureIncrease(double exposureAdjustment) {
        try {
            Range range = (Range) ((CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE)).getCameraCharacteristics(this.mCameraId).get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);
            if (range == null) {
                //Log.e(FaceSDKConstants.LOG_TAG, "Failed to get exposure ranges.");
                return 0.0d;
            }
            int minExposure = (Integer) range.getLower();
            int maxExposure = (Integer) range.getUpper();
            if (minExposure == 0 && maxExposure == 0) {
                return 0.0d;
            }
            if (exposureAdjustment >= 0.0d) {
                return ((double) minExposure) * exposureAdjustment;
            }
            return ((double) (maxExposure * -1)) * exposureAdjustment;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    private Range<Integer> getFPSRange() {
        try {
            Range[] rangeArr = (Range[]) ((CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE)).getCameraCharacteristics(this.mCameraId).get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            Range range = null;
            for (Range range2 : rangeArr) {
                int upper = ((Integer) range2.getUpper()).intValue();
                if (upper >= 10 && (range == null || upper < ((Integer) range.getUpper()).intValue())) {
                    range = range2;
                }
                Log.e("Avaliable frame fps :", "" + range2);
            }
            if (range == null) {
                range = rangeArr[0];
            }
            Log.e("frame fps :", "" + range);
            return range;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void configureTransform(int viewWidth, int viewHeight) {
        Activity activity = (Activity) getContext();
        if (this.mTextureView != null && this.mPreviewSize != null && activity != null) {
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            Matrix matrix = new Matrix();
            RectF viewRect = new RectF(0.0f, 0.0f, (float) viewWidth, (float) viewHeight);
            RectF bufferRect = new RectF(0.0f, 0.0f, (float) this.mPreviewSize.getHeight(), (float) this.mPreviewSize.getWidth());
            float centerX = viewRect.centerX();
            float centerY = viewRect.centerY();
            if (1 == rotation || STATE_WAITING_NON_PRECAPTURE == rotation) {
                bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
                matrix.setRectToRect(viewRect, bufferRect, ScaleToFit.FILL);
                float scale = Math.max(((float) viewHeight) / ((float) this.mPreviewSize.getHeight()), ((float) viewWidth) / ((float) this.mPreviewSize.getWidth()));
                matrix.postScale(scale, scale, centerX, centerY);
                matrix.postRotate((float) ((rotation - 2) * 90), centerX, centerY);
            } else if (STATE_WAITING_PRECAPTURE == rotation) {
                matrix.postRotate(180.0f, centerX, centerY);
            }
            this.mTextureView.setTransform(matrix);
        }
    }

    private static boolean checkAspectsEqual(Size a, Size b) {
        return Math.abs((((double) a.getWidth()) / ((double) a.getHeight())) - (((double) b.getWidth()) / ((double) b.getHeight()))) <= ASPECT_RATIO_TOLERANCE;
    }

    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {
        ArrayList bigEnough = new ArrayList();
        ArrayList notBigEnough = new ArrayList();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight && option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth && option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        if (bigEnough.size() > 0) {
            return (Size) Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return (Size) Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            return choices[0];
        }
    }
}
