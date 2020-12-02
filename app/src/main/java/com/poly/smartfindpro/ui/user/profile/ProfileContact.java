package com.poly.smartfindpro.ui.user.profile;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface ProfileContact {

    interface ViewModel extends BaseView {
        void onBackClick();

        void onClickEditUser();
    }

    interface Presenter extends BasePresenter {
        void onBackClick();

        void onClickEditUser();
    }
}
