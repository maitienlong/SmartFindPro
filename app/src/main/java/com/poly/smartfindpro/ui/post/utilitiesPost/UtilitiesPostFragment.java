package com.poly.smartfindpro.ui.post.utilitiesPost;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentAddressPostBinding;
import com.poly.smartfindpro.databinding.FragmentUtilitiesPostBinding;
import com.poly.smartfindpro.ui.post.adapter.UtilitiesAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostContract;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostPresenter;
import com.poly.smartfindpro.ui.post.confirmPost.ConfirmPostFragment;
import com.poly.smartfindpro.ui.post.model.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UtilitiesPostFragment extends BaseDataBindFragment<FragmentUtilitiesPostBinding, UtilitiesPresenter> implements UtilitiesContract.ViewModel {
    private PostRequest postRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_utilities_post;
    }

    private void ReciData() {
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);

    }

    @Override
    protected void initView() {
        ReciData();

    }

    @Override
    protected void initData() {
        mPresenter = new UtilitiesPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mPresenter.setPostRequest(postRequest);

        mPresenter.CreateData();
        UtilitiesAdapter utilitiesAdapter = new UtilitiesAdapter(mActivity, this);
        utilitiesAdapter.setListItem(mPresenter.CreateData());

        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity, 2);
        mBinding.rcUtilities.setAdapter(utilitiesAdapter);
        mBinding.rcUtilities.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onBackData(List<UtilitiesModel> arrayList) {
        mPresenter.setmListUpdate(arrayList);
    }

    @Override
    public void onNext(String jsonData) {
        Log.d("CheckLog", jsonData);

        Fragment fragment = new ConfirmPostFragment();

        Bundle bundle = new Bundle();

        bundle.putString(Config.POST_BUNDEL_RES, jsonData);

        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();

        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fl_post, fragment);

        fragmentTransaction.addToBackStack("confirm");

        fragmentTransaction.commit();


    }
}
