package com.poly.smartfindpro.basedatabind;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.poly.smartfindpro.base.BaseActivity;
import com.poly.smartfindpro.base.BaseFragment;


public abstract class BaseDataBindFragment<T extends ViewDataBinding, K> extends BaseFragment {
    protected T mBinding;
    protected K mPresenter;
    protected AppCompatActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container,
                false);

        initView();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter instanceof BaseViewModel) {
            ((BaseViewModel) mPresenter).subscribe();
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter instanceof BaseViewModel) {
            ((BaseViewModel) mPresenter).unSubscribe();
        }
        super.onDestroy();
    }

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
