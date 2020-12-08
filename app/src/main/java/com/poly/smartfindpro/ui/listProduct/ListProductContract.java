package com.poly.smartfindpro.ui.listProduct;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.product.res.Products;

import java.util.List;

public interface ListProductContract {
    interface ViewModel extends BaseView {
        void openFragment();
//        void onShow(List<Products> productList);
    }

    interface Presenter extends BasePresenter {

    }
}
