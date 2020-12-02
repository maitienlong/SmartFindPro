package com.poly.smartfindpro.ui.user.setting.permission;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface PermissionContact {
    interface ViewModel extends BaseView {
        void onBackClick();
    }

    interface Presenter extends BasePresenter {
        void onBackClick();
    }
}
