package com.poly.smartfindpro.ui.searchProduct;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.product.res.Products;

import java.util.List;

public interface SearchProductContract {
    interface ViewModel extends BaseView {
        void onShow(List<Products> products);

        void onSearch();

        void onShowResult(List<Products> productsList, int code);

        void onResultAdapter(String tag);

        void onSelectTypeFilter();

        void filterAdvance(String jsonData);
    }

    interface Presenter extends BasePresenter {

        void onSearch();

        void onDataCallBackMap(String tag);

        void onResultAdapter(String tag);

        void onSelectTypeFilter();

        void filterAddress();

        void filterPrice();

        void filterAdvance();
    }
}
