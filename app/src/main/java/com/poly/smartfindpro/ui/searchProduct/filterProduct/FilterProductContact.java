package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import com.poly.smartfindpro.data.model.home.res.Product;

import java.util.List;

public interface FilterProductContact {
    public interface ViewModel {
        void onShow(List<Product> products);

        void onClickFilter();
    }

    public interface Presenter {
        void onClickFilter();
    }
}
