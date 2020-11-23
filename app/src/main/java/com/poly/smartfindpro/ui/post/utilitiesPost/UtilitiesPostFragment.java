package com.poly.smartfindpro.ui.post.utilitiesPost;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentUtilitiesPostBinding;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.adapter.UtilitiesAdapter;
import com.poly.smartfindpro.ui.post.confirmPost.ConfirmPostFragment;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;
import com.poly.smartfindpro.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilitiesPostFragment extends BaseDataBindFragment<FragmentUtilitiesPostBinding, UtilitiesPresenter> implements UtilitiesContract.ViewModel, View.OnClickListener
, UtilitiesAdapter.OnOptionClick{

    private List<UtilitiesModel> listOptions = new ArrayList<>();
    private UtilitiesAdapter utilitiesAdapter = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_utilities_post;
    }

    @Override
    protected void initView() {
        mBinding.btnContinue.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        mPresenter = new UtilitiesPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);

        mPresenter.createData();
        utilitiesAdapter = new UtilitiesAdapter(mActivity);
        utilitiesAdapter.setListItem(mPresenter.createData());
        utilitiesAdapter.setOnOptionClick(this);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity,2);
        mBinding.rcUtilities.setAdapter(utilitiesAdapter);
        mBinding.rcUtilities.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContinue:
                AppUtils.replaceFragmentToActivity(getActivity().getSupportFragmentManager(),
                        new ConfirmPostFragment(), R.id.fl_post, false, "ConfirmPostFragment");
                break;
        }
    }

    @Override
    public void onOptionClick() {
        ((PostActivity) getActivity()).setListUtilitiesModel(utilitiesAdapter.getListUltilities());
    }

    public List<UtilitiesModel> getUtilitiesWereChoosen(){
        if (utilitiesAdapter != null){
            return utilitiesAdapter.getListUltilities();
        } else {
            return null;
        }
    }
}
