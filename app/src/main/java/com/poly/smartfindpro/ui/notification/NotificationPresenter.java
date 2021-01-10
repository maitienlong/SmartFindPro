package com.poly.smartfindpro.ui.notification;

import android.content.Context;
import android.util.Log;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.notification.req.NotificationRequest;
import com.poly.smartfindpro.data.model.notification.res.Notification;
import com.poly.smartfindpro.data.model.notification.res.NotificationResponse;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentNotificationBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter implements NotificationContract.Presenter {

    private Context context;
    private NotificationContract.ViewModel mViewModel;

    private FragmentNotificationBinding mBinding;

    public NotificationPresenter(Context context, NotificationContract.ViewModel mViewModel, FragmentNotificationBinding mBinding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {
        onRequestNotification();
    }

    private void onRequestNotification() {
        NotificationRequest request = new NotificationRequest(Config.TOKEN_USER);
        MyRetrofitSmartFind.getInstanceSmartFind().getNotification(request).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {
                    mViewModel.onShowNotification(response.body().getResponseBody().getNotification());
                } else {
                    //                    mViewModel.showMessage("Bình luận bài viết hiện không thể thực hiện");
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                mViewModel.showMessage(context.getString(R.string.services_not_avail));
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
