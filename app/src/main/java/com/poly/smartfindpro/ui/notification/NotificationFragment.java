package com.poly.smartfindpro.ui.notification;

import android.util.Log;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.model.notification.res.History;
import com.poly.smartfindpro.databinding.FragmentNotificationBinding;
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
    public void onShowNotification(List<History> responseList) {
        Log.d("onResponseNotification", String.valueOf(responseList.size()));
        notificationAdapter.setItem(responseList);
        BindingUtils.setAdapter(mBinding.rvNotification, notificationAdapter, false);
    }
}