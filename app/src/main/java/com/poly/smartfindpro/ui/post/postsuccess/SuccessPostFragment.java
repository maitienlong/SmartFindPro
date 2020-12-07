package com.poly.smartfindpro.ui.post.postsuccess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.area.req.BodyReq;
import com.poly.smartfindpro.data.model.area.result.ListArea;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.model.post.req.Address;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.databinding.FragmentAddressPostBinding;
import com.poly.smartfindpro.databinding.FragmentPostSuccessBinding;
import com.poly.smartfindpro.ui.post.adapter.SpinnerAreaAdapter;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

import java.lang.reflect.Type;

import static android.app.Activity.RESULT_OK;

public class SuccessPostFragment extends BaseDataBindFragment<FragmentPostSuccessBinding, SuccessPostPresenter> implements SuccessPostContract.ViewModel {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post_success;
    }


    @Override
    protected void initView() {
        mPresenter = new SuccessPostPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onConfirm() {
        mActivity.finish();
    }
}