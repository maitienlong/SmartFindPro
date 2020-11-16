package com.poly.smartfindpro.ui.post.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerPostAdapter extends FragmentStatePagerAdapter {
    public ViewPagerPostAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:

                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
