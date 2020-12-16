package com.poly.smartfindpro.ui.login.registerFragment;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface RegisterContract {
    interface ViewModel extends BaseView {
        void checkNumber(String jsonData, String phone);

    }

    interface Presenter extends BasePresenter {
        void onClickRegister();
    }
}
