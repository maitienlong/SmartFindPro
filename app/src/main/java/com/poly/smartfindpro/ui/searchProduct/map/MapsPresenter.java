package com.poly.smartfindpro.ui.searchProduct.map;

import android.content.Context;

import com.poly.smartfindpro.databinding.FragmentMapsBinding;


public class MapsPresenter implements MapsContract.Presenter {

    private Context mContext;
    private MapsContract.ViewModel mViewModel;

    private FragmentMapsBinding mBinding;

    public MapsPresenter(Context context, MapsContract.ViewModel viewModel, FragmentMapsBinding binding) {
        mContext = context;
        mViewModel = viewModel;
        this.mBinding = binding;
        initData();
    }

    private void initData() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


}
