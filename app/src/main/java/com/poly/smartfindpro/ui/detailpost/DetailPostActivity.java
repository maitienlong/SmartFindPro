package com.poly.smartfindpro.ui.detailpost;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailPostActivity extends BaseDataBindActivity<ActivityInformationPostBinding, DetailPostPresenter>
        implements DetailPostContact.ViewModel {

    private DetailImageAdapter adapter;
    private List<String> imageList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_post;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            imageList.add("image"+ i);
        }

        adapter = new DetailImageAdapter(this, imageList);
        mBinding.rvListImage.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        mBinding.rvListImage.setAdapter(adapter);

    }
}