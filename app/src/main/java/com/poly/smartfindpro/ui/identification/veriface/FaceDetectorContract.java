package com.poly.smartfindpro.ui.identification.veriface;


import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface FaceDetectorContract {
    interface ViewModel extends BaseView {

        void onBackClick();

    }

    interface Presenter extends BasePresenter {


    }
}
