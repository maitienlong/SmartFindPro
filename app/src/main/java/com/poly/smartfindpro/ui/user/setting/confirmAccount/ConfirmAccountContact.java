package com.poly.smartfindpro.ui.user.setting.confirmAccount;

public interface ConfirmAccountContact {

    public interface ViewModel {
        void onBackClick();

        void onConfirm();

        boolean onSaveLevel(String username, String password, String token, int level, String tokenDevice);

    }

    public interface Presenter {
        void onBackClick();

        void onConfirm();

    }


}
