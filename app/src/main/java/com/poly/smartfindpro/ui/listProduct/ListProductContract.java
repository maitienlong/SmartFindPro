                     package com.poly.smartfindpro.ui.listProduct;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface ListProductContract {
    interface ViewModel extends BaseView {
        void openFragment();
    }

    interface Presenter extends BasePresenter {

    }
}
