package com.poly.smartfindpro.ui.user.setting.confirmAccount;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.user.setting.changepass.ChangePassContact;

public class ConfirmAccountPresenter implements ConfirmAccountContact.Presenter {
    private Context context;
    private ConfirmAccountContact.ViewModel mViewModel;

    public ConfirmAccountPresenter(Context context, ConfirmAccountContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }

    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }
    public void setType(){

    }
}
