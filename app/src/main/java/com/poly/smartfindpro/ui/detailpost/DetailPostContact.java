package com.poly.smartfindpro.ui.detailpost;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface DetailPostContact {
    interface ViewModel extends BaseView{
        void onBackClick();
    }

    interface Presenter extends BasePresenter {
        void onBackClick();
    }
}
