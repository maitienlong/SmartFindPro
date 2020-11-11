package com.poly.smartfindpro.ui.searchProduct;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface SearchProductContract {
    interface ViewModel extends BaseView {
        void openFragment();
    }

    interface Presenter extends BasePresenter {

    }
}
