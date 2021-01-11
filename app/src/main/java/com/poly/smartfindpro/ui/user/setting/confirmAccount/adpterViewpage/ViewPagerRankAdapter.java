package com.poly.smartfindpro.ui.user.setting.confirmAccount.adpterViewpage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.poly.smartfindpro.ui.checklevel.levelbac.RankBacFragment;
import com.poly.smartfindpro.ui.checklevel.leveldong.RankDongFragment;
import com.poly.smartfindpro.ui.checklevel.levelvang.RankVangFragment;
import com.poly.smartfindpro.ui.home.HomeFragment;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.user.userFragment.UserFragment;

public class ViewPagerRankAdapter extends FragmentPagerAdapter {

    private Context context;

    public ViewPagerRankAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new RankDongFragment();
                break;
            case 1:
                fragment = new RankBacFragment();
                break;
            case 2:
                fragment = new RankVangFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
