package com.poly.smartfindpro.ui.post.adressPost.chooselocation;

import android.content.Context;

import com.poly.smartfindpro.databinding.FragmentMapsSearchBinding;


public class ChooseLoactionPresenter implements ChooseLocationContract.Presenter {

    private Context mContext;
    private ChooseLocationContract.ViewModel mViewModel;

    private FragmentMapsSearchBinding mBinding;

    public ChooseLoactionPresenter(Context context, ChooseLocationContract.ViewModel viewModel, FragmentMapsSearchBinding binding) {
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
