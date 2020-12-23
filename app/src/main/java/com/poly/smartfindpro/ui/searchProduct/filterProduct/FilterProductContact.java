package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import com.poly.smartfindpro.data.model.home.res.Product;
import com.poly.smartfindpro.data.model.product.res.Products;

import java.util.List;

public interface FilterProductContact {
    public interface ViewModel {
        void onShow(List<Products> products);

        void onClickFilter();

        void onShowMsg(String msg);

        void onBackClick();
    }

    public interface Presenter {
        void onClickFilter();

        void onBackClick();

        void onClickFilter(FilterTool filterTool, List<String> priority);
    }
}
