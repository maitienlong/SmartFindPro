package com.poly.smartfindpro.ui.post.inforPost;

import android.net.Uri;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface InforPostContract {

    interface ViewModel extends BaseView {
        void onNextFragment();
        void onErrorCategory();
        void onErrorInfor();
        void onErrorGender();
        void onShowPhoto();
    }

    interface Presenter extends BasePresenter {
        void openChoosePhoto();
    }
}
