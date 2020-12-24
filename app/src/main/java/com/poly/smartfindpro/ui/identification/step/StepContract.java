package com.poly.smartfindpro.ui.identification.step;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface StepContract {
    interface ViewModel extends BaseView {
        void onTakeCMNDTruoc();

        void onTakeCMNDSau();

        void onNextVeriFace(String jsonData);
    }

    interface Presenter extends BasePresenter {
        void onTakeCMNDTruoc();

        void onTakeCMNDSau();

        void onSubmit();


        void setMaxLength(int number);
    }
}
