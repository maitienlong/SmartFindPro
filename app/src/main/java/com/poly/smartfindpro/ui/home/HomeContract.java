package com.poly.smartfindpro.ui.home;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.home.res.Product;

import java.util.List;

public interface HomeContract {
    interface ViewModel extends BaseView {
        void onBackClick();
        void onShow(List<Product> productList);
        void openPost();
    }

    interface Presenter extends BasePresenter {
        void openPost();
    }
}
