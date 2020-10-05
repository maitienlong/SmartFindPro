package com.poly.smartfindpro.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.poly.smartfindpro.R;


public class CommonToolbar extends RelativeLayout {

    private FrameLayout flBack, flClose;

    private TextView tvTitle;

    public CommonToolbar(Context context) {
        super(context);
        initUI(context, null, 0);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI(context, attrs, 0);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context, attrs, defStyleAttr);
    }

    public void setOnBackClickListener(OnClickListener listener) {
        flBack.setOnClickListener(listener);
    }

    public void setOnCloseClickListener(OnClickListener listener) {
        flClose.setOnClickListener(listener);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void hideBackIcon(boolean hide) {
        flBack.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    public void invisibleBackIcon(boolean invisible) {
        flBack.setVisibility(invisible ? View.INVISIBLE : View.VISIBLE);
        flBack.setEnabled(!invisible);
    }

    public void hiddenCloseIcon(boolean hidden) {
        flClose.setVisibility(hidden ? View.GONE : View.VISIBLE);
    }

    public void showCloseIcon(boolean show) {
        flClose.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void initUI(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_custom_common_toolbar, this);
        flBack = findViewById(R.id.fl_back);
        tvTitle = findViewById(R.id.tv_title);
        flClose = findViewById(R.id.fl_close);
    }
}
