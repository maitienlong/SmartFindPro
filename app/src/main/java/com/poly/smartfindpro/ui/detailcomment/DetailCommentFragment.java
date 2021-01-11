package com.poly.smartfindpro.ui.detailcomment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.comment.deleteComment.req.DeleteCommentRequest;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;
import com.poly.smartfindpro.data.model.comment.initrecomment.req.CommentDetailRequest;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentDetailCommentBinding;

import com.poly.smartfindpro.ui.detailpost.DetailPostActivity;
import com.poly.smartfindpro.ui.detailpost.adapter.ReplyCommentPostAdapter;
import com.poly.smartfindpro.utils.BindingUtils;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCommentFragment extends BaseDataBindFragment<FragmentDetailCommentBinding, DetailcommentPresenter> implements DetailCommentContact.ViewModel {

    private CommentDetailRequest mCommentDetailRequest;

    private ReplyCommentPostAdapter commentPostAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_comment;
    }

    private void getData() {
        if (getArguments() != null) {
            Type type = new TypeToken<CommentDetailRequest>() {
            }.getType();
            mCommentDetailRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);
        }
    }


    @Override
    protected void initView() {
        getData();
        mPresenter = new DetailcommentPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mBinding.ctbReplyComment.setTitle("Trả lời");

        mPresenter.initComment(mCommentDetailRequest);
        mBinding.cvInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.fragment_bottom_information, null);
                BottomSheetDialog dialog = new BottomSheetDialog(getContext());

                ImageView imgBottomAvatar;
                TextView tvBottomName;
                LinearLayout lnBottomDelete;
//bottomsheet
                imgBottomAvatar = (ImageView) view.findViewById(R.id.img_bottom_avatar);
                tvBottomName = (TextView) view.findViewById(R.id.tv_bottom_name);
                lnBottomDelete = (LinearLayout) view.findViewById(R.id.ln_bottom_delete);

                // ten
                tvBottomName.setText(mBinding.tvPerName.getText());

                // avatar
                Glide.
                        with(getContext())
                        .load(MyRetrofitSmartFind.smartFind + mPresenter.imgUserProduct)
                        .placeholder(R.mipmap.imgplaceholder)
                        .error(R.mipmap.imgplaceholder)
                        .into(imgBottomAvatar);
                if (!mPresenter.idUserOfProduct.equalsIgnoreCase(Config.TOKEN_USER)) {
                    lnBottomDelete.setVisibility(View.GONE);
                }

                lnBottomDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onDeleteComment(mPresenter.idOfProduct);
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view);
                dialog.show();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        commentPostAdapter = new ReplyCommentPostAdapter(mActivity, this);
    }

    @Override
    public void onShowComment(List<Comments> responseList) {
        commentPostAdapter.setItem(responseList);
        BindingUtils.setAdapter(mBinding.rcReplyComment, commentPostAdapter, false);
    }

    @Override
    public void onClose() {
        Intent intent = new Intent();
        intent.putExtra(Config.DATA_CALL_BACK, "ok");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    public void onDeleteComment(String commentID) {
        DeleteCommentRequest request = new DeleteCommentRequest();
        request.setUser(Config.TOKEN_USER);
        request.setComment(commentID);
        MyRetrofitSmartFind.getInstanceSmartFind().getDeleteComment(request).enqueue(new Callback<DeleteProductResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                Log.d("onResponse-delete-comment:", new Gson().toJson(response.body()) + "");
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {
                    if (response.body().getResponseBody().getStatus().equalsIgnoreCase("Success")) {
                        Toast.makeText(mActivity, "Xóa bình luận thành công", Toast.LENGTH_SHORT).show();
                        //back to info
                        onClose();
                    } else {
                        showMessage("Xóa bình luận hiện không thể thực hiện");
                    }
                } else {
                    showMessage("Xóa bình luận hiện không thể thực hiện");
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {

            }
        });
    }
}
