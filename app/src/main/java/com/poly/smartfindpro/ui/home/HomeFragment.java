package com.poly.smartfindpro.ui.home;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentHomeBinding;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.adapter.MainSliderAdapter;
import com.poly.smartfindpro.ui.post.adapter.PicassoImageLoadingService;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class HomeFragment extends BaseDataBindFragment<FragmentHomeBinding,
        HomePresenter> implements HomeContract.ViewModel {
    private HomeAdapter homeAdapter;
    private ArrayList<Product> product = new ArrayList<>();
    private ArrayList<String> productTest = new ArrayList<String>();

    private List<String> mListImage;

    private MainSliderAdapter mainSliderAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mPresenter = new HomePresenter(mActivity, this);
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
        homeAdapter = new HomeAdapter(mActivity);
        for (int i = 0; i < 10; i++) {
            productTest.add("namdeptrai" + i);
        }
        homeAdapter.setListItemTest(productTest);

        BindingUtils.setAdapter(mBinding.rvList, homeAdapter, false);
        // mPresenter. ... goi nhung thanh phan ben Presenter

    }

    @Override
    public void onBackClick() {
        // Cai nay la o ben Controctor khai bao
    }

    @Override
    public void openPost() {
        getBaseActivity().openActivity(PostActivity.class);
    }

}