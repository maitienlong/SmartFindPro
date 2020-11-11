package com.poly.smartfindpro.ui.user.changepass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentChangePassBinding;
import com.poly.smartfindpro.databinding.FragmentSettingUserBinding;
import com.poly.smartfindpro.ui.user.setting.SettingUserContact;
import com.poly.smartfindpro.ui.user.setting.SettingUserPresenter;

public class ChangePassFragment extends BaseDataBindFragment<FragmentChangePassBinding, ChangePassPresenter>
        implements ChangePassContact.ViewModel{


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_pass;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}