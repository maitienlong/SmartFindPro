package com.poly.smartfindpro.ui.identification.veriface.internal;

public class FaceDetectionType {

    private String mFaceAction;

    private int mType;

    private int mTimeInterval;

    FaceDetectionType(String faceAction, int type) {
        this.mFaceAction = faceAction;
        this.mType = type;
        this.mTimeInterval = 30;
    }

    public String getFaceAction() {
        return mFaceAction;
    }

    public int getType() {
        return this.mType;
    }

    public int getTimeInterval() {
        return this.mTimeInterval;
    }
}
