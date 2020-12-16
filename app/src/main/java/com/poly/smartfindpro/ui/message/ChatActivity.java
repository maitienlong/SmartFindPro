package com.poly.smartfindpro.ui.message;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivityMessageBinding;

import java.lang.reflect.Type;

public class ChatActivity extends BaseDataBindActivity<ActivityMessageBinding, ChatPresenter>
        implements ChatContact.ViewModel {

    private Products mProduct;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        mPresenter = new ChatPresenter(this, this, mBinding);
        mBinding.setPresenter(mPresenter);

    }

    @Override
    protected void initData() {
        Type type = new TypeToken<Products>() {
        }.getType();
        Intent intent = getIntent();
        mProduct = new Products();
        mProduct = new Gson().fromJson(intent.getStringExtra(Config.POST_BUNDEL_RES), type);

        mBinding.cmtbUsername.setTitle(mProduct.getUser().getUserName());
    }
}