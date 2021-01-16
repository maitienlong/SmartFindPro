package com.poly.smartfindpro.ui.user.setting.confirmAccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.ConfigSharedPreferences;
import com.poly.smartfindpro.databinding.FragmentConfirmAccountBinding;
import com.poly.smartfindpro.ui.identification.activity.IdentificationActivity;
import com.poly.smartfindpro.ui.identification.adapter.RankAccount;
import com.poly.smartfindpro.ui.identification.adapter.SlideshowLevelAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConfirmAccountFragment extends BaseDataBindFragment<FragmentConfirmAccountBinding, ConfirmAccountPresenter>
        implements ConfirmAccountContact.ViewModel {


    private SlideshowLevelAdapter levelAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_account;
    }

    @Override
    protected void initView() {
        mPresenter = new ConfirmAccountPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mBinding.ctb.setTitle("Xác thực tài khoản");

        List<RankAccount> rankAccountList = new ArrayList<>();
        RankAccount rankDong = new RankAccount(R.mipmap.rank_dong, "Rank dong co nhung chuc nang nhu sau");
        RankAccount rankBac = new RankAccount(R.mipmap.rank_bac, "Rank bac co nhung chuc nang nhu sau");
        RankAccount rankVang = new RankAccount(R.mipmap.rank_vang, "Rank vang co nhung chuc nang nhu sau");

        rankAccountList.add(rankDong);
        rankAccountList.add(rankBac);
        rankAccountList.add(rankVang);

        levelAdapter = new SlideshowLevelAdapter(mActivity, rankAccountList);

        mBinding.viewPagerRank.setAdapter(levelAdapter);

        mBinding.viewPagerRank.setCurrentItem(0);

        mBinding.viewPagerRank.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        checkLevel(1);
                        break;
                    case 1:
                        checkLevel(2);
                        break;
                    case 2:
                        checkLevel(3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

    }

    @Override
    protected void initData() {
        checkLevel(1);
    }

    private void checkLevel(int rank) {
        if (rank > Config.LEVEL_ACCOUNT) {
            mPresenter.setTitleButton("Định danh");
            mBinding.btnAction.setEnabled(true);
        } else {
            mPresenter.setTitleButton("Đã sở hữu");
            mBinding.btnAction.setEnabled(false);
        }
    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onConfirm() {
        Intent intent = new Intent(mActivity, IdentificationActivity.class);

        startActivityForResult(intent, Config.RESULT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RESULT_REQUEST && resultCode == Activity.RESULT_OK) {

            if (data.hasExtra(Config.DATA_CALL_BACK)) {
                int level = data.getIntExtra(Config.DATA_CALL_BACK, Config.LEVEL_ACCOUNT);

                SharedPreferences prefs = mActivity.getSharedPreferences(Config.NAME_FILE_PREFERENCE, Context.MODE_PRIVATE);
                String token = prefs.getString(ConfigSharedPreferences.TOKEN, "token");
                String username = prefs.getString(ConfigSharedPreferences.USERNAME, "username");
                String password = prefs.getString(ConfigSharedPreferences.PASSWORD, "password");
                boolean isSave = false;

                if (onSaveLevel(username, password, token, level, isSave)) {
                    Log.i(getContext().getPackageName(), "update ok");
                }

            }

        }
    }

    private boolean onSaveLevel(String username, String password, String token, int level, boolean isSave) {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(Config.NAME_FILE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ConfigSharedPreferences.USERNAME, username);
        editor.putString(ConfigSharedPreferences.PASSWORD, password);
        editor.putString(ConfigSharedPreferences.TOKEN, token);
        editor.putInt(ConfigSharedPreferences.LEVEL, level);
        editor.putBoolean(ConfigSharedPreferences.IS_SAVE, isSave);

        return editor.commit();
    }
}
