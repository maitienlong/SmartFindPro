package com.poly.smartfindpro.ui.post.confirmPost;

import android.util.Log;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentConfirmPostBinding;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.model.InforModel;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.ArrayList;
import java.util.List;

public class ConfirmPostFragment extends BaseDataBindFragment<FragmentConfirmPostBinding, ConfirmPostPresenter> {
    private InforModel inforModel = null;
    private List<UtilitiesModel>  utilitiesModelList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_post;
    }

    @Override
    protected void initData() {
        inforModel = ((PostActivity) getActivity()).getDataInforModel();
        utilitiesModelList = ((PostActivity) getActivity()).getListUtilitiesModel();
        if(inforModel != null) {
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

        if(utilitiesModelList != null) {
            Log.d("Choanh", "-- " + utilitiesModelList.size());
            String utilities = utilitiesModelList.get(0).getTitle();
            for (int i = 1; i < utilitiesModelList.size(); i++){
                utilities += " ; " + utilitiesModelList.get(i).getTitle();
            }
            mBinding.strUtilities.setText(utilities);
        }
    }

    @Override
    protected void initView() {

    }
}

