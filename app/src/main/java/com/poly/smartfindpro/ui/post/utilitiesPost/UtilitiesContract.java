package com.poly.smartfindpro.ui.post.utilitiesPost;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.ArrayList;
import java.util.List;

public interface UtilitiesContract {

    interface ViewModel extends BaseView {
      void onBackData(List<UtilitiesModel> arrayList);
        void onNext(String jsonData);
    }

    interface Presenter extends BasePresenter {

    }
}
