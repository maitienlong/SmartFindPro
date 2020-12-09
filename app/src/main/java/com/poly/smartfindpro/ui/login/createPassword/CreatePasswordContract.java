package com.poly.smartfindpro.ui.login.createPassword;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface CreatePasswordContract {
    interface ViewModel extends BaseView {
        void OnBackClick();

//        void onClickRegister();

    }

    interface Presenter extends BasePresenter {
        void onClickRegister();
    }


}
