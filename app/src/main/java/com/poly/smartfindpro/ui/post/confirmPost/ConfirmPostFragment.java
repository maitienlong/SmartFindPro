package com.poly.smartfindpro.ui.post.confirmPost;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentConfirmPostBinding;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.model.InforModel;
import com.poly.smartfindpro.ui.post.model.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;
//import com.poly.smartfindpro.ui.post.model.Address;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfirmPostFragment extends BaseDataBindFragment<FragmentConfirmPostBinding, ConfirmPostPresenter> {
    private PostRequest postRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_post;
    }

    private void reciveData() {
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        reciveData();
    }
}

