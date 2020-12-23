package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

public class PreviewView extends TextureView {
    private boolean isStretchToFill;
    private int mRatioHeight;
    private int mRatioWidth;

    public PreviewView(Context context) {
        this(context, null);
    }

    public PreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreviewView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mRatioWidth = 0;
        this.mRatioHeight = 0;
        this.isStretchToFill = false;
    }

    public void setStretchToFill(boolean stretchToFill) {
        this.isStretchToFill = stretchToFill;
    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        } else if (this.mRatioWidth != width || this.mRatioHeight != height) {
            this.mRatioWidth = width;
            this.mRatioHeight = height;
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (this.mRatioWidth == 0 || this.mRatioHeight == 0) {
            setMeasuredDimension(width, height);
        } else if (this.isStretchToFill) {
            if (width > (this.mRatioWidth * height) / this.mRatioHeight) {
                setMeasuredDimension(width, (this.mRatioHeight * width) / this.mRatioWidth);
            } else {
                setMeasuredDimension((this.mRatioWidth * height) / this.mRatioHeight, height);
            }
        } else if (width < (this.mRatioWidth * height) / this.mRatioHeight) {
            setMeasuredDimension(width, (this.mRatioHeight * width) / this.mRatioWidth);
        } else {
            setMeasuredDimension((this.mRatioWidth * height) / this.mRatioHeight, height);
        }
    }
}
