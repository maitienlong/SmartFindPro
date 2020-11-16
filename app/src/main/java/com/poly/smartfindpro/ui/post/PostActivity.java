package com.poly.smartfindpro.ui.post;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityPostBinding;
import com.poly.smartfindpro.ui.post.adapter.ViewPagerPostAdapter;

public class PostActivity extends BaseDataBindActivity<ActivityPostBinding, PostPresenter> implements PostContract.ViewModel{

    private boolean isStatusInfor = false, isStatusAddress = false, isStatusTools = false, isStatusConfirm = false;

    private ViewPager viewPager;

    private ViewPagerPostAdapter viewPagerPostAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    protected void initView() {
        viewPager = mBinding.vpPost;

        FragmentManager fragmentManager = getSupportFragmentManager();

        viewPagerPostAdapter = new ViewPagerPostAdapter(fragmentManager);

        viewPager.setAdapter(viewPagerPostAdapter);

        viewPager.setCurrentItem(0);

        //viewPager.addOnPageChangeListener();
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_infor:

                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_address:
                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_tool:
                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_confirm:
                Toast.makeText(PostActivity.this, "A", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
