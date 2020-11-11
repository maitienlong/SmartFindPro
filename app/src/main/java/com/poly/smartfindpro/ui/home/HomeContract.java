package com.poly.smartfindpro.ui.home;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface HomeContract {
    interface ViewModel extends BaseView {
        void onBackClick();
    }

    interface Presenter extends BasePresenter {

    }
}
