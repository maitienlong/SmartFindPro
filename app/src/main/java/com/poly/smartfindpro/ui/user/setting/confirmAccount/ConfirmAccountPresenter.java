package com.poly.smartfindpro.ui.user.setting.confirmAccount;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.user.setting.changepass.ChangePassContact;

public class ConfirmAccountPresenter implements ConfirmAccountContact.Presenter {
    private Context context;
    private ConfirmAccountContact.ViewModel mViewModel;

    public ObservableField<String> titleButton;
    public ConfirmAccountPresenter(Context context, ConfirmAccountContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData(){
        titleButton = new ObservableField<>();
    }

    public void setTitleButton(String title){
        titleButton.set(title);
    }

    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void onConfirm() {
        mViewModel.onConfirm();
    }

    public void setType() {

    }
}
