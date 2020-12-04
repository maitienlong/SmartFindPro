package com.poly.smartfindpro.ui.user.profile;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.product.res.Product;

import java.util.List;

public interface ProfileContact {

    interface ViewModel extends BaseView {
        void onBackClick();
        void onShow(List<Product> productList);

        void onClickEditUser();
    }

    interface Presenter extends BasePresenter {
        void onBackClick();

        void onClickEditUser();
    }
}
