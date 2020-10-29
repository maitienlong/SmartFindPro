package com.poly.smartfindpro.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentSettingUserBinding;


public class SettingUserFragment extends BaseDataBindFragment<FragmentSettingUserBinding, SettingUserPresenter>
implements SettingUserContact.ViewModel{


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_user;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}