package com.poly.smartfindpro.ui.identification.veriface;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;

import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.identification.RequestIndentifi;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.databinding.ActivityFaceDetectorBinding;
import com.poly.smartfindpro.ui.identification.veriface.internal.ActionFace;
import com.poly.smartfindpro.ui.identification.veriface.internal.CameraView;
import com.poly.smartfindpro.ui.identification.veriface.internal.CameraViewEventListener;
import com.poly.smartfindpro.ui.identification.veriface.internal.CircleProgressBar;
import com.poly.smartfindpro.ui.identification.veriface.internal.FaceDetectionHelper;
import com.poly.smartfindpro.ui.identification.veriface.internal.FaceDetectionType;
import com.poly.smartfindpro.ui.identification.veriface.internal.LivenessDetector;
import com.poly.smartfindpro.ui.identification.veriface.internal.LivenessDetectorListener;
import com.poly.smartfindpro.ui.identification.veriface.internal.SDKConfiguration;
import com.poly.smartfindpro.ui.identification.veriface.internal.SelfieConfiguration;
import com.poly.smartfindpro.ui.identification.veriface.internal.SoundPlayer;
import com.poly.smartfindpro.ui.identification.veriface.internal.TVSelfieImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FaceDetectorActivity extends BaseDataBindActivity<ActivityFaceDetectorBinding, FaceDetectorPresenter> implements FaceDetectorContract.ViewModel {

    private String actionFace = "Y";
    private String liveness = "N";
    private final int REQ_CODE_CAMERA_PERMISSION = 100;
    private final int REQ_CODE_FACE_PAY = 200;

    private RequestIndentifi mProduct;

    private List<Bitmap> mImageIdentifi;

    private TextView mPromptText;

    private TextView mTVCooldown;

    private ImageView mCameraMaskImageView;

    private ImageView mCameraBGImageView;

    private CameraView mCameraView;

    private ImageView mCaptureButton;

    private RelativeLayout mLivenessLayout;

    private CircleProgressBar mCircleProgressBar;

    private CircleProgressBar.CircleProgressBarAnimation mProgressAnimation;

    private boolean isHandleStart;

    private boolean isFinished;

    private boolean isCapturing;

    private SoundPlayer mSoundPlayer;

    private CountDownTimer mTimer;

    private CountDownTimer mLivenessTimer;

    private ArrayList<TVSelfieImage> mListBitmap = new ArrayList<>();

    private ArrayList<TVSelfieImage> selfieImages;

    private LivenessDetector mLivenessDetector;

    private FaceDetectionHelper mLivenessUIHelper;

    private int mCameraMode;

    private int numOfFrame;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private SDKConfiguration mConfiguration;

    private final LivenessDetectorListener mFaceDetectorListener = new LivenessDetectorListener() {
        public void onDetectionFailed(final String message) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (!isHandleStart) {
                        mCameraBGImageView.setImageDrawable(getResources().getDrawable(R.mipmap.bg_camera_mask_red));
                        mCameraMaskImageView.setImageDrawable(getResources().getDrawable(R.mipmap.bg_face_border_red));
                        mPromptText.setText(message);
                        mCaptureButton.setEnabled(false);
                    } else if (mConfiguration.getLivenessMode() == SDKConfiguration.LivenessMode.ACTIVE) {
                        mLivenessUIHelper.setLivenessError(message);
                    }

                }
            });
        }

        @Override
        public void onMovedToNextStep(final FaceDetectionType currentStep, final FaceDetectionType nexStep, final Bitmap bitmap) {
            if (!isFinished && mConfiguration.getLivenessMode() == SDKConfiguration.LivenessMode.ACTIVE) {
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSoundPlayer.doPlay(R.raw.weldone);
                        stopTimeoutTimer();
                        mLivenessUIHelper.finishCurrentStep(bitmap);
                        mListBitmap.add(new TVSelfieImage(currentStep.getFaceAction(), bitmap));
                    }
                });
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSoundPlayer.doPlay(mLivenessUIHelper.getSoundID(nexStep));
                        mLivenessDetector.changeDetectionType(nexStep);
                        startTimeoutTimer((long) nexStep.getTimeInterval());
                    }
                }, 1000L);
            }
        }

        public void onDetectionSuccess(final Bitmap bitmap) {
            isFinished = true;
            stopTimeoutTimer();
            mTVCooldown.setText("");
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPromptText.setText("");
                    mListBitmap.add(new TVSelfieImage(ActionFace.portrait.name(), bitmap));
                    mLivenessUIHelper.finishCurrentStep(bitmap);
                    startCapture();
                }
            }, 1000L);
        }

        public void onFaceDetected(Bitmap faceBitmap) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (mConfiguration.getLivenessMode() != SDKConfiguration.LivenessMode.ACTIVE) {
                        if (!isFinished) {
                            mPromptText.setText("Chụp ngay");
                            mCameraBGImageView.setImageDrawable(getResources().getDrawable(R.mipmap.bg_camera_mask));
                            mCameraMaskImageView.setImageDrawable(getResources().getDrawable(R.mipmap.bg_face_border_blue));
                            mCaptureButton.setEnabled(true);
                        }
                    } else if (!isHandleStart) {
                        mPromptText.setText("");
                        mSoundPlayer.doPlay(R.raw.weldone);
                        isHandleStart = true;
                        mCameraBGImageView.setImageDrawable(getResources().getDrawable(R.mipmap.bg_camera_mask));
                        mCameraMaskImageView.setImageDrawable(getResources().getDrawable(R.mipmap.bg_face_border_blue));
                        startActiveLiveness();
                    } else {
                        mLivenessUIHelper.setLivenessError("");
                    }


                }
            });
        }

        @Override
        public void onResetStep() {
            reset();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_detector;
    }

    @Override
    protected void initView() {
        ReceiveData();
        FirebaseApp.initializeApp(this);
        mPromptText = mBinding.livenessLayoutPromptText;
        mTVCooldown = mBinding.tvCooldown;
        mCameraMaskImageView = mBinding.cameraMask;
        mCameraBGImageView = mBinding.cameraMaskBg;
        mCameraView = mBinding.cameraView;
        mCaptureButton = mBinding.cameraButton;
        mLivenessLayout = mBinding.livenessLayoutGesture;
        mCircleProgressBar = mBinding.livenessProgress;

        mConfiguration = new SDKConfiguration(new SelfieConfiguration.Builder().setEnableSound(true)
                .setLivenessMode(SDKConfiguration.LivenessMode.ACTIVE)
                .setCameraOption(SDKConfiguration.CameraOption.FRONT).build());
        setupView();
        requestCameraPermission();
    }

    @Override
    protected void initData() {
        mPresenter = new FaceDetectorPresenter(this, this);
        mBinding.setPresenter(mPresenter);

        mPresenter.setmProduct(mProduct);

        mPresenter.setmSelfieImages(mImageIdentifi);
    }

    private void ReceiveData() {
        if (getIntent() != null) {
            Type type = new TypeToken<RequestIndentifi>() {
            }.getType();

            Type typePhoto = new TypeToken<List<Bitmap>>() {
            }.getType();

            mProduct = new Gson().fromJson(getIntent().getStringExtra(Config.POST_BUNDEL_RES), type);
            mImageIdentifi = new Gson().fromJson(getIntent().getStringExtra(Config.POST_BUNDEL_RES_PHOTO), typePhoto);
        }

    }


    private void startActiveLiveness() {
        mSoundPlayer.doPlay(this.mLivenessUIHelper.getSoundID(this.mLivenessDetector.getFirstDetectionType()));
        mLivenessUIHelper.start();
    }

    private void startTimeoutTimer(final long time) {
        this.stopTimeoutTimer();
        if (time > 0L) {
            long second = 0L;
            mTVCooldown.setText(String.valueOf(second));
            mLivenessTimer = new CountDownTimer(time * 1000L, 1000L) {
                public void onTick(long millisUntilFinished) {
                    long second = millisUntilFinished / 1000L;
                    mTVCooldown.setText(String.valueOf(second));
                    mCircleProgressBar.setProgress((int) ((float) (time - second) / (float) time * 100.0F));
                }

                public void onFinish() {
                    reset();
                    Toast.makeText(FaceDetectorActivity.this, "Quá thời gian thực hiện, Xin quý khách vui lòng thử lại.", Toast.LENGTH_LONG).show();
                }
            };
            this.mLivenessTimer.start();
        }

    }

    private void stopTimeoutTimer() {
        if (this.mLivenessTimer != null) {
            this.mLivenessTimer.cancel();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCameraView.startCamera();
            reset();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCameraView.stopCamera();
    }

    @Override
    public void onDestroy() {
        stopTimeoutTimer();
        mSoundPlayer.close();
        changeStatusBar(false);
        super.onDestroy();
    }


    private void requestCameraPermission() {
        int contactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (contactPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_CAMERA_PERMISSION);
        } else {
            mCameraView.startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (String permission : permissions) {
            if (permission.equals(Manifest.permission.CAMERA)) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Chưa cấp quyền truy cập camera", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    private void initFaceDetector() {
        mLivenessDetector = new LivenessDetector(this);
        mLivenessDetector.setFaceActions();
        mLivenessDetector.setConfiguration(mConfiguration);
        mLivenessDetector.setDetectorListener(mFaceDetectorListener);
        mLivenessDetector.start();
    }

    private void reset() {
        stopTimeoutTimer();
        mTVCooldown.setText("");
        mListBitmap.clear();
        isHandleStart = false;
        isFinished = false;
        isCapturing = false;
        mCircleProgressBar.setProgress(0);
        if (mLivenessDetector != null) {
            mLivenessDetector.setFaceActions();
            mLivenessDetector.reset();
        }
        if (mLivenessUIHelper != null) {
            mLivenessUIHelper.reset();
            mLivenessUIHelper.setSteps(mLivenessDetector.getAllSteps());
        }
        mLivenessLayout.setVisibility(View.GONE);
        mCameraBGImageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.bg_camera_mask));
        mCameraMaskImageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.bg_face_border_blue));
        mCaptureButton.setVisibility(View.GONE);
    }

    private void setupView() {
        mSoundPlayer = new SoundPlayer(this, this.mConfiguration.getEnableSound());
        initFaceDetector();
        mCircleProgressBar.setProgressFormatter(null);
        switch (this.mConfiguration.getCameraOption()) {
            case BACK:
                this.mCameraMode = 1;
                break;
            case FRONT:
                this.mCameraMode = 0;
                break;
            case BOTH:
                this.mCameraMode = 0;
        }
        mCameraView.isStretchToFill = true;
        mCameraView.cameraType = this.mCameraMode;
        mCameraView.imageFormat = 35;
        mCameraView.cameraMaxBufferWidth = 640;
        mCameraView.cameraMaxBufferHeight = 480;
        mCameraView.setBufferListener(new CameraViewEventListener() {

            public void onCameraError(String error) {
                Toast.makeText(FaceDetectorActivity.this,
                        error, Toast.LENGTH_SHORT).show();
            }

            public void onPreviewReady() {
                if (!isHandleStart) {
                    mSoundPlayer.doPlay(R.raw.guide);
                }
            }

            public void onBufferReady(Image image) {
                if (!isFinished && !isCapturing) {
                    try {
                        mLivenessDetector.doDetection(image, mCameraMode == 0 ? 3 : 1);
                    } catch (Exception var3) {
                        var3.printStackTrace();
                    }

                }
            }

            public void onCapturedImage(Bitmap bm) {
                mListBitmap.add(new TVSelfieImage(ActionFace.portrait.name(), bm));
            }
        });
        mCircleProgressBar.setProgress(0);
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaptureButton.setVisibility(View.INVISIBLE);
                FaceDetectorActivity.this.startCapture();
            }
        });
        numOfFrame = this.mConfiguration.hasLivenessStep() && this.mConfiguration.getLivenessMode() == SDKConfiguration.LivenessMode.PASSIVE ? 1 : 2;
        mProgressAnimation = new CircleProgressBar.CircleProgressBarAnimation(this.mCircleProgressBar, (long) (this.numOfFrame * 500));
        mLivenessUIHelper = new FaceDetectionHelper(this, mLivenessLayout);
        mLivenessUIHelper.setSteps(mLivenessDetector.getAllSteps());
        switch (mConfiguration.getLivenessMode()) {
            case NONE:
            case PASSIVE:
                this.mCaptureButton.setVisibility(View.VISIBLE);
                break;
            case ACTIVE:
                this.mCaptureButton.setVisibility(View.INVISIBLE);
        }
    }

    private void startCapture() {
        isCapturing = true;
        mCaptureButton.setEnabled(false);
        if (mTimer != null) {
            mTimer.cancel();
        }

        int countDownTime = numOfFrame * 500 + 200;
        mProgressAnimation.setProgress(100);
        mTimer = new CountDownTimer((long) countDownTime, 500L) {
            public void onTick(long millisUntilFinished) {
                if (mListBitmap.size() < numOfFrame + 4) {
                    mCameraView.takePicture();
                }
            }

            public void onFinish() {
                faceVerify();
            }
        };
        mTimer.start();
    }

    private void faceVerify() {
        selfieImages = new ArrayList<>();
        if ("Y".equals(actionFace)) {
            if ("Y".equals(liveness)) {
                selfieImages = mListBitmap;
            } else {
                // 0,5 là cắt số ảnh bitmap từ 0 đến 5
                selfieImages.addAll(mListBitmap.subList(0, 1));

            }
        } else {
            if ("Y".equals(liveness)) {
                selfieImages.addAll(mListBitmap.subList(3, 6));

            } else {
                selfieImages.addAll(mListBitmap.subList(4, 5));

            }
        }
        storeImage(selfieImages.get(0).getImage());

        // XU LY SU KIEN CÂT ANH
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_CAMERA_PERMISSION) {
            requestCameraPermission();
        } else if (requestCode == REQ_CODE_FACE_PAY) {
            if (resultCode != RESULT_OK) {
                finish();
            }
        }
    }

    private void changeStatusBar(boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = FaceDetectorActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, isBlack ? R.color.black : R.color.blue));
        }
    }


    @Override
    public void onBackClick() {

    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("CheckFace",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("CheckFace", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("CheckFace", "Error accessing file: " + e.getMessage());
        }

        List<File> fileList = new ArrayList<>();
        fileList.add(pictureFile);
        mPresenter.requestUploadSurvey(fileList);


    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        return mediaFile;
    }



}