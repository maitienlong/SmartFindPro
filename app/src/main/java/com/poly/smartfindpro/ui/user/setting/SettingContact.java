package com.poly.smartfindpro.ui.user.setting;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface SettingContact {

    interface ViewModel extends BaseView {
        void onBackClick();
        void onClickInformation();
        void onClickChangePassword();
        void onClickPerMission();
    }

    interface Presenter extends BasePresenter {
        void onBackClick();
        void onClickInformation();
        void onClickChangePassword();
        void onClickPerMission();
    }
}
