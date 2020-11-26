package com.poly.smartfindpro.ui.post.adressPost;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.area.result.ResultArea;

public interface AddressPostContract {
    interface ViewModel extends BaseView {
        void onShowProvince(ResultArea resultArea);

        void onShowDistrict(ResultArea resultArea);

        void onShowCommune(ResultArea resultArea);

        void onSubmitData();
    }

    interface Presenter extends BasePresenter {

        void getDataApiArea(int areaType, String jsonData);

        void onNext();

    }
}
