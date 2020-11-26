package com.poly.smartfindpro.ui.post.confirmPost;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentConfirmPostBinding;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.model.InforModel;
import com.poly.smartfindpro.ui.post.model.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;
//import com.poly.smartfindpro.ui.post.model.Address;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfirmPostFragment extends BaseDataBindFragment<FragmentConfirmPostBinding, ConfirmPostPresenter> {
    private InforModel inforModel = null;
    private Address addressModel = null;
    private List<UtilitiesModel> utilitiesModelList = new ArrayList<>();

    private PostRequest postRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_post;
    }

    private void rData(){
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);
    }

    @Override
    protected void initData() {
        inforModel = ((PostActivity) getActivity()).getDataInforModel();
        utilitiesModelList = ((PostActivity) getActivity()).getListUtilitiesModel();
      //  addressModel = ((PostActivity) getActivity()).getDataAddress();
        if (inforModel != null) {

            mBinding.strCategory.setText(inforModel.getCategory());
            mBinding.strAmount.setText(inforModel.getNumPerson());
            mBinding.strPrice.setText(inforModel.getMonthlyPrice());
            mBinding.strDeposit.setText(inforModel.getDeposit());
            mBinding.strGender.setText(inforModel.getGender());
            mBinding.strAddress.setText(inforModel.getAddress());
            mBinding.strElectricbill.setText(inforModel.getElectricBill());
            mBinding.strWaterbill.setText(inforModel.getWaterBill());
            mBinding.strDescriptiondetail.setText(inforModel.getDescription());
        }

        if (utilitiesModelList != null) {
            Log.d("Choanh", "-- " + utilitiesModelList.size());
            String utilities = utilitiesModelList.get(0).getTitle();
            for (int i = 1; i < utilitiesModelList.size(); i++) {
                utilities += " ; " + utilitiesModelList.get(i).getTitle();
            }
            mBinding.strUtilities.setText(utilities);
        }
//        if (addressModelList != null) {
//
//        }
    }

    @Override
    protected void initView() {

    }
}

