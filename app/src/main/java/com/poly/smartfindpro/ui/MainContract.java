package com.poly.smartfindpro.ui;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface MainContract {
    interface ViewModel extends BaseView {
        void openFragment();
    }

    interface Presenter extends BasePresenter {

    }
}
