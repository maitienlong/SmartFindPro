package com.poly.smartfindpro.ui.user.userFragment;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface UserContact {

    interface ViewModel extends BaseView {
        void onClickProfile();

        void onClickSetting();

        void onClickRules();

        void onClickHelp();

        void onClickLogOut();
    }

    interface Presenter extends BasePresenter {
        void onClickProfile();

        void onClickSetting();

        void onClickRules();

        void onClickHelp();

        void onClickLogOut();
    }
}
