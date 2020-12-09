package com.poly.smartfindpro.ui.login.loginFragment;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.home.res.Product;

import java.util.List;

public interface LoginFragmentContract {
    interface ViewModel extends BaseView {
        void saveLogin(String username, String password, String token);


    }

    interface Presenter extends BasePresenter {
        void sendRequestUser();
    }
}
