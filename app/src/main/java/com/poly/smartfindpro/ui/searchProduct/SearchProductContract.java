package com.poly.smartfindpro.ui.searchProduct;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.product.Product_;

import java.util.List;

public interface SearchProductContract {
    interface ViewModel extends BaseView {
        void openFragment();

        void onShow(List<Product_> products);
    }

    interface Presenter extends BasePresenter {

    }
}
