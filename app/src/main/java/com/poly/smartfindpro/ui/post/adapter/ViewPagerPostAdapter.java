package com.poly.smartfindpro.ui.post.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;

public class ViewPagerPostAdapter extends FragmentStateAdapter {

    public ViewPagerPostAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new AddressPostFragment();
                break;
            case 1:
                fragment = new InforPostFragment();


                break;
            case 2:

                break;
            case 3:

                break ;
            default:

        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
