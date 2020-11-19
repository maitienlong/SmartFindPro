package com.poly.smartfindpro.ui.post.utilitiesPost;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentAddressPostBinding;
import com.poly.smartfindpro.databinding.FragmentUtilitiesPostBinding;
import com.poly.smartfindpro.ui.post.adapter.UtilitiesAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostContract;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostPresenter;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.List;

public class UtilitiesPostFragment extends BaseDataBindFragment<FragmentUtilitiesPostBinding, UtilitiesPresenter> implements UtilitiesContract.ViewModel, View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_utilities_post;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {
        mPresenter = new UtilitiesPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);

        mPresenter.CreateData();
        UtilitiesAdapter utilitiesAdapter = new UtilitiesAdapter(mActivity);
        utilitiesAdapter.setListItem(mPresenter.CreateData());

        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity,2);
        mBinding.rcUtilities.setAdapter(utilitiesAdapter);
        mBinding.rcUtilities.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {

    }
}
