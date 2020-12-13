package com.poly.smartfindpro.ui.post.adressPost;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.addressgoogle.Candidate;
import com.poly.smartfindpro.data.model.addressgoogle.Location;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.model.post.req.Address;

import java.util.List;

public interface AddressPostContract {
    interface ViewModel extends BaseView {
        void onShowProvince(ResultArea resultArea);

        void onShowDistrict(ResultArea resultArea);

        void onShowCommune(ResultArea resultArea);

        void onSubmitData(Address address, int status, List<Candidate> locationList);
    }

    interface Presenter extends BasePresenter {

        void getDataApiArea(int areaType, String jsonData);

        void onNext();

    }
}
