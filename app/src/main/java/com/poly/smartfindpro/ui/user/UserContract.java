package com.poly.smartfindpro.ui.user;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface UserContract {
    interface ViewModel extends BaseView {
        void openFragment();
    }

    interface Presenter extends BasePresenter {

    }
}
