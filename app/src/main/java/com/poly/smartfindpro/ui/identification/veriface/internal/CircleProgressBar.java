package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.poly.smartfindpro.R;

public class CircleProgressBar extends View {
    private final RectF mProgressRectF;
    private final Rect mProgressTextRect;
    private final Paint mProgressPaint;
    private final Paint mProgressBackgroundPaint;
    private final Paint mProgressStrokePaint;
    private final Paint mProgressShadowPaint;
    private final Paint mProgressTextPaint;
    private float mRadius;
    private float mCenterX;
    private float mCenterY;
    private int mProgress;
    private int mMax;
    private int mLineCount;
    private float mLineWidth;
    private float mProgressStrokeWidth;
    private float mProgressTextSize;
    private int mProgressStartColor;
    private int mProgressEndColor;
    private int mProgressTextColor;
    private int mProgressBackgroundColor;
    private int mStartDegree;
    private boolean mDrawBackgroundOutsideProgress;
    private ProgressFormatter mProgressFormatter;
    private int mStyle;
    private int mShader;
    private Paint.Cap mCap;

    public CircleProgressBar(Context context) {
        this(context, (AttributeSet)null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mProgressRectF = new RectF();
        this.mProgressTextRect = new Rect();
        this.mProgressPaint = new Paint(1);
        this.mProgressBackgroundPaint = new Paint(1);
        this.mProgressStrokePaint = new Paint(1);
        this.mProgressShadowPaint = new Paint(1);
        this.mProgressTextPaint = new TextPaint(1);
        this.mMax = 100;
        this.mProgressFormatter = new DefaultProgressFormatter();
        this.initFromAttributes(context, attrs);
        this.initPaint();
    }

    private void initFromAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        this.mLineCount = a.getInt(R.styleable.CircleProgressBar_line_count, 45);
        this.mStyle = a.getInt(R.styleable.CircleProgressBar_style, 0);
        this.mShader = a.getInt(R.styleable.CircleProgressBar_progress_shader, 0);
        this.mCap = a.hasValue(R.styleable.CircleProgressBar_progress_stroke_cap) ? Paint.Cap.values()[a.getInt(R.styleable.CircleProgressBar_progress_stroke_cap, 0)] : Paint.Cap.ROUND;
        this.mLineWidth = (float)a.getDimensionPixelSize(R.styleable.CircleProgressBar_bar_line_width, DisplayUtils.dip2px(this.getContext(), 11.0F));
        this.mProgressTextSize = (float)a.getDimensionPixelSize(R.styleable.CircleProgressBar_progress_text_size, DisplayUtils.dip2px(this.getContext(), 11.0F));
        this.mProgressStrokeWidth = (float)a.getDimensionPixelSize(R.styleable.CircleProgressBar_progress_stroke_width, DisplayUtils.dip2px(this.getContext(), 1.0F));
        this.mProgressStartColor = a.getColor(R.styleable.CircleProgressBar_progress_start_color, Color.parseColor("#fff2a670"));
        this.mProgressEndColor = a.getColor(R.styleable.CircleProgressBar_progress_end_color, Color.parseColor("#fff2a670"));
        this.mProgressTextColor = a.getColor(R.styleable.CircleProgressBar_progress_text_color, Color.parseColor("#fff2a670"));
        this.mProgressBackgroundColor = a.getColor(R.styleable.CircleProgressBar_progress_background_color, Color.parseColor("#ffe3e3e5"));
        this.mStartDegree = a.getInt(R.styleable.CircleProgressBar_progress_start_degree, 90);
        this.mDrawBackgroundOutsideProgress = a.getBoolean(R.styleable.CircleProgressBar_drawBackgroundOutsideProgress, false);
        a.recycle();
    }

