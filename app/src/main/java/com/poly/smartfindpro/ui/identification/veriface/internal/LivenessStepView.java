package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.poly.smartfindpro.R;

public class LivenessStepView extends RelativeLayout {

    ImageView mIconDefault;

    ImageView mIconView;

    CircleImageView mImageView;

    public LivenessStepView(Context context) {
        super(context);
        inflate(this.getContext(), R.layout.liveness_gesture_step_view, this);
        mIconDefault = findViewById(R.id.icon_default);
        mIconView = findViewById(R.id.icon_view);
        mImageView = findViewById(R.id.iv_liveness);
    }

    public LivenessStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LivenessStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void setIcon(Drawable bitmap) {
        mIconView.setImageDrawable(bitmap);
    }

    void setBitmap(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, 1, 0.5F, 1, 0.5F);
        scaleAnimation.setDuration(300L);
        scaleAnimation.setFillAfter(true);
        mImageView.startAnimation(scaleAnimation);

    }

    void setMode(Mode mode) {
        switch (mode) {
            case ACTIVE:
                mIconView.setVisibility(VISIBLE);
                mIconDefault.setVisibility(GONE);
                mImageView.setVisibility(GONE);
                setBackground(null);
                break;
            case INACTIVE:
                mIconView.setVisibility(GONE);
                mIconDefault.setVisibility(VISIBLE);
                mImageView.setVisibility(GONE);
                break;
            case FINISH:
                mIconView.setVisibility(GONE);
                mIconDefault.setVisibility(GONE);
                mImageView.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }

    enum Mode {
        INACTIVE,
        ACTIVE,
        FINISH;
        Mode() {

        }
    }
}
