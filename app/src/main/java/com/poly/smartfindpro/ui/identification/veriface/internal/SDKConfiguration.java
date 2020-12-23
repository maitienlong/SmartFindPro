package com.poly.smartfindpro.ui.identification.veriface.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SDKConfiguration implements Serializable {
    private Boolean isEnableSound;
    private LivenessMode livenessMode;
    private ActionMode actionMode;
    private CardType cardType;
    private CameraOption cameraOption;
    private CardSide cardSide;
    private boolean isEnableIDSanityCheck;
    private boolean isEnableSelfieSanityCheck;
    private boolean isEnableTiltChecking;

    SDKConfiguration(Builder builder) {
        this.isEnableSound = builder.isEnableSound;
        this.livenessMode = builder.livenessMode;
        this.actionMode = builder.actionMode;
        this.cardType = builder.cardType;
        this.cameraOption = builder.cameraOption;
        this.cardSide = builder.cardSide;
        this.isEnableTiltChecking = builder.isEnableTiltChecking;
        if (!this.hasLivenessStep()) {
            this.livenessMode = LivenessMode.NONE;
        }

    }

    public SDKConfiguration(IDConfiguration idConfiguration) {
        this.isEnableSound = idConfiguration.getEnableSound();
        this.livenessMode = LivenessMode.NONE;
        if (idConfiguration.isReadBothSide()) {
            this.actionMode = ActionMode.READ_CARD_INFO_TWO_SIDE;
        } else {
            this.actionMode = ActionMode.READ_CARD_INFO_ONE_SIDE;
        }

        this.cardType = idConfiguration.getCardType();
        this.cardSide = idConfiguration.getCardSide();
        this.cameraOption = CameraOption.FRONT;
        this.isEnableIDSanityCheck = idConfiguration.isEnableSanityCheck();
        this.isEnableTiltChecking = idConfiguration.isEnableTiltChecking();
    }

    public SDKConfiguration(SelfieConfiguration selfieConfiguration) {
        this.isEnableSound = selfieConfiguration.getEnableSound();
        this.livenessMode = selfieConfiguration.getLivenessMode();
        this.cameraOption = selfieConfiguration.getCameraOption();
        this.actionMode = ActionMode.LIVENESS;
        this.isEnableSelfieSanityCheck = selfieConfiguration.isEnableSanityCheck();
    }

    public LivenessMode getLivenessMode() {
        return this.livenessMode;
    }

    public Boolean getEnableSound() {
        return this.isEnableSound;
    }

    public ActionMode getActionMode() {
        return this.actionMode;
    }

    public CardType getCardType() {
        return this.cardType;
    }

    public CameraOption getCameraOption() {
        return this.cameraOption;
    }

    public CardSide getCardSide() {
        return this.cardSide;
    }

    public boolean isEnableIDSanityCheck() {
        return this.isEnableIDSanityCheck;
    }

    public boolean isEnableSelfieSanityCheck() {
        return this.isEnableSelfieSanityCheck;
    }

    public boolean isEnableTiltChecking() {
        return this.isEnableTiltChecking;
    }

    public List<Step> getAllSteps() {
        ArrayList steps;
        switch(this.actionMode) {
        case FACE_MATCHING:
            return new ArrayList(Arrays.asList(Step.CAPTURE_ID, Step.CAPTURE_SELFIE, Step.FACE_COMPARE));
        case FULL:
            steps = new ArrayList(Arrays.asList(Step.CAPTURE_ID, Step.READ_CARD_INFO, Step.CAPTURE_SELFIE, Step.FACE_COMPARE));
            if (this.cardType.isRequireBackside()) {
                steps.add(1, Step.CAPTURE_BACK_ID);
            }

            return steps;
        case READ_CARD_INFO_ONE_SIDE:
            steps = new ArrayList();
            if (this.cardSide == CardSide.FRONT) {
                steps.add(Step.CAPTURE_ID);
            } else {
                steps.add(Step.CAPTURE_BACK_ID);
            }

            steps.add(Step.READ_CARD_INFO);
            return steps;
        case READ_CARD_INFO_TWO_SIDE:
            steps = new ArrayList(Arrays.asList(Step.CAPTURE_ID, Step.READ_CARD_INFO));
            if (this.cardType.isRequireBackside()) {
                steps.add(1, Step.CAPTURE_BACK_ID);
            }

            return steps;
        case LIVENESS:
            return new ArrayList(Arrays.asList(Step.CAPTURE_SELFIE));
        default:
            return null;
        }
    }

    public boolean hasLivenessStep() {
        return this.actionMode == ActionMode.LIVENESS || this.actionMode == ActionMode.FULL;
    }

    public static class Builder {
        private Boolean isEnableSound = true;
        private LivenessMode livenessMode;
        private ActionMode actionMode;
        private CardType cardType;
        private CameraOption cameraOption;
        private CardSide cardSide;
        private boolean isEnableIDSanityCheck;
        private boolean isEnableSelfieSanityCheck;
        private boolean isEnableTiltChecking;

        public Builder() {
            this.livenessMode = LivenessMode.NONE;
            this.actionMode = ActionMode.FACE_MATCHING;
            this.cameraOption = CameraOption.FRONT;
            this.cardSide = CardSide.FRONT;
            this.isEnableIDSanityCheck = false;
            this.isEnableSelfieSanityCheck = false;
            this.isEnableTiltChecking = false;
        }

        public Builder setEnableSound(Boolean isEnable) {
            this.isEnableSound = isEnable;
            return this;
        }

        public Builder setLivenessMode(LivenessMode livenessMode) {
            this.livenessMode = livenessMode;
            return this;
        }

        public Builder setActionMode(ActionMode actionMode) {
            this.actionMode = actionMode;
            return this;
        }

        public Builder setCardType(CardType cardType) {
            this.cardType = cardType;
            return this;
        }

        public Builder setCameraOption(CameraOption cameraOption) {
            this.cameraOption = cameraOption;
            return this;
        }

        public Builder setEnableIDSanityCheck(boolean enableIDSanityCheck) {
            this.isEnableIDSanityCheck = enableIDSanityCheck;
            return this;
        }

        public Builder setEnableSelfieSanityCheck(boolean enableSelfieSanityCheck) {
            this.isEnableSelfieSanityCheck = enableSelfieSanityCheck;
            return this;
        }

        public Builder setEnableTiltChecking(boolean enableTiltChecking) {
            this.isEnableTiltChecking = enableTiltChecking;
            return this;
        }

        public CardSide getCardSide() {
            return this.cardSide;
        }

        public SDKConfiguration build() {
            return new SDKConfiguration(this);
        }
    }

    public static enum CardSide {
        FRONT,
        BACK;

        private CardSide() {
        }
    }

    public static enum CameraOption {
        FRONT,
        BACK,
        BOTH;

        private CameraOption() {
        }
    }

    public static enum ActionMode {
        FACE_MATCHING,
        FULL,
        LIVENESS,
        READ_CARD_INFO_ONE_SIDE,
        READ_CARD_INFO_TWO_SIDE;

        private ActionMode() {
        }
    }

    public static enum LivenessMode {
        NONE,
        PASSIVE,
        ACTIVE;

        private LivenessMode() {
        }
    }

    public static enum Step {
        CAPTURE_ID,
        CAPTURE_BACK_ID,
        CAPTURE_SELFIE,
        READ_CARD_INFO,
        FACE_COMPARE;

        private Step() {
        }
    }
}