    private void initPaint() {
        this.mProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mProgressTextPaint.setTextSize(this.mProgressTextSize);
        this.mProgressPaint.setStyle(this.mStyle == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.mProgressPaint.setStrokeWidth(this.mProgressStrokeWidth);
        this.mProgressPaint.setColor(this.mProgressStartColor);
        this.mProgressPaint.setStrokeCap(this.mCap);
        this.mProgressBackgroundPaint.setStyle(this.mStyle == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.mProgressBackgroundPaint.setStrokeWidth(this.mProgressStrokeWidth);
        this.mProgressBackgroundPaint.setColor(this.mProgressBackgroundColor);
        this.mProgressStrokePaint.setStyle(Paint.Style.STROKE);
        this.mProgressStrokePaint.setStrokeWidth(8.0F);
        this.mProgressStrokePaint.setColor(this.mProgressStartColor);
        this.mProgressStrokePaint.setStrokeCap(this.mCap);
    }

    private void updateProgressShader() {
        if (this.mProgressStartColor != this.mProgressEndColor) {
            Shader shader = null;
            switch(this.mShader) {
            case 0:
                shader = new LinearGradient(this.mProgressRectF.left, this.mProgressRectF.top, this.mProgressRectF.left, this.mProgressRectF.bottom, this.mProgressStartColor, this.mProgressEndColor, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                matrix.setRotate(90.0F, this.mCenterX, this.mCenterY);
                ((Shader)shader).setLocalMatrix(matrix);
                break;
            case 1:
                shader = new RadialGradient(this.mCenterX, this.mCenterY, this.mRadius, this.mProgressStartColor, this.mProgressEndColor, Shader.TileMode.CLAMP);
                break;
            case 2:
                float radian = (float)((double)this.mProgressStrokeWidth / 3.141592653589793D * 2.0D / (double)this.mRadius);
                float rotateDegrees = (float)(-(this.mCap == Paint.Cap.BUTT && this.mStyle == 2 ? 0.0D : Math.toDegrees((double)radian)));
                shader = new SweepGradient(this.mCenterX, this.mCenterY, new int[]{this.mProgressStartColor, this.mProgressEndColor}, new float[]{0.0F, 1.0F});
                matrix = new Matrix();
                matrix.setRotate(rotateDegrees, this.mCenterX, this.mCenterY);
                ((Shader)shader).setLocalMatrix(matrix);
            }

            this.mProgressPaint.setShader((Shader)shader);
            this.mProgressStrokePaint.setShader((Shader)shader);
        } else {
            this.mProgressPaint.setShader((Shader)null);
            this.mProgressPaint.setColor(this.mProgressStartColor);
            this.mProgressStrokePaint.setShader((Shader)null);
            this.mProgressStrokePaint.setColor(this.mProgressStartColor);
        }

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate((float)this.mStartDegree, this.mCenterX, this.mCenterY);
        this.drawProgress(canvas);
        canvas.restore();
        this.drawProgressText(canvas);
    }

    private void drawShadow(Canvas canvas) {
        this.mProgressShadowPaint.setShadowLayer(this.mRadius, 0.0F, -10.0F, this.mProgressBackgroundColor);
    }

    private void drawProgressText(Canvas canvas) {
        if (this.mProgressFormatter != null) {
            CharSequence progressText = this.mProgressFormatter.format(this.mProgress, this.mMax);
            if (!TextUtils.isEmpty(progressText)) {
                this.mProgressTextPaint.setTextSize(this.mProgressTextSize);
                this.mProgressTextPaint.setColor(this.mProgressTextColor);
                this.mProgressTextPaint.getTextBounds(String.valueOf(progressText), 0, progressText.length(), this.mProgressTextRect);
                canvas.drawText(progressText, 0, progressText.length(), this.mCenterX, this.mCenterY + (float)(this.mProgressTextRect.height() / 2), this.mProgressTextPaint);
            }
        }
    }

    private void drawProgress(Canvas canvas) {
        switch(this.mStyle) {
        case 0:
        default:
            this.drawLineProgress(canvas);
            break;
        case 1:
            this.drawSolidProgress(canvas);
            break;
        case 2:
            this.drawSolidLineProgress(canvas);
        }

    }

    private void drawLineProgress(Canvas canvas) {
        float spacing = 10.0F;
        int lines = (int)(3.141592653589793D * (double)this.getHeight() / (double)spacing);
        float unitDegrees = (float)(6.283185307179586D / (double)lines);
        //float outerGreateCircleRadius = this.mRadius + 2.0F * this.mLineWidth;
        float outerCircleRadius = this.mRadius + this.mLineWidth;
        float interCircleRadius = this.mRadius;
        int progressLineCount = (int)((float)this.mProgress / (float)this.mMax * (float)lines);

        for(int i = 0; i < lines; ++i) {
            float rotateDegrees = (float)i * -unitDegrees;
            float startX = this.mCenterX + (float) Math.cos((double)rotateDegrees) * interCircleRadius;
            float startY = this.mCenterY - (float) Math.sin((double)rotateDegrees) * interCircleRadius;
            float stopX = this.mCenterX + (float) Math.cos((double)rotateDegrees) * outerCircleRadius;
            float stopY = this.mCenterY - (float) Math.sin((double)rotateDegrees) * outerCircleRadius;
            if (this.mDrawBackgroundOutsideProgress) {
                if (i >= progressLineCount) {
                    canvas.drawLine(startX, startY, stopX, stopY, this.mProgressBackgroundPaint);
                }
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, this.mProgressBackgroundPaint);
            }

            if (i < progressLineCount) {
                canvas.drawLine(startX, startY, stopX, stopY, this.mProgressPaint);
            }
            /*stopX = this.mCenterX + (float)Math.cos((double)rotateDegrees) * outerGreateCircleRadius;
            stopY = this.mCenterY - (float)Math.sin((double)rotateDegrees) * outerGreateCircleRadius;
            if (i < progressLineCount && i % 2 == 0) {
                canvas.drawLine(startX, startY, stopX, stopY, this.mProgressPaint);
            }*/
        }

        this.drawStrokeLineProgress(canvas);
    }

    private void drawStrokeLineProgress(Canvas canvas) {
        canvas.drawArc(this.mProgressRectF, 0.0F, 360.0F * (float)this.mProgress / (float)this.mMax, false, this.mProgressStrokePaint);
    }

    private void drawSolidProgress(Canvas canvas) {
        if (this.mDrawBackgroundOutsideProgress) {
            float startAngle = 360.0F * (float)this.mProgress / (float)this.mMax;
            float sweepAngle = 360.0F - startAngle;
            canvas.drawArc(this.mProgressRectF, startAngle, sweepAngle, true, this.mProgressBackgroundPaint);
        } else {
            canvas.drawArc(this.mProgressRectF, 0.0F, 360.0F, true, this.mProgressBackgroundPaint);
        }

        canvas.drawArc(this.mProgressRectF, 0.0F, 360.0F * (float)this.mProgress / (float)this.mMax, true, this.mProgressPaint);
    }

    private void drawSolidLineProgress(Canvas canvas) {
        if (this.mDrawBackgroundOutsideProgress) {
            float startAngle = 360.0F * (float)this.mProgress / (float)this.mMax;
            float sweepAngle = 360.0F - startAngle;
            canvas.drawArc(this.mProgressRectF, startAngle, sweepAngle, false, this.mProgressBackgroundPaint);
        } else {
            canvas.drawArc(this.mProgressRectF, 0.0F, 360.0F, false, this.mProgressBackgroundPaint);
        }

        canvas.drawArc(this.mProgressRectF, 0.0F, 360.0F * (float)this.mProgress / (float)this.mMax, false, this.mProgressPaint);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mCenterX = (float)(w / 2);
        this.mCenterY = (float)(h / 2);
        this.mRadius = Math.min(this.mCenterX, this.mCenterY);
        this.mProgressRectF.top = this.mCenterY - this.mRadius;
        this.mProgressRectF.bottom = this.mCenterY + this.mRadius;
        this.mProgressRectF.left = this.mCenterX - this.mRadius;
        this.mProgressRectF.right = this.mCenterX + this.mRadius;
        this.updateProgressShader();
        this.mProgressRectF.inset(this.mProgressStrokeWidth / 2.0F, this.mProgressStrokeWidth / 2.0F);
    }

    public void setProgressFormatter(ProgressFormatter progressFormatter) {
        this.mProgressFormatter = progressFormatter;
        this.invalidate();
    }

    public void setProgressStrokeWidth(float progressStrokeWidth) {
        this.mProgressStrokeWidth = progressStrokeWidth;
        this.mProgressRectF.inset(this.mProgressStrokeWidth / 2.0F, this.mProgressStrokeWidth / 2.0F);
        this.invalidate();
    }

    public void setProgressTextSize(float progressTextSize) {
        this.mProgressTextSize = progressTextSize;
        this.invalidate();
    }

    public void setProgressStartColor(int progressStartColor) {
        this.mProgressStartColor = progressStartColor;
        this.updateProgressShader();
        this.invalidate();
    }

    public void setProgressEndColor(int progressEndColor) {
        this.mProgressEndColor = progressEndColor;
        this.updateProgressShader();
        this.invalidate();
    }

    public void setProgressTextColor(int progressTextColor) {
        this.mProgressTextColor = progressTextColor;
        this.invalidate();
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.mProgressBackgroundColor = progressBackgroundColor;
        this.mProgressBackgroundPaint.setColor(this.mProgressBackgroundColor);
        this.invalidate();
    }

    public void setLineCount(int lineCount) {
        this.mLineCount = lineCount;
        this.invalidate();
    }

    public void setLineWidth(float lineWidth) {
        this.mLineWidth = lineWidth;
        this.invalidate();
    }

    public void setStyle(int style) {
        this.mStyle = style;
        this.mProgressPaint.setStyle(this.mStyle == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.mProgressBackgroundPaint.setStyle(this.mStyle == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.invalidate();
    }

    public void setShader(int shader) {
        this.mShader = shader;
        this.updateProgressShader();
        this.invalidate();
    }

    public void setCap(Paint.Cap cap) {
        this.mCap = cap;
        this.mProgressPaint.setStrokeCap(cap);
        this.mProgressBackgroundPaint.setStrokeCap(cap);
        this.invalidate();
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        this.invalidate();
    }

    public void setMax(int max) {
        this.mMax = max;
        this.invalidate();
    }

    public int getProgress() {
        return this.mProgress;
    }

    public int getMax() {
        return this.mMax;
    }

    public int getStartDegree() {
        return this.mStartDegree;
    }

    public void setStartDegree(int startDegree) {
        this.mStartDegree = startDegree;
        this.invalidate();
    }

    public boolean isDrawBackgroundOutsideProgress() {
        return this.mDrawBackgroundOutsideProgress;
    }

    public void setDrawBackgroundOutsideProgress(boolean drawBackgroundOutsideProgress) {
        this.mDrawBackgroundOutsideProgress = drawBackgroundOutsideProgress;
        this.invalidate();
    }

    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.progress = this.mProgress;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.setProgress(ss.progress);
    }

    public static class CircleProgressBarAnimation extends Animation {
        private CircleProgressBar mProgressBar;
        private int mTo;
        private int mFrom;
        private long mStepDuration;

        public CircleProgressBarAnimation(CircleProgressBar progressBar, long fullDuration) {
            this.mProgressBar = progressBar;
            this.mStepDuration = fullDuration / (long)progressBar.getMax();
        }

        public void setProgress(int progress) {
            if (progress < 0) {
                progress = 0;
            }

            if (progress > this.mProgressBar.getMax()) {
                progress = this.mProgressBar.getMax();
            }

            this.mTo = progress;
            this.mFrom = this.mProgressBar.getProgress();
            this.setDuration((long) Math.abs(this.mTo - this.mFrom) * this.mStepDuration);
            this.mProgressBar.startAnimation(this);
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float value = (float)this.mFrom + (float)(this.mTo - this.mFrom) * interpolatedTime;
            this.mProgressBar.setProgress((int)value);
        }
    }

    private static final class SavedState extends BaseSavedState {
        int progress;
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.progress = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.progress);
        }
    }

    private static final class DefaultProgressFormatter implements ProgressFormatter {
        private static final String DEFAULT_PATTERN = "%d%%";

        private DefaultProgressFormatter() {
        }

        public CharSequence format(int progress, int max) {
            return String.format("%d%%", (int)((float)progress / (float)max * 100.0F));
        }
    }

    public interface ProgressFormatter {
        CharSequence format(int var1, int var2);
    }
}