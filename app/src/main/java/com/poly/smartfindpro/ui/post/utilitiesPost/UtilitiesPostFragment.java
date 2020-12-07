package com.poly.smartfindpro.ui.post.utilitiesPost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentUtilitiesPostBinding;
import com.poly.smartfindpro.ui.post.adapter.UtilitiesAdapter;
import com.poly.smartfindpro.ui.post.confirmPost.ConfirmPostFragment;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.lang.reflect.Type;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class UtilitiesPostFragment extends BaseDataBindFragment<FragmentUtilitiesPostBinding, UtilitiesPresenter> implements UtilitiesContract.ViewModel {
    private PostRequest postRequest;
    private String jsonPhoto;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_utilities_post;
    }

    private void ReciData() {
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);
        jsonPhoto = getArguments().getString(Config.POST_BUNDEL_RES_PHOTO);
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
        Log.d("CheckLog", new Gson().toJson(postRequest));

        Intent intent = new Intent();

        intent.putExtra(Config.DATA_CALL_BACK, "4");

        intent.putExtra(Config.POST_BUNDEL_RES, jsonData);

        intent.putExtra(Config.POST_BUNDEL_RES_PHOTO, getArguments().getString(Config.POST_BUNDEL_RES_PHOTO));

        setResult(RESULT_OK, intent);

        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, jsonData);
        bundle.putString(Config.POST_BUNDEL_RES_PHOTO, getArguments().getString(Config.POST_BUNDEL_RES_PHOTO));

        onBackData();

        getBaseActivity().goToFragmentCallBackData(R.id.fl_post, new ConfirmPostFragment(), bundle, getOnFragmentDataCallBack());
    }
}
