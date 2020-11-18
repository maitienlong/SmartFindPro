package com.poly.smartfindpro.basedatabind;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.poly.smartfindpro.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDataBindActivity<T extends ViewDataBinding, K> extends BaseActivity implements View.OnClickListener {

    protected T mBinding;
    protected K mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initData();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter instanceof BasePresenter) {
            ((BasePresenter) mPresenter).subscribe();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter instanceof BasePresenter) {
            ((BasePresenter) mPresenter).unSubscribe();
        }
    }

    @Override
    public void onClick(View v) {

    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setListViewOnClick(ArrayList<View> listV){
        if(listV != null && listV.size() > 0){
            listV.forEach((n) -> {
                n.setOnClickListener(this);
            });
        }
    }
}
