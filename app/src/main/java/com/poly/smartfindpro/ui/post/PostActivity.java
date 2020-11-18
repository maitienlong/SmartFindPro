package com.poly.smartfindpro.ui.post;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityPostBinding;
import com.poly.smartfindpro.ui.post.adapter.ViewPagerPostAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;

public class PostActivity extends BaseDataBindActivity<ActivityPostBinding, PostPresenter> implements PostContract.ViewModel {

    private boolean isStatusInfor = false, isStatusAddress = false, isStatusTools = false, isStatusConfirm = false;

    private ViewPagerPostAdapter viewPagerPostAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    protected void initView() {

        viewPagerPostAdapter = new ViewPagerPostAdapter(this);
        mBinding.vpPost.setAdapter(viewPagerPostAdapter);

        mBinding.vpPost.setCurrentItem(1);

//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tlPost, mBinding.vpPost, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                switch (position){
//                    case 0:
//                        tab.setIcon(R.mipmap.btn_rdo_false);
//                        tab.setText("Thông tin");
//                        break;
//                    case 1:
//                        tab.setIcon(R.mipmap.btn_rdo_false);
//                        tab.setText("Địa chỉ");
//                        break;
//                    case 3:
//                        tab.setIcon(R.mipmap.btn_rdo_false);
//                        tab.setText("Tiện ích");
//                        break;
//                    case 4:
//                        tab.setIcon(R.mipmap.btn_rdo_false);
//                        tab.setText("Xác nhận ");
//                        break;
//                }
//            }
//        });
//        tabLayoutMediator.attach();

    }

    @Override
    protected void initData() {


    }

//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId()) {
//            case R.id.btn_infor:
//
//                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.btn_address:
//                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.btn_tool:
//                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.btn_confirm:
//                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
}
