package com.poly.smartfindpro.ui.post;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.callback.OnFragmentCloseCallback;
import com.poly.smartfindpro.databinding.ActivityPostBinding;
import com.poly.smartfindpro.ui.post.adapter.ViewPagerPostAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.post.model.Address;
import com.poly.smartfindpro.ui.post.model.InforModel;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends BaseDataBindActivity<ActivityPostBinding, PostPresenter> implements PostContract.ViewModel {
    private InforModel inforModel = null;
    private boolean isStatusInfor = false, isStatusAddress = false, isStatusTools = false, isStatusConfirm = false;

    private ViewPagerPostAdapter viewPagerPostAdapter;
    private List<UtilitiesModel> utilitiesModels = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    protected void initView() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.fl_post, new InforPostFragment());

        fragmentTransaction.addToBackStack("inforpost");

        fragmentTransaction.commit();

    }

    @Override
    protected void initData() {


    }



    @Override
    public void onClick(View v) {
        super.onClick(v);

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (v.getId()) {
            case R.id.btn_infor:

                fragmentManager.popBackStack("inforpost", 0);
                break;

            case R.id.btn_address:
                fragmentManager.popBackStack("addresspost", 0);
                break;

            case R.id.btn_tool:
                fragmentManager.popBackStack("utilitiespost", 0);
                break;

            case R.id.btn_confirm:
                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.d("CheckValue", String.valueOf(requestCode));
//        if (resultCode == Activity.RESULT_OK) {
//            if (data.hasExtra("demo")) {
//                String name = data.getStringExtra("demo");
//                Log.d("CheckValue", name);
//            }
//        }
//    }
}
