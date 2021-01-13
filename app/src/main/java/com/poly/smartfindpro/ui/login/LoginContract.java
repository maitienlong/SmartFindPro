package com.poly.smartfindpro.ui.login;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface LoginContract {
    interface ViewModel extends BaseView {
        void onForgotClick();
    }

    interface Presenter extends BasePresenter {

    }
}


