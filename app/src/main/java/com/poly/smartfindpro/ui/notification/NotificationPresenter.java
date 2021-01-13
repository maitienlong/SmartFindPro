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
                    List<History> notificationList = new ArrayList<>();
                    for (int i = 0; i < resList.size(); i++) {


                        switch (resList.get(i).getStatus()) {
                            case "LOGIN-APP":
                                break;
                            case "UPDATE-USER-PASSWORD":
                                resList.get(i).setStatus("Bạn đã đổi mật khẩu thành công");
                                notificationList.add(resList.get(i));
                                break;
                            case "UPDATE-USER":
                                resList.get(i).setStatus("Bạn đã cập nhật thông tin tài khoản thành công");
                                notificationList.add(resList.get(i));
                                break;
                            case "UPDATE-USER-LEVEL-1":
                                resList.get(i).setStatus("Tài khoản của bạn đã được nâng cấp lên thành viên Đồng(tài khoản cấp 1)");
                                notificationList.add(resList.get(i));
                                break;
                            case "UPGRADE-USER":
                                resList.get(i).setStatus("Bạn đã cập nhật lại thông tin để nâng cấp lên thành Viên bạc(tài khoản cấp 2)");
                                notificationList.add(resList.get(i));
                                break;
                            case "INIT-UPGRADE-USER":
                                resList.get(i).setStatus("Bạn đã gửi thông tin để nâng cấp tài khoản lên thành Viên bạc(tài khoản cấp 2). Vui lòng chờ xác nhận từ người quản lý");
                                notificationList.add(resList.get(i));
                                break;
                            case "DISABLE-USER":
                                resList.get(i).setStatus("Tài khoản của bạn đã bị khóa bởi tài khoản quản lý vì bạn đã vi phạm trong các điều khoản khi sử dụng ứng dụng");
                                notificationList.add(resList.get(i));
                                break;
                            case "ENABLE-USER":
                                resList.get(i).setStatus("Tài khoản của bạn đã được khôi phục");
                                notificationList.add(resList.get(i));
                                break;
                            case "INIT-PRODUCT":
                                resList.get(i).setStatus("Phòng của bạn đã được gửi cho bên duyệt bài của chúng tôi. Vui lòng chờ phản hồi từ chúng tôi");
                                notificationList.add(resList.get(i));
                                break;
                            case "UPDATE-PRODUCT":
                                try {
                                    Products products = (Products) resList.get(i).getProduct();
                                    resList.get(i).setStatus("Bạn đã cập nhật thông tin của bài đăng " + products.getContent());
                                } catch (Exception e) {
                                    Log.d("getProduct", e.toString());
                                }
                                resList.get(i).setStatus("Bạn đã cập nhật thông tin của bài đăng ");
                                notificationList.add(resList.get(i));
                                break;
                            case "UPDATE-TOTAL-PEOPLE-LEASE":

                                try {
                                    Products products = (Products) resList.get(i).getProduct();
                                    resList.get(i).setStatus("Bạn đã cập nhật số lượng người đã thuê của bài đăng " + products.getContent());
                                } catch (Exception e) {
                                    Log.d("getProduct", e.toString());
                                }
                                resList.get(i).setStatus("Bạn đã cập nhật số lượng người đã thuê của bài đăng ");
                                notificationList.add(resList.get(i));
                                break;
                            case "DELETE-PRODUCT-USER":
                                try {
                                    Products products = (Products) resList.get(i).getProduct();
                                    resList.get(i).setStatus("Bài đăng " + products.getContent() + "đã được xóa thành công");
                                } catch (Exception e) {
                                    Log.d("getProduct", e.toString());
                                }
                                resList.get(i).setStatus("Bài đăng bạn vừa xóa đã được xóa thành công");
                                notificationList.add(resList.get(i));
                                break;
                            case "DELETE-PRODUCT-ADMIN":
                                try {
                                    Products products = (Products) resList.get(i).getProduct();
                                    resList.get(i).setStatus("Bài đăng " + products.getContent() + "đã được xóa bởi quản lý");
                                } catch (Exception e) {
                                    Log.d("getProduct", e.toString());
                                }
                                resList.get(i).setStatus("Bài đăng đã được xóa bởi quản lý");
                                notificationList.add(resList.get(i));
                                break;
                            case "INIT-COMMENT-COMMENT":
                                break;
                            case "INIT-COMMENT-REPLY":
                                break;
                            case "INIT-FAVORITE":
                                break;
                            case "LOGIN-WEB-SERVER":
                                break;
                            case "CONFIRM-PRODUCT":
                                try {
                                    Products products = (Products) resList.get(i).getProduct();
                                    resList.get(i).setStatus("Bài đăng " + products.getContent() + " đã được duyệt bởi quản lý. Giờ đây bạn có thể thấy bài đăng này trong mục tìm kiếm");

                                } catch (Exception e) {
                                    Log.d("getProduct", e.toString());
                                }
                                resList.get(i).setStatus("Bài đăng đã được duyệt bởi quản lý. Giờ đây bạn có thể thấy bài đăng này trong mục tìm kiếm");
                                notificationList.add(resList.get(i));
                                break;
                            case "CANCEL-PRODUCT":
                                try {
                                    Products products = (Products) resList.get(i).getProduct();
                                    resList.get(i).setStatus("Bài đăng " + products.getContent() + " không được duyệt vì một vài lý do trong quá trình duyệt. Chúng tôi sẽ sớm liên hệ với bạn để giải thích về vấn đề này hoặc bạn có thể liên hệ với chúng tôi qua số điện thoại 0399551166");
                                } catch (Exception e) {
                                    Log.d("getProduct", e.toString());
                                }
                                resList.get(i).setStatus("Bài đăng không được duyệt vì một vài lý do trong quá trình duyệt. Chúng tôi sẽ sớm liên hệ với bạn để giải thích về vấn đề này hoặc bạn có thể liên hệ với chúng tôi qua số điện thoại 0399551166");
                                notificationList.add(resList.get(i));
                                break;
                            case "CONFIRM-UPGRADE-USER-LEVEL-2":
                                resList.get(i).setStatus("Tài khoản của bạn đã được nâng cấp lên thành viên Bạc(tài khoản cấp 2)");
                                notificationList.add(resList.get(i));
                                break;
                            case "CONFIRM-UPGRADE-USER-LEVEL-3":
                                resList.get(i).setStatus("Tài khoản của bạn đã được nâng cấp lên thành viên Vàng(tài khoản cấp 3)");
                                notificationList.add(resList.get(i));
                                break;
                            case "CANCEL-UPGRADE-USER":
                                resList.get(i).setStatus("Tài khoản của bạn không được duyệt vì một vài lý do trong quá trình duyệt. Chúng tôi sẽ sớm liên hệ với bạn để giải thích về vấn đề này hoặc bạn có thể liên hệ với chúng tôi qua số điện thoại 0399551166");
                                notificationList.add(resList.get(i));
                                break;

                        }
                    }
                    mViewModel.onShowNotification(notificationList);
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
