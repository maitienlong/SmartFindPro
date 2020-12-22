package com.poly.smartfindpro.ui.login;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface LoginContract {
    interface ViewModel extends BaseView {
        void openForgotPassword();
    }

    interface Presenter extends BasePresenter {
        void openForgotPassword();
    }
}


