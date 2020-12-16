package com.poly.smartfindpro.ui.intro;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface IntroContract {
    interface ViewModel extends BaseView {
        void onNextSceen();

        void onShowDialogMsg(String msg);
    }

    interface Presenter extends BasePresenter {
        void getUpdateUser();
    }
}
