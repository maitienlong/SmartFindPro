package com.poly.smartfindpro.ui.detailpost;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;
import com.poly.smartfindpro.ui.detailpost.adapter.DetailImageAdapter;

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
    private List<String> imageList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_post;
    }

    @Override
    protected void initView() {

        getData();

        mPresenter = new DetailPostPresenter(this, this,mBinding);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initData() {
        adapter = new DetailImageAdapter(this, imageList);
        mBinding.rvListImage.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        mBinding.rvListImage.setAdapter(adapter);
        mPresenter.setData(mProduct);
    }
    private String getTime(Date datePost) {
        String dateOK = "";
        Date today = Calendar.getInstance().getTime();
        if (!getTime("yyyy", today).equals(getTime("yyyy", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("yyyy", today)) - Integer.valueOf(getTime("yyyy", datePost))) + " năm";
        } else if (!getTime("MM", today).equals(getTime("MM", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("MM", today)) - Integer.valueOf(getTime("MM", datePost))) + " tháng";
        } else if (!getTime("dd", today).equals(getTime("dd", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("dd", today)) - Integer.valueOf(getTime("dd", datePost))) + " ngày";
        } else if (!getTime("HH", today).equals(getTime("HH", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("HH", today)) - Integer.valueOf(getTime("HH", datePost))) + " giờ";
        } else if (!getTime("mm", today).equals(getTime("mm", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("mm", today)) - Integer.valueOf(getTime("mm", datePost))) + " phút";
        } else if (!getTime("ss", today).equals(getTime("ss", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("ss", today)) - Integer.valueOf(getTime("ss", datePost))) + " giây";
        }
        return dateOK;
    }

    private String getTime(String type, Date date) {
        DateFormat dateFormat = new SimpleDateFormat(type);
        return dateFormat.format(date);
    }
    private void getData() {
        Type type = new TypeToken<Products>() {
        }.getType();
        Intent intent = getIntent();
        mProduct = new Products();
        mProduct = new Gson().fromJson(intent.getStringExtra(Config.POST_BUNDEL_RES),type);

    }
}