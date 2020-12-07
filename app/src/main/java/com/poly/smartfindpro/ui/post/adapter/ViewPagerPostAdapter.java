package com.poly.smartfindpro.ui.post.adapter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.poly.smartfindpro.ui.home.HomeFragment;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.confirmPost.ConfirmPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;
import com.poly.smartfindpro.ui.user.userFragment.UserFragment;

public class ViewPagerPostAdapter extends FragmentPagerAdapter {

    private Context context;

    public ViewPagerPostAdapter(@NonNull FragmentManager fm, Context context) {
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
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new InforPostFragment();
                break;
            case 2:
                fragment = new UserFragment();
                break;
            default:
                fragment = new InforPostFragment();

        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
