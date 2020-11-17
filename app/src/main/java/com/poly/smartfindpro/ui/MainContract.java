package com.poly.smartfindpro.ui;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface MainContract {
    interface ViewModel extends BaseView {
        void onNextFragment();
        void onErrorCategory();
        void onErrorInfor();
        void onErrorGender();
    }

    interface Presenter extends BasePresenter {

    }
}
