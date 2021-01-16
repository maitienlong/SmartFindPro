package com.poly.smartfindpro.ui.notification;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.notification.req.NotificationRequest;
import com.poly.smartfindpro.data.model.notification.res.History;
import com.poly.smartfindpro.data.model.notification.res.NotifyResponse;
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
        MyRetrofitSmartFind.getInstanceSmartFind().getNotification(request).enqueue(new Callback<NotifyResponse>() {
            @Override
            public void onResponse(Call<NotifyResponse> call, Response<NotifyResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {
                    List<History> resList = response.body().getResponseBody().getHistory();
                    Log.d("onResponse: ", new Gson().toJson(resList));

                    mViewModel.onShowNotification(resList);
                } else {
                    mViewModel.showMessage("Bình luận bài viết hiện không thể thực hiện");
                }
            }

            @Override
            public void onFailure(Call<NotifyResponse> call, Throwable t) {
                Log.d("CheckNotify", t.toString());
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
