package com.poly.smartfindpro.ui.detailpost;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;
import com.poly.smartfindpro.ui.detailpost.adapter.DetailImageAdapter;
import com.poly.smartfindpro.ui.user.profile.ProfileFragment;
import com.poly.smartfindpro.ui.user.userFragment.UserFragment;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailPostActivity extends BaseDataBindActivity<ActivityInformationPostBinding, DetailPostPresenter>
        implements DetailPostContact.ViewModel {
    private Products mProduct;
    private DetailImageAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_post;
    }

    @Override
    protected void initView() {

        getData();

        mPresenter = new DetailPostPresenter(this, this, mBinding);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initData() {
        adapter = new DetailImageAdapter(this);
        adapter.setImage(mProduct.getProduct().getInformation().getImage());
        mBinding.rvListImage.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.rvListImage.setAdapter(adapter);
        mBinding.rvListImage.setLayoutManager(layoutManager);
        mPresenter.setData(mProduct);

    }


    private void getData() {
        Type type = new TypeToken<Products>() {
        }.getType();
        Intent intent = getIntent();
        mProduct = new Products();
        mProduct = new Gson().fromJson(intent.getStringExtra(Config.POST_BUNDEL_RES), type);

    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onClickCall() {
        String PhoneNum = mBinding.tvPhoneNumber.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+Uri.encode(PhoneNum.trim())));
//        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }

    @Override
    public void onClickInbox() {
        Toast.makeText(this, "Chưa thực hiện được nhắn tin ", Toast.LENGTH_SHORT).show();
    }
}