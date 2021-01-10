package com.poly.smartfindpro.ui.notification;

import android.util.Log;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.model.notification.res.Notification;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.FragmentNotificationBinding;
import com.poly.smartfindpro.ui.detailpost.DetailPostPresenter;
import com.poly.smartfindpro.ui.detailpost.adapter.CommentPostAdapter;
import com.poly.smartfindpro.ui.detailpost.adapter.DetailImageAdapter;
import com.poly.smartfindpro.ui.notification.adapter.NotificationAdapter;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.List;

public class NotificationFragment extends BaseDataBindFragment<FragmentNotificationBinding, NotificationPresenter> implements NotificationContract.ViewModel {

    private NotificationAdapter notificationAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void initView() {
        mPresenter = new NotificationPresenter(getContext(), this, mBinding);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initData() {
        notificationAdapter = new NotificationAdapter(getContext(), this);
    }

    @Override
    public void onShowNotification(List<Notification> responseList) {
        Log.d("onResponseNotification", String.valueOf(responseList.size()));
        notificationAdapter.setItem(responseList);
        BindingUtils.setAdapter(mBinding.rvNotification, notificationAdapter, false);
    }
}