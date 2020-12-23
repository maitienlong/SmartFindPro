package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poly.smartfindpro.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaceDetectionHelper {

    private Context mContext;

    private int mCurShowIndex = -1;

    private HashMap mDrawableCache = new HashMap();

    private View mRootView;

    private LinearLayout mContentView;

    private TextView mTVError;

    private List<FaceDetectionType> mSteps;

    private ArrayList mStepsView = new ArrayList();

    private TextView mTextView;

    private ImageView mIVSelected;

    public void setSteps(List<FaceDetectionType> steps) {
        mSteps = steps;
        for (FaceDetectionType step : mSteps) {
            LivenessStepView stepView = new LivenessStepView(mContext);
            stepView.setIcon(getDrawRes(step));
            stepView.setMode(LivenessStepView.Mode.INACTIVE);
            mContentView.addView(stepView);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) stepView.getLayoutParams();
            layoutParams.leftMargin = 30;
            stepView.setLayoutParams(layoutParams);
            mStepsView.add(stepView);
        }

    }

    public FaceDetectionHelper(Context context, View view) {
        mContext = context;
        mRootView = view;
        initView();
        initAnimation();
    }

    public void start() {
        mRootView.setVisibility(View.VISIBLE);
        mIVSelected.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.new_fade_out);
        mRootView.setAnimation(animation);
        mCurShowIndex = 0;
        ((LivenessStepView) mStepsView.get(this.mCurShowIndex)).setMode(LivenessStepView.Mode.ACTIVE);
        updateView(this.mSteps.get(0));
    }

    public void destroy() {
        mRootView = null;
        mContext = null;
        mDrawableCache.clear();
    }

    public void finishCurrentStep(Bitmap bitmap) {
        mTVError.setText("");
        if (mCurShowIndex >= 0) {
            ((LivenessStepView) mStepsView.get(mCurShowIndex)).setBitmap(TVSDKUtil.flipFacingBitmap(bitmap));
            ((LivenessStepView) mStepsView.get(mCurShowIndex)).setMode(LivenessStepView.Mode.FINISH);
        }

        ++mCurShowIndex;
        if (mCurShowIndex >= 0 && mCurShowIndex < mSteps.size()) {
            ((LivenessStepView) mStepsView.get(this.mCurShowIndex)).setMode(LivenessStepView.Mode.ACTIVE);
            updateView(mSteps.get(mCurShowIndex));
        } else {
            mIVSelected.setVisibility(View.GONE);
        }

    }

    public void setLivenessError(String error) {
        mTVError.setText(error);
    }

    private void initAnimation() {
        mDrawableCache = new HashMap();
    }

    private void initView() {
        mContentView = mRootView.findViewById(R.id.liveness_content_view);
        mTextView = mRootView.findViewById(R.id.tv_step_instruction);
        mIVSelected = mRootView.findViewById(R.id.iv_selected);
        mTVError = mRootView.findViewById(R.id.tv_liveness_error);
        mRootView.setVisibility(View.INVISIBLE);
    }

    private void updateView(FaceDetectionType detectionType) {
        mTVError.setText("");
        ObjectAnimator animation = ObjectAnimator.ofFloat(mIVSelected, "translationX",
                new float[]{(float) (((LivenessStepView) mStepsView.get(this.mCurShowIndex)).getLeft() + this.mContentView.getLeft() - 10)});
        animation.setDuration(500L);
        animation.start();
        String text = getDetectionName(detectionType);
        mTextView.setText(text);
    }

    private Drawable getDefaultDrawRes() {
        return mContext.getResources().getDrawable(R.mipmap.ic_opposite);
    }

    private Drawable getDrawRes(FaceDetectionType detectionType) {
        int drawableID = -1;
        switch (detectionType.getType()) {
            case 1:
                drawableID = R.mipmap.ic_opposite;
                break;
            case 2:
                drawableID = R.mipmap.ic_left;
                break;
            case 3:
                drawableID = R.mipmap.ic_right;
                break;
            case 4:
                drawableID = R.mipmap.ic_smile;
                break;
            case 5:
                drawableID = R.mipmap.ic_up;
                break;
            case 6:
                drawableID = R.mipmap.ic_down;
                break;
            case 7:
                drawableID = R.mipmap.ic_blink_left;
                break;
            case 8:
                drawableID = R.mipmap.ic_blink_right;
                break;
            case 9:
                drawableID = R.mipmap.ic_incline_left;
                break;
            case 10:
                drawableID = R.mipmap.ic_incline_right;
                break;
            case 11:
                drawableID = R.mipmap.ic_close_eye;
                break;
        }

        Drawable drawable = (Drawable) this.mDrawableCache.get(drawableID);
        if (drawable != null) {
            return drawable;
        } else {
            drawable = this.mContext.getResources().getDrawable(drawableID);
            mDrawableCache.put(drawableID, drawable);
            return drawable;
        }
    }

    private String getDetectionName(FaceDetectionType detectionType) {
        if (mContext != null) {
            switch (detectionType.getType()) {
                case 1:
                    return this.mContext.getString(R.string.liveness_detection_neutral);
                case 2:
                    return this.mContext.getString(R.string.liveness_detection_face_left);
                case 3:
                    return this.mContext.getString(R.string.liveness_detection_face_right);
                case 4:
                    return this.mContext.getString(R.string.liveness_detection_face_smile);
                case 5:
                    return this.mContext.getString(R.string.liveness_detection_face_up);
                case 6:
                    return this.mContext.getString(R.string.liveness_detection_face_down);
                case 7:
                    return this.mContext.getString(R.string.liveness_detection_blink_left);
                case 8:
                    return this.mContext.getString(R.string.liveness_detection_blink_right);
                case 9:
                    return this.mContext.getString(R.string.liveness_detection_face_incline_left);
                case 10:
                    return this.mContext.getString(R.string.liveness_detection_face_incline_right);
                case 11:
                    return this.mContext.getString(R.string.linveness_detection_close_eye);
            }
        }

        return null;
    }

    public int getSoundID(FaceDetectionType detectionType) {
        switch (detectionType.getType()) {
            case 1:
                return R.raw.lock_straight;
            case 2:
                return R.raw.face_left;
            case 3:
                return R.raw.face_right;
            case 4:
                return R.raw.smile;
            case 5:
                return R.raw.face_up;
            case 6:
                return R.raw.face_down;
            case 7:
                return R.raw.blink_left;
            case 8:
                return R.raw.blink_right;
            case 9:
                return R.raw.face_incline_left;
            case 10:
                return R.raw.face_incline_right;
            case 11:
                return R.raw.close_eye;
            default:
                return -1;
        }
    }

    public void reset() {
        mCurShowIndex = -1;
        mStepsView.clear();
        mSteps.clear();
        mContentView.removeAllViews();
    }
}