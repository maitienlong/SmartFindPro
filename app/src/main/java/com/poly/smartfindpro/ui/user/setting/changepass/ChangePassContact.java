package com.poly.smartfindpro.ui.user.setting.changepass;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface ChangePassContact {
    interface ViewModel extends BaseView {
        void onBackClick();
    }

    interface Presenter extends BasePresenter {
        void onBackClick();
    }
}
