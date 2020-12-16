package com.poly.smartfindpro.ui.login.otp;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface ConfirmOTPContract {
    interface ViewModel extends BaseView {
        void onClickConfirm();

        void onResendOTP();
    }

    interface Presenter extends BasePresenter {
        void onClickConfirm();

        void onResendOTP();
    }
}
