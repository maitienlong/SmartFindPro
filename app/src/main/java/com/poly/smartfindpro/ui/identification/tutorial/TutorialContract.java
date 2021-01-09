package com.poly.smartfindpro.ui.identification.tutorial;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface TutorialContract {
    interface ViewModel extends BaseView {
        void onClickConfirm();
        void onBackClick();
    }

    interface Presenter extends BasePresenter {
       void onClickConfirm();
        void onBackClick();
    }
}
