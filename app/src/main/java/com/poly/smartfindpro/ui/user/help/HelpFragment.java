package com.poly.smartfindpro.ui.user.help;

import android.content.Intent;
import android.net.Uri;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentHelpUserBinding;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;
import com.poly.smartfindpro.ui.login.LoginActivity;
import com.poly.smartfindpro.ui.user.setting.information.InforPresenter;

public class HelpFragment extends BaseDataBindFragment<FragmentHelpUserBinding, HelpPresenter> implements HelpContact.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_help_user;
    }

    @Override
    protected void initView() {
        mPresenter = new HelpPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Trợ giúp và phản hồi");

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onCallClick() {
        showAlertDialog("Thông báo", "Bạn muốn gọi điện cho chúng tôi", "Gọi", "Hủy", true, new AlertDialogListener() {
            @Override
            public void onAccept() {
                String PhoneNum = mBinding.tvPhoneApp.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(PhoneNum.trim())));
//        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }

            @Override
            public void onCancel() {

            }
        });


    }
}
