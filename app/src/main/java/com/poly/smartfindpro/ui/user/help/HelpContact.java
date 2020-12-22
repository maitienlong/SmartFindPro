package com.poly.smartfindpro.ui.user.help;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface HelpContact {

    interface ViewModel extends BaseView {
        void onBackClick();

        void onCallClick();
    }

    interface Presenter extends BasePresenter {
        void onBackClick();

        void onCallClick();
    }
}
