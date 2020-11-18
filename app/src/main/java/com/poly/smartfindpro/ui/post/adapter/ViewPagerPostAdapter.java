package com.poly.smartfindpro.ui.post.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

public class ViewPagerPostAdapter extends FragmentStatePagerAdapter {


    public ViewPagerPostAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InforPostFragment();
            case 1:
                return new AddressPostFragment();
            case 2:
                return new UtilitiesPostFragment();
            case 3:
                break;
            default:
                new InforPostFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
