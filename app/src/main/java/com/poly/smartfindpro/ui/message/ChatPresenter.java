package com.poly.smartfindpro.ui.message;

import android.content.Context;

import com.poly.smartfindpro.databinding.ActivityMessageBinding;

public class ChatPresenter implements ChatContact.ViewModel {

    private Context context;
    private ChatContact.ViewModel mViewModel;
    private ActivityMessageBinding mBinding;

    public ChatPresenter(Context context, ChatContact.ViewModel mViewModel, ActivityMessageBinding binding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = binding;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }
}
