package com.poly.smartfindpro.ui.login.forgotPassword;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface ForgotPasswordContract {
    interface ViewModel extends BaseView {
        void OnBackClick();
        void checkData(String phone);
    }

    interface Presenter extends BasePresenter {
        void OnBackClick();
        void onClickForgot();
    }
}
