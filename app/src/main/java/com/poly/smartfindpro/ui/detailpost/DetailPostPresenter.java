package com.poly.smartfindpro.ui.detailpost;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.comment.initcomment.InitComment;
import com.poly.smartfindpro.data.model.comment.getcomment.req.CommentRequest;
import com.poly.smartfindpro.data.model.comment.getcomment.res.CommentResponse;
import com.poly.smartfindpro.data.model.favorite.ResponseFavoritePost;
import com.poly.smartfindpro.data.model.initfavorite.InitFavorite;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostPresenter implements DetailPostContact.Presenter {

    private Context context;
    private DetailPostContact.ViewModel mViewModel;

    private ActivityInformationPostBinding mBinding;

    private Products mProduct;

    public ObservableField<String> priceDetail;
    public ObservableField<String> addressDetail;
    public ObservableField<String> genderDetail;
    public ObservableField<String> amountDetail;
    public ObservableField<String> priceElectricDetail;
    public ObservableField<String> priceWaterDetail;
    public ObservableField<String> utilitiesDetail;
    public ObservableField<String> contentDetail;
    public ObservableField<String> userNameDetail;
    public ObservableField<String> timePostDetail;
    public ObservableField<String> priceDepositDetail;
    public ObservableField<String> categoryDetail;
    public ObservableField<String> phoneNumberDetail;

    public ObservableField<String> favoriteCount;


    public DetailPostPresenter(Context context, DetailPostContact.ViewModel mViewModel, ActivityInformationPostBinding binding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = binding;
        initData();
    }

    private void initData() {
        priceDetail = new ObservableField<>();
        addressDetail = new ObservableField<>();
        genderDetail = new ObservableField<>();
        amountDetail = new ObservableField<>();
        priceElectricDetail = new ObservableField<>();
        priceWaterDetail = new ObservableField<>();
        utilitiesDetail = new ObservableField<>();
        contentDetail = new ObservableField<>();
        userNameDetail = new ObservableField<>();
        timePostDetail = new ObservableField<>();
        priceDepositDetail = new ObservableField<>();
        categoryDetail = new ObservableField<>();
        phoneNumberDetail = new ObservableField<>();
        favoriteCount = new ObservableField<>();
        getProduct();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    private void getProduct() {

    }

    private void onRequestComment(String idPost) {
        CommentRequest request = new CommentRequest(Config.TOKEN_USER, idPost);
        MyRetrofitSmartFind.getInstanceSmartFind().getComment(request).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {
                    mViewModel.onShowComment(response.body().getResponseBody().getComments());

                } else {
                    mViewModel.showMessage("Bình luận bài viết hiện không thể thực hiện");
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                mViewModel.showMessage(context.getString(R.string.services_not_avail));
            }
        });
    }

    public void setData(Products product) {
        mProduct = product;
        priceDetail.set(NumberFormat.getNumberInstance().format(product.getProduct().getInformation().getPrice()) + " " + product.getProduct().getInformation().getUnit());
        addressDetail.set(product.getAddress().getDetailAddress() + "," + product.getAddress().getCommuneWardTown() + "," + product.getAddress().getDistrictsTowns() + "," + product.getAddress().getProvinceCity());
        genderDetail.set(product.getProduct().getInformation().getGender());
        amountDetail.set(product.getProduct().getInformation().getAmountPeople().toString());
        priceElectricDetail.set(NumberFormat.getNumberInstance().format(product.getProduct().getInformation().getElectricBill()) + "đ/" + product.getProduct().getInformation().getElectricUnit());
        priceWaterDetail.set(NumberFormat.getNumberInstance().format(product.getProduct().getInformation().getWaterBill()) + "đ/" + product.getProduct().getInformation().getWaterUnit());
        priceDepositDetail.set(NumberFormat.getNumberInstance().format(product.getProduct().getInformation().getDeposit()) + " " + product.getProduct().getInformation().getUnit());
        categoryDetail.set(product.getProduct().getCategory() + "  ");
        if (Config.LEVEL_ACCOUNT > 0) {
            phoneNumberDetail.set(product.getUser().getPhoneNumber());
        }
        mBinding.cmtb.setTitle("Bài viết");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat dateFormatter = sdf;

        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + product.getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(mBinding.imgAvatar);

        try {
            Date date = dateFormatter.parse(product.getCreateAt());

            timePostDetail.set(getTime(date));

        } catch (Exception e) {

        }
        String tIch = "";
        for (String item : product.getProduct().getUtilities()) {
            if (tIch.isEmpty()) {
                tIch = tIch + item;
            } else {
                tIch = tIch + ", " + item;
            }

        }
        utilitiesDetail.set(tIch);
        contentDetail.set(product.getContent());
        userNameDetail.set(product.getUser().getFullname());

        onRequestComment(product.getId());
        onGetFavorite(product.getId());

    }

    private String getTime(Date datePost) {
        String dateOK = "";
        Date today = Calendar.getInstance().getTime();
        if (!getTime("yyyy", today).equals(getTime("yyyy", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("yyyy", today)) - Integer.valueOf(getTime("yyyy", datePost))) + " năm";
        } else if (!getTime("MM", today).equals(getTime("MM", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("MM", today)) - Integer.valueOf(getTime("MM", datePost))) + " tháng";
        } else if (!getTime("dd", today).equals(getTime("dd", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("dd", today)) - Integer.valueOf(getTime("dd", datePost))) + " ngày";
        } else if (!getTime("HH", today).equals(getTime("HH", datePost))) {
            Log.d("CheckTimesss", String.valueOf(Integer.valueOf(getTime("HH", today))));
            return String.valueOf(Integer.valueOf(getTime("HH", today)) - Integer.valueOf(getTime("HH", datePost))) + " giờ";
        } else if (!getTime("mm", today).equals(getTime("mm", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("mm", today)) - Integer.valueOf(getTime("mm", datePost))) + " phút";
        } else if (!getTime("ss", today).equals(getTime("ss", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("ss", today)) - Integer.valueOf(getTime("ss", datePost))) + " giây";
        }
        return dateOK;
    }

    private String getTime(String type, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        DateFormat dateFormat = simpleDateFormat;
        Log.d("CheckTime", dateFormat.format(date));
        return dateFormat.format(date);
    }

    private void onGetFavorite(String productID){
        InitFavorite request = new InitFavorite();
        request.setUser(Config.TOKEN_USER);
        request.setProduct(productID);

        MyRetrofitSmartFind.getInstanceSmartFind().getFavorite(request).enqueue(new Callback<ResponseFavoritePost>() {
            @Override
            public void onResponse(Call<ResponseFavoritePost> call, Response<ResponseFavoritePost> response) {
                if(response.code() == 200 && response.body().getResponseHeader().getResCode() == 200){

                    favoriteCount.set(String.valueOf(response.body().getResponseBody().getCount()));


                    Log.d("CheckLogDetail", String.valueOf(response.body().getResponseBody().getIsFavorite()));
                    if(response.body().getResponseBody().getIsFavorite()){
                        mBinding.imgFavorite.setImageResource(R.drawable.ic_favorite_full);
                    }else {
                        mBinding.imgFavorite.setImageResource(R.drawable.ic_love_border);

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseFavoritePost> call, Throwable t) {

            }
        });


    }


    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }

    @Override
    public void onClickCall() {
        if (Config.LEVEL_ACCOUNT > 0) {
            mViewModel.onClickCall();
        } else {
            mViewModel.showMessage(context.getString(R.string.msg_đinhanh));
        }

    }

    @Override
    public void onClickInbox() {
        if (Config.LEVEL_ACCOUNT > 0) {
            mViewModel.onClickInbox();
        } else {
            mViewModel.showMessage(context.getString(R.string.msg_đinhanh));
        }
    }

    @Override
    public void onClickProfile() {
        mViewModel.onClickProfile();
    }

    @Override
    public void onClickShare() {
        mViewModel.onClickShare();
    }

    @Override
    public void onClickLike() {
        InitFavorite request = new InitFavorite();
        request.setUser(Config.TOKEN_USER);
        request.setProduct(mProduct.getId());

        MyRetrofitSmartFind.getInstanceSmartFind().initFavorite(request).enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {
                    onGetFavorite(mProduct.getId());
                } else {
                    mViewModel.showMessage("Hiện tại bạn không thể thích bài viết này, vui lòng thử lại sau");
                }
            }

            @Override
            public void onFailure(Call<CheckPhoneResponse> call, Throwable t) {

            }
        });

    }

    public TextWatcher onCommentListenner() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    mBinding.btnSendComment.setImageTintList(context.getColorStateList(R.color.gray2));
                } else {
                    mBinding.btnSendComment.setImageTintList(context.getColorStateList(R.color.background));
                }
            }
        };
    }

    @Override
    public void onComment() {


        InitComment request = new InitComment();
        request.setUser(Config.TOKEN_USER);
        request.setProduct(mProduct.getId());
        request.setOldComment(null);
        request.setTitle(mBinding.edtComment.getText().toString());

        MyRetrofitSmartFind.getInstanceSmartFind().initComment(request).enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {
                    onRequestComment(mProduct.getId());
                    Toast.makeText(context, "Bình luận thành công", Toast.LENGTH_SHORT).show();
                    mBinding.edtComment.setText("");
                } else {
                    mViewModel.showMessage("Hiện tại bạn không thể bình luận bài viết này, vui lòng thử lại sau");
                }
            }

            @Override
            public void onFailure(Call<CheckPhoneResponse> call, Throwable t) {
                mViewModel.showMessage(context.getString(R.string.services_not_avail));
            }
        });
    }

    @Override
    public void onRefeshComment() {
        onRequestComment(mProduct.getId());
    }
}
