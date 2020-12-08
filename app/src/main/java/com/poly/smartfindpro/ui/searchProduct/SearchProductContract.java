package com.poly.smartfindpro.ui.searchProduct;


import android.widget.Adapter;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.product.res.Products;

import java.util.List;

public interface SearchProductContract {
    interface ViewModel extends BaseView {
        void onShow(List<Products> products);

    }

    interface Presenter extends BasePresenter {

        void onSearch();
    }
}
