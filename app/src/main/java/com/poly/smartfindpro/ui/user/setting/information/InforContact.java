package com.poly.smartfindpro.ui.user.setting.information;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface InforContact {

    interface ViewModel extends BaseView {
        void onBackClick();
        void onCLickUpdate();
        void onClickAddress();

    }

    interface Presenter extends BasePresenter {
        void onBackClick();
        void onCLickUpdate();
        void onClickAddress();
    }
}
