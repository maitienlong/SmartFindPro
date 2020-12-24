package com.poly.smartfindpro.ui.intro;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface IntroContract {
    interface ViewModel extends BaseView {

        void onShowDialogMsg(String msg);

        void onAccountNotAvail(String msg);

        void onNextLogin();

        void onNextHome();
    }

    interface Presenter extends BasePresenter {
        void getUpdateUser(String username, String password, boolean isLogin);
    }
}
