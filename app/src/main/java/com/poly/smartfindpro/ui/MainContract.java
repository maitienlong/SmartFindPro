package com.poly.smartfindpro.ui;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface MainContract {
    interface ViewModel extends BaseView {

        void onSelectHome();

        void onSelecFind();

        void onSelectMessager();

        void onSelectUser();
    }

    interface Presenter extends BasePresenter {
        void onSelectHome();

        void onSelecFind();

        void onSelectMessager();

        void onSelectUser();
    }
}
