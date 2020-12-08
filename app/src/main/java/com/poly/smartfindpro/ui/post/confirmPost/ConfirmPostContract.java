package com.poly.smartfindpro.ui.post.confirmPost;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;

public interface ConfirmPostContract {
    interface ViewModel extends BaseView {
        void showLoadingDialog();

        void onConfirm();
    }

    interface Presenter extends BasePresenter {
        void requestUploadSurvey();

        void onRequestToServer();
    }
}
