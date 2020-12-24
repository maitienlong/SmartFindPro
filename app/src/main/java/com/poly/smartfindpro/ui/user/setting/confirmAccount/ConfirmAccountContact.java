package com.poly.smartfindpro.ui.user.setting.confirmAccount;

public interface ConfirmAccountContact {

    public interface ViewModel {
        void onBackClick();
        void onConfirm();

    }
    public interface Presenter {
        void onBackClick();

        void onConfirm();

    }


}
