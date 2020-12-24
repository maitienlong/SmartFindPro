package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.content.Context;
import android.graphics.Rect;
import android.media.Image;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.poly.smartfindpro.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LivenessDetector {

    private boolean isDetecting = false;

    private boolean isStarted = false;

    private boolean readyForNextStep = true;

    private LivenessDetectorListener mDetectorListener;

    private SDKConfiguration mConfiguration;

    private int mCurrentStepIndex;

    private Context mContext;

    private FirebaseVisionFaceDetector mDetector;

    private List<String> faceActions;

    private List<FaceDetectionType> allSteps;

    public FaceDetectionType getFirstDetectionType() {
        return allSteps.get(0);
    }

    public void setConfiguration(SDKConfiguration configuration) {
        mConfiguration = configuration;
    }

    public LivenessDetector(Context context) {
        this.mContext = context;
        this.mCurrentStepIndex = -1;
        this.faceActions = new ArrayList<>();
        FirebaseVisionFaceDetectorOptions highAccuracyOpts = (new FirebaseVisionFaceDetectorOptions.Builder()).setMinFaceSize(0.15F).build();
        this.mDetector = FirebaseVision.getInstance().getVisionFaceDetector(highAccuracyOpts);
    }

    public List<FaceDetectionType> getAllSteps() {
        return allSteps;
    }

    public void setFaceActions(List<String> faceActions) {
        this.faceActions.clear();
        this.faceActions.addAll(faceActions);
        setAllSteps();
    }

    public void setFaceActions() {
        setAllSteps();
    }

    private void setAllSteps() {
        allSteps = new ArrayList<>();
//        if (faceActions != null && faceActions.size() > 0) {
//            List<String> randomFaceActions = randomFaceAction(faceActions);
//            if (randomFaceActions.size() > 0) {
//                for (String faceAction : faceActions) {
//                    allSteps.add(new FaceDetectionType(faceAction, ActionFace.valueOf(faceAction).getValue()));
//                }
//            }
//        }

        //       allSteps.add(new FaceDetectionType(ActionFace.portrait.name(), ActionFace.valueOf(ActionFace.portrait.name()).getValue()));
//        allSteps.add(new FaceDetectionType(ActionFace.turn_left.name(),  ActionFace.valueOf(ActionFace.turn_left.name()).getValue()));
//        allSteps.add(new FaceDetectionType(ActionFace.turn_right.name(),  ActionFace.valueOf(ActionFace.turn_right.name()).getValue()));
//        allSteps.add(new FaceDetectionType(ActionFace.smile.name(),  ActionFace.valueOf(ActionFace.smile.name()).getValue()));
        allSteps.add(new FaceDetectionType(ActionFace.portrait.name(), ActionFace.valueOf(ActionFace.portrait.name()).getValue()));
    }

    private List<String> randomFaceAction(List<String> faceActions) {
        List<String> randomFaceActions = new ArrayList<>();
        String lastFaceAction = faceActions.get(faceActions.size() - 1);
        faceActions.remove(lastFaceAction);
        int size = faceActions.size();
        if (size > 3) {
            for (int i = 0; i < 3; i++) {
                int randomIndex = getRandomIndex(faceActions);
                randomFaceActions.add(faceActions.get(randomIndex));
                faceActions.remove(randomIndex);
            }
        }
        return randomFaceActions;
    }

    private int getRandomIndex(List<String> faceActions) {
        return new Random().nextInt(faceActions.size());
    }

    public void setDetectorListener(LivenessDetectorListener mDetectorListener) {
        this.mDetectorListener = mDetectorListener;
    }

    public synchronized void start() {
        mCurrentStepIndex = -1;
    }

    public synchronized void reset() {
        this.isDetecting = false;
        this.isStarted = false;
        this.readyForNextStep = true;
        this.mCurrentStepIndex = -1;
        this.allSteps = getAllSteps();
    }

    public synchronized void changeDetectionType(FaceDetectionType faceDetectionType) {
        this.readyForNextStep = true;
    }

    private FaceDetectionType getCurrentStep() {
        return this.mCurrentStepIndex >= 0 && this.mCurrentStepIndex < this.allSteps.size() ? this.allSteps.get(this.mCurrentStepIndex) : null;
    }

    public synchronized void doDetection(Image image, int rotation) {
        if (!this.isDetecting && this.readyForNextStep) {
            this.isDetecting = true;
            FirebaseVisionFaceDetectorOptions.Builder firebaseBuider = new FirebaseVisionFaceDetectorOptions.Builder();
            firebaseBuider.setMinFaceSize(0.2F);
            if (this.mConfiguration.getLivenessMode() == SDKConfiguration.LivenessMode.ACTIVE) {
                firebaseBuider.setLandmarkMode(2).setClassificationMode(2);
            }
            this.mDetector = FirebaseVision.getInstance().getVisionFaceDetector(firebaseBuider.build());
            final FirebaseVisionImage vsImage = FirebaseVisionImage.fromMediaImage(image, rotation);
            final int originwidth = image.getWidth();
            final int originHeight = image.getHeight();
            final Rect originRect = new Rect(0, 0, originwidth, originHeight);
            //final long curTime = System.currentTimeMillis();
            this.mDetector.detectInImage(vsImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                @Override
                public void onSuccess(List<FirebaseVisionFace> allFaces) {
                    //long endTime = System.currentTimeMillis();
                    //long deltaTime = endTime - curTime;
                    //Log.i("FACESDK", "Processed Time: " + deltaTime);
                    List<FirebaseVisionFace> faces = new ArrayList<>();
                    for (FirebaseVisionFace facex : allFaces) {
                        if (originRect.contains(facex.getBoundingBox())) {
                            faces.add(facex);
                        }
                    }

                    if (faces.isEmpty()) {
                        LivenessDetector.this.showErrorMessage(LivenessDetector.this.mContext.getString(R.string.face_not_found));
                        if (LivenessDetector.this.mCurrentStepIndex != -1) {
                            if (LivenessDetector.this.mDetectorListener != null) {
                                LivenessDetector.this.mDetectorListener.onResetStep();
                            }
                        }
                    } else if (faces.size() > 1) {
                        LivenessDetector.this.showErrorMessage(LivenessDetector.this.mContext.getString(R.string.face_multiple));
                        if (LivenessDetector.this.mCurrentStepIndex != -1) {
                            if (LivenessDetector.this.mDetectorListener != null) {
                                LivenessDetector.this.mDetectorListener.onResetStep();
                            }
                        }
                    } else {
                        FirebaseVisionFace face = faces.get(0);
                        Rect faceRect = face.getBoundingBox();
                        double ratio = (double) Math.max((float) faceRect.width() / (float) originwidth, (float) faceRect.height() / (float) originHeight);
                        if (!LivenessDetector.this.isStarted) {
                            boolean faceTooSmall = ratio < 0.30000001192092896D;
                            boolean faceTooLarge = ratio > 0.8500000238418579D;
                            boolean faceIsCenter = TVSDKUtil.isContainsRect(originRect, faceRect);
                            if (faceTooSmall) {
                                LivenessDetector.this.showErrorMessage(LivenessDetector.this.mContext.getString(R.string.face_too_small));
                                return;
                            }

                            if (faceTooLarge) {
                                LivenessDetector.this.showErrorMessage(LivenessDetector.this.mContext.getString(R.string.face_too_large));
                                return;
                            }

                            if (!faceIsCenter) {
                                LivenessDetector.this.showErrorMessage(LivenessDetector.this.mContext.getString(R.string.face_out_of_rect));
                                return;
                            }
                        }

                        if (LivenessDetector.this.mDetectorListener != null) {
                            LivenessDetector.this.mDetectorListener.onFaceDetected(vsImage.getBitmap());
                        }

                        if (LivenessDetector.this.mConfiguration.getLivenessMode() != SDKConfiguration.LivenessMode.ACTIVE) {
                            LivenessDetector.this.isDetecting = false;
                        } else if (!LivenessDetector.this.isStarted) {
                            LivenessDetector.this.isStarted = true;
                            LivenessDetector.this.mCurrentStepIndex = LivenessDetector.this.mCurrentStepIndex + 1;
                            LivenessDetector.this.isDetecting = false;
                        } else {
                            FaceDetectionType currentStep = LivenessDetector.this.getCurrentStep();
                            if (currentStep != null) {
                                int result = LivenessDetector.this.verifyFace(face, LivenessDetector.this.getCurrentStep().getType());
                                if (result == 1) {
                                    if (LivenessDetector.this.mDetectorListener != null) {
                                        LivenessDetector.this.readyForNextStep = false;
                                        LivenessDetector.this.mCurrentStepIndex = LivenessDetector.this.mCurrentStepIndex + 1;
                                        FaceDetectionType nextStep = LivenessDetector.this.getCurrentStep();
                                        if (nextStep != null) {
                                            LivenessDetector.this.mDetectorListener.onMovedToNextStep(currentStep, nextStep, vsImage.getBitmap());
                                        } else {
                                            LivenessDetector.this.mDetectorListener.onDetectionSuccess(vsImage.getBitmap());
                                        }
                                    }
                                }
                            }

                            LivenessDetector.this.isDetecting = false;
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private int verifyFace(FirebaseVisionFace face, int type) {
        float eulerAngleY = face.getHeadEulerAngleY();
        float eulerAngleZ = face.getHeadEulerAngleZ();
        float leftEyeOpenProbability = face.getLeftEyeOpenProbability();
        float rightEyeOpenProbability = face.getRightEyeOpenProbability();
        float smilingProbability = face.getSmilingProbability();
        float THRESHOLD_BLINK_EYE = 0.05f;
        int THRESHOLD_INCLINE_RIGHT = 20;
        int THRESHOLD_INCLINE_LEFT = -20;
        int THRESHOLD_LOOK_LEFT = 20;
        int THRESHOLD_LOOK_RIGHT = -20;
        int THRESHOLD_LOOK_STRAIGHT_LEFT = -5;
        int THRESHOLD_LOOK_STRAIGHT_RIGHT = 5;
        float THRESHOLD_SMILING = 0.5f;
        switch (type) {
            case 1: // Nhìn thẳng
                if (eulerAngleY <= THRESHOLD_LOOK_STRAIGHT_RIGHT && eulerAngleY >= THRESHOLD_LOOK_STRAIGHT_LEFT) {
                    if (smilingProbability < THRESHOLD_SMILING) {
                        return 1;
                    }
                }
                return -1;
            case 2:  // Nhìn sang trái
                if (eulerAngleY >= THRESHOLD_LOOK_LEFT) {
                    return 1;
                }
                return -1;
            case 3: // Nhìn sang phải
                if (eulerAngleY <= THRESHOLD_LOOK_RIGHT) {
                    return 1;
                }
                return -1;
            case 4: // Nhìn thẳng cười
                if (eulerAngleY <= THRESHOLD_LOOK_STRAIGHT_RIGHT && eulerAngleY >= THRESHOLD_LOOK_STRAIGHT_LEFT) {
                    if (smilingProbability >= THRESHOLD_SMILING) {
                        return 1;
                    }
                }
                return -1;
            case 5: // Ngẩng mặt lên
                if (eulerAngleZ >= 4) {
                    return 1;
                }
                return -1;
            case 6: // Cúi mặt xuống
                if (eulerAngleZ < 4) {
                    return 1;
                }
                return -1;
            case 7:
                // Nháy mắt trái
                if (rightEyeOpenProbability <= THRESHOLD_BLINK_EYE) {
                    return 1;
                }
                return -1;
            case 8: // Nháy mắt phải
                if (leftEyeOpenProbability <= THRESHOLD_BLINK_EYE) {
                    return 1;
                }
                return -1;
            case 9: // Nghiêng sang trái
                if (eulerAngleZ <= THRESHOLD_INCLINE_LEFT) {
                    return 1;
                }
                return -1;
            case 10: // Nghiêng sang phải
                if (eulerAngleZ >= THRESHOLD_INCLINE_RIGHT) {
                    return 1;
                }
                return -1;
            case 11: // Nhắm cả hai mắt
                if (rightEyeOpenProbability <= THRESHOLD_BLINK_EYE && leftEyeOpenProbability <= THRESHOLD_BLINK_EYE) {
                    return 1;
                }
                return -1;
            default:
                return -1;
        }
    }

    private void showErrorMessage(String message) {
        this.isDetecting = false;
        if (this.mDetectorListener != null) {
            this.mDetectorListener.onDetectionFailed(message);
        }
    }
}