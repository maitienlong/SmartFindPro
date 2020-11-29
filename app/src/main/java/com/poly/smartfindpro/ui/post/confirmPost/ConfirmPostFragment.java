package com.poly.smartfindpro.ui.post.confirmPost;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentConfirmPostBinding;
import com.poly.smartfindpro.ui.post.adapter.ShowImagePostAdapter;
import com.poly.smartfindpro.ui.post.model.ImageInforPost;
import com.poly.smartfindpro.ui.post.model.PostRequest;
//import com.poly.smartfindpro.ui.post.model.Address;

import java.lang.reflect.Type;
import java.util.List;

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
        mPresenter = new ConfirmPostPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);

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
}

