package com.poly.smartfindpro.ui.post.postsuccess;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.model.post.req.Address;

public interface SuccessPostContract {
    interface ViewModel extends BaseView {

        void onConfirm();
    }

    interface Presenter extends BasePresenter {
        void onConfirm();

        void onNext();
    }
}
