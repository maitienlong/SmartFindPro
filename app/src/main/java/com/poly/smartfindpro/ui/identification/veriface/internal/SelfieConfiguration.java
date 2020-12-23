package com.poly.smartfindpro.ui.identification.veriface.internal;

public class SelfieConfiguration {
    private SDKConfiguration.CameraOption cameraOption;
    private SDKConfiguration.LivenessMode livenessMode;
    private boolean isEnableSound;
    private boolean isEnableSanityCheck;

    SelfieConfiguration(Builder builder) {
        this.isEnableSound = builder.isEnableSound;
        this.cameraOption = builder.cameraOption;
        this.livenessMode = builder.livenessMode;
        this.isEnableSanityCheck = builder.isEnableSanityCheck;
    }

    public SDKConfiguration.LivenessMode getLivenessMode() {
        return this.livenessMode;
    }

    public SDKConfiguration.CameraOption getCameraOption() {
        return this.cameraOption;
    }

    public boolean getEnableSound() {
        return this.isEnableSound;
    }

    public boolean isEnableSanityCheck() {
        return this.isEnableSanityCheck;
    }

    public static class Builder {
        private SDKConfiguration.CameraOption cameraOption;
        private SDKConfiguration.LivenessMode livenessMode;
        private Boolean isEnableSound = false;
        private boolean isEnableSanityCheck = false;

        public Builder() {
        }

        public Builder setEnableSound(boolean isEnable) {
            this.isEnableSound = isEnable;
            return this;
        }

        public Builder setCameraOption(SDKConfiguration.CameraOption cameraOption) {
            this.cameraOption = cameraOption;
            return this;
        }

        public Builder setLivenessMode(SDKConfiguration.LivenessMode livenessMode) {
            this.livenessMode = livenessMode;
            return this;
        }

        public Builder setEnableSanityCheck(boolean isEnableSanityCheck) {
            this.isEnableSanityCheck = isEnableSanityCheck;
            return this;
        }

        public SelfieConfiguration build() {
            return new SelfieConfiguration(this);
        }
    }
}
