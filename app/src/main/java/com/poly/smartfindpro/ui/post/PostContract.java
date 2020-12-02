package com.poly.smartfindpro.ui.post;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface PostContract {

    interface ViewModel extends BaseView {
        void statusProress(String isStatus);
    }

    interface Presenter extends BasePresenter {
        void nextFragment(String isStatus, String jsonData, String jsonPhoto);
    }
}
