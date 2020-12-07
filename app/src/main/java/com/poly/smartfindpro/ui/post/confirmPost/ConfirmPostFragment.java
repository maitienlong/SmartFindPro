package com.poly.smartfindpro.ui.post.confirmPost;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentConfirmPostBinding;
import com.poly.smartfindpro.ui.post.adapter.ShowImagePostAdapter;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.ui.post.postsuccess.SuccessPostFragment;
//import com.poly.smartfindpro.data.model.post.req.Address;

import java.lang.reflect.Type;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ConfirmPostFragment extends BaseDataBindFragment<FragmentConfirmPostBinding, ConfirmPostPresenter> implements ConfirmPostContract.ViewModel {
    private PostRequest postRequest;

    private ShowImagePostAdapter showImagePostAdapter;

    private List<ImageInforPost> imageList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_post;
    }

    private void reciveData() {
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        Type typeImage = new TypeToken<List<ImageInforPost>>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);

        imageList = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES_PHOTO), typeImage);
    }

    @Override
    protected void initData() {
        mPresenter = new ConfirmPostPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

        if (postRequest != null) {

            showImagePostAdapter = new ShowImagePostAdapter(mActivity);

            onShowImage();

            mPresenter.setPostRequest(postRequest, imageList);

            mPresenter.setTheLoai(postRequest.getCategory());

            mPresenter.setSoLuong(postRequest.getInformation().getAmountPeople());

            mPresenter.setGia(postRequest.getInformation().getPrice());

            mPresenter.setDatCoc(postRequest.getInformation().getDeposit());

            mPresenter.setGioiTinh(postRequest.getInformation().getGender());

            mPresenter.setDiaChi(postRequest.getAddress().getDetailAddress() + ", " + postRequest.getAddress().getCommuneWardTown() + ", " + postRequest.getAddress().getDistrictsTowns() + ", " + postRequest.getAddress().getProvinceCity());

            mPresenter.setTienDien(postRequest.getInformation().getElectricBill());

            mPresenter.setTienNuoc(postRequest.getInformation().getElectricBill());

            mPresenter.setTienIch(postRequest.getUtilities());

            mPresenter.setMoTa(postRequest.getInformation().getDescribe());
        }
    }

    @Override
    protected void initView() {
        reciveData();
    }

    public void onShowImage() {
        showImagePostAdapter.setItemView(imageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        mBinding.rcPhoto.setLayoutManager(linearLayoutManager);
        mBinding.rcPhoto.setAdapter(showImagePostAdapter);

    }

    @Override
    public void showLoadingDialog() {
        super.showLoadingDialog();
    }

    @Override
    public void onConfirm() {
        Intent intent = new Intent();

        intent.putExtra(Config.DATA_CALL_BACK, "5");

        setResult(RESULT_OK, intent);

        onBackData();

        getBaseActivity().goToFragment(R.id.fl_post, new SuccessPostFragment(), null);
    }
}

