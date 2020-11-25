package com.poly.smartfindpro.ui.post.adressPost;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.area.result.ResultArea;

public interface AddressPostContract {
    interface ViewModel extends BaseView {
        void onShowDistrict(ResultArea resultArea);

        void onShowCommune(ResultArea resultArea);
    }

    interface Presenter extends BasePresenter {

        void getDataApiArea(int areaType, String jsonData);

    }
}
