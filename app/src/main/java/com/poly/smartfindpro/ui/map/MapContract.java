package com.poly.smartfindpro.ui.map;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface MapContract {
    interface ViewModel extends BaseView {
        void openFragment();
    }

    interface Presenter extends BasePresenter {

    }
}
