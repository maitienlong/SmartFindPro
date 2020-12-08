package com.poly.smartfindpro.ui.home;

import android.annotation.SuppressLint;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.model.home.res.Product;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.FragmentHomeBinding;
import com.poly.smartfindpro.ui.home.adapter.HomeAdapter;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.adapter.MainSliderAdapter;
import com.poly.smartfindpro.ui.post.adapter.PicassoImageLoadingService;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class HomeFragment extends BaseDataBindFragment<FragmentHomeBinding, HomePresenter> implements HomeContract.ViewModel {
    private HomeAdapter homeAdapter;
//    private ArrayList<Product> product = new ArrayList<>();

    private List<String> mListImage;

    private MainSliderAdapter mainSliderAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mPresenter = new HomePresenter(mActivity, this,mBinding);
        mBinding.setPresenter(mPresenter);
        mListImage = new ArrayList<>();
        mListImage.add("https://puta.edu.vn/wp-content/uploads/2020/01/Free-Vector-Hoa-Mai-Hoa-%C4%90%C3%A0o-ng%C3%A0y-t%E1%BA%BFt-th%C3%AAm-r%E1%BB%B1c-r%E1%BB%A1-9.png");
        mListImage.add("https://tronghoa.vn/wp-content/uploads/2018/10/Hoa-dao.jpg");
        mListImage.add("https://images.kienthuc.net.vn/zoom/800/Uploaded/ngoclinh/2015_12_17/NewFolder1/vnpa4vtxv_-_copy_CYMU.jpg");

        PicassoImageLoadingService picassoImageLoadingService = new PicassoImageLoadingService(mActivity);
        Slider.init(picassoImageLoadingService);
        mBinding.sliderHome.setAdapter(new MainSliderAdapter(mListImage));


    }

    @Override
    protected void initData() {
        mPresenter = new HomePresenter(mActivity, this,mBinding);
        mBinding.setPresenter(mPresenter);
        homeAdapter = new HomeAdapter(mActivity, mActivity.getSupportFragmentManager());
    }

    @Override
    public void onBackClick() {

    }

    @Override
    public void onShow(List<Product> productList) {
        homeAdapter.setListItem(productList);
        BindingUtils.setAdapter(mBinding.rvList, homeAdapter, true);
    }


    @Override
    public void openPost() {
        getBaseActivity().openActivity(PostActivity.class);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClickRentalRoom() {
//        mBinding.btnRentalRoom.setBackgroundColor(R.color.blue);
//        mBinding.btnRentalRoom.setCardBackgroundColor(R.color.blue);
//        mBinding.btnRentalRoom.setRadius(R.color.blue);
////        mBinding.tvRentalRoom.
//        mBinding.btnShareRoom.setBackgroundColor(R.color.white);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClickShareRoom() {
//
    }

}