package com.poly.smartfindpro.ui.user.profile;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.product.res.Products;

import java.util.List;

public interface ProfileContact {

    interface ViewModel extends BaseView {
        void onBackClick();

        void onShow(List<Products> productList);

        void onClickEditUser();

        void onClickPending();

        void onClickApproved();

        void onCallback(int type, String idPost, String jsonData);

        void onUpdate(String jsonData);

        void onGetTotalPeople(String idPost, String amount);

        void setAmountPeople(String amount);


    }

    interface Presenter extends BasePresenter {
        void onBackClick();

        void onClickEditUser();

        void onClickPending();

        void onClickApproved();

        void onGetTotalPeople();

        void setAmountPeople(String amount);

    }
}
