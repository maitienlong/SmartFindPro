package com.poly.smartfindpro.ui.identification.veriface;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface FaceDetectorContract {
    interface ViewModel extends BaseView {

        void onBackClick();

        void onSuccess(String msg);

        void onFail(String msg);

    }

    interface Presenter extends BasePresenter {


    }
}
