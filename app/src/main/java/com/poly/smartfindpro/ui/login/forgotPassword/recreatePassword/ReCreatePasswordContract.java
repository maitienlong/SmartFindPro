package com.poly.smartfindpro.ui.login.forgotPassword.recreatePassword;

import android.text.TextWatcher;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface ReCreatePasswordContract {
    interface ViewModel extends BaseView {
        void OnBackClick();
    }

    interface Presenter extends BasePresenter {
        void OnBackClick();

    }
}
