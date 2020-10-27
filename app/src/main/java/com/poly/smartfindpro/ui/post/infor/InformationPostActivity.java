package com.poly.smartfindpro.ui.post.infor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;

public class InformationPostActivity extends BaseDataBindActivity<ActivityInformationPostBinding, InformationPostPresenter>
        implements InformationPostContact.ViewModel {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_post;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}