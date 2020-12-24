package com.poly.smartfindpro.ui.detailcomment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;
import com.poly.smartfindpro.data.model.comment.initcomment.InitComment;
import com.poly.smartfindpro.data.model.comment.initrecomment.req.CommentDetailRequest;
import com.poly.smartfindpro.data.model.comment.initrecomment.res.ReplycommentResponse;
import com.poly.smartfindpro.data.model.initfavorite.InitFavorite;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentDetailCommentBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailcommentPresenter implements DetailCommentContact.Presenter {
    private Context context;

    private DetailCommentContact.ViewModel mViewModel;

    private CommentDetailRequest mCommentDetailRequest;

    private String idProduct;

    public ObservableField<String> name;

    public ObservableField<String> content;

    public ObservableField<String> time;

    public ObservableField<String> startCount;

    private FragmentDetailCommentBinding mBinding;

    private Comments mProduct;

    public DetailcommentPresenter(Context context, DetailCommentContact.ViewModel mViewModel, FragmentDetailCommentBinding binding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = binding;
        initData();
    }

    private void initData() {
        mProduct = new Comments();
        name = new ObservableField();
        content = new ObservableField();
        time = new ObservableField();
        startCount = new ObservableField();


    }


    public void initComment(CommentDetailRequest mCommentDetailRequest) {
        this.mCommentDetailRequest = mCommentDetailRequest;
        MyRetrofitSmartFind.getInstanceSmartFind().getReplyComment(mCommentDetailRequest).enqueue(new Callback<ReplycommentResponse>() {
            @Override
            public void onResponse(Call<ReplycommentResponse> call, Response<ReplycommentResponse> response) {
                if (response.code() == 200) {

                    Log.d("CheckFavorite", response.body().getResponseBody().getComments().getFavorites().getCount()+"");

                    Log.d("CheckFavorite", response.body().getResponseBody().getComments().getComment().getId());

                    Log.d("CheckFavoritePrd", response.body().getResponseBody().getComments().getComment().getProduct());

                    Comments item = response.body().getResponseBody().getComments();

                    idProduct = item.getComment().getProduct();

                    mProduct = response.body().getResponseBody().getComments();

                    onUpdatePost(mProduct);

                    initView(item.getComment().getUser().getFullname(), item.getComment().getTitle()
                            , item.getComment().getUser().getAvatar()
                            , item.getComment().getCreateAt()
                            , item.getFavorites().getCount());

                    mViewModel.onShowComment(item.getReply().getList());

                } else {
                    mViewModel.showMessage("Hệ thống bận: Code - " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ReplycommentResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onComment() {
        InitComment request = new InitComment();
        request.setUser(Config.TOKEN_USER);

        request.setProduct(idProduct);

        request.setOldComment(mCommentDetailRequest.getOldComment());

        request.setTitle(mBinding.edtComment.getText().toString());

        MyRetrofitSmartFind.getInstanceSmartFind().initComment(request).enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {
                    initComment(mCommentDetailRequest);
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
    public void onClose() {
        mViewModel.onClose();
    }

    @Override
    public void onFavorite() {
        InitFavorite request = new InitFavorite();
        request.setUser(Config.TOKEN_USER);
        request.setProduct(mProduct.getComment().getProduct());
        request.setComment(mProduct.getComment().getId());

        MyRetrofitSmartFind.getInstanceSmartFind().initFavorite(request).enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {

                    if (mProduct.getIsFavorite()) {
                        mProduct.setIsFavorite(false);
                        initComment(mCommentDetailRequest);
                    } else {
                        mProduct.setIsFavorite(true);
                        initComment(mCommentDetailRequest);
                    }


                }
            }

            @Override
            public void onFailure(Call<CheckPhoneResponse> call, Throwable t) {

            }
        });
    }

    private void onUpdatePost(Comments product){
        startCount.set(String.valueOf(product.getFavorites().getCount()));
        if(product.getIsFavorite()){
            mBinding.btnFavorite.setTextColor(Color.BLUE);
        }else {
            mBinding.btnFavorite.setTextColor(Color.BLACK);
        }
    }

    private void initView(String name, String content, String imageAddress, String time, int startCount) {
        this.name.set(name);
        this.content.set(content);
        this.startCount.set(String.valueOf(startCount));
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + imageAddress)
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(mBinding.imgAvtComment);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat dateFormatter = sdf;

        try {
            Date date = dateFormatter.parse(time);
            this.time.set(getTime(date));
        } catch (Exception e) {

        }
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
}
