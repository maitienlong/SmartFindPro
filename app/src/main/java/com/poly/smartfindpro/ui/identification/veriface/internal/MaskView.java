package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.poly.smartfindpro.R;


public class MaskView extends ConstraintLayout {
    public static final String RATIO_FORMAT = "%d:%d";
    private static final String TAG = MaskView.class.getSimpleName();
    private ImageView borderView;
    private ImageView maskView;
    private float percentWidth;
    private float percentHeight;
    private int backgroundSrc;
    private int backgroundInnerView;
    private int ratioWidth;
    private int ratioHeight;

    public MaskView(Context context) {
        super(context);
    }

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public MaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_mask_view, this, true);
        this.borderView = view.findViewById(R.id.iv_id_card_mask_background);
        this.maskView = view.findViewById(R.id.camera_mask);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaskView, 0, 0);
        this.percentWidth = a.getFloat(R.styleable.MaskView_percentWidth, 1.0F);
        this.percentHeight = a.getFloat(R.styleable.MaskView_percentHeight, 1.0F);
        this.backgroundSrc = a.getResourceId(R.styleable.MaskView_backgroundSrc, 0);
        this.backgroundInnerView = a.getResourceId(R.styleable.MaskView_backgroundInnerView, 0);
        this.ratioWidth = a.getInt(R.styleable.MaskView_ratioWidth, 1);
        this.ratioHeight = a.getInt(R.styleable.MaskView_ratioHeight, 1);
    }

    @SuppressLint({"DefaultLocale"})
    public void updateConstraint(ViewMode viewMode, ConstraintLayout parentView) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        ConstraintSet rootSet = new ConstraintSet();
        rootSet.clone(parentView);
        if (viewMode == ViewMode.LANDSCAPE) {
            constraintSet.constrainPercentWidth(this.maskView.getId(), this.percentHeight);
            constraintSet.constrainPercentHeight(this.maskView.getId(), this.percentWidth);
            rootSet.setDimensionRatio(this.getId(), String.format("%d:%d", this.ratioHeight, this.ratioWidth));
            this.rotateBitmap();
        } else {
            this.borderView.setImageResource(this.backgroundSrc);
            constraintSet.constrainPercentWidth(this.maskView.getId(), this.percentWidth);
            constraintSet.constrainPercentHeight(this.maskView.getId(), this.percentHeight);
            rootSet.setDimensionRatio(this.getId(), String.format("%d:%d", this.ratioWidth, this.ratioHeight));
        }

        if (this.backgroundInnerView != 0) {
            this.maskView.setBackgroundResource(this.backgroundInnerView);
        }

        constraintSet.applyTo(this);
        rootSet.applyTo(parentView);
    }

    public ImageView getImageView() {
        return this.maskView;
    }

    private void rotateBitmap() {
        try {
            Bitmap source = BitmapFactory.decodeResource(this.getResources(), this.backgroundSrc);
            this.borderView.setImageBitmap(TVSDKUtil.rotateBitmap(source, 90.0F));
        } catch (Exception var2) {
            Log.e(TAG, var2.getMessage(), var2);
        }

    }

    public static enum ViewMode {
        LANDSCAPE,
        PORTRAIT;

        private ViewMode() {
        }
    }
}
