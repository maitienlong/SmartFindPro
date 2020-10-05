package com.poly.smartfindpro.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.poly.smartfindpro.R;


public class MainToolbar extends RelativeLayout {

    private Context mContext;

    private Toolbar toolbar;

    private TextView tvTitle;

    public MainToolbar(Context context) {
        super(context);

        initUI(context, null, 0);
    }

    public MainToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initUI(context, attrs, 0);
    }

    public MainToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initUI(context, attrs, defStyleAttr);
    }

    public Toolbar getRoot() {
        return toolbar;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    private void initUI(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        inflate(context, R.layout.layout_custom_main_toolbar, this);
        toolbar = findViewById(R.id.container);
        tvTitle = findViewById(R.id.tv_title);
    }
}
