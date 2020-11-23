package com.poly.smartfindpro.utils;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AppUtils {
    public static void replaceFragmentToActivity(FragmentManager fragmentManager,
                                                 Fragment fragment, int frameId, boolean onBackstack, String tag) {


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.setCustomAnimations(R.anim.attack_fragment,R.anim.detack_fragment,R.anim.backstack_in,R.anim.backstack_out);
        transaction.replace(frameId, fragment, tag);
        if (onBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        //transaction.commit();
    }
}
