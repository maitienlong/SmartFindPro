package com.poly.smartfindpro.ui.detailcomment;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;
import com.poly.smartfindpro.data.model.comment.initrecomment.req.CommentDetailRequest;
import com.poly.smartfindpro.databinding.FragmentDetailCommentBinding;

import com.poly.smartfindpro.ui.detailpost.adapter.ReplyCommentPostAdapter;
import com.poly.smartfindpro.utils.BindingUtils;

import java.lang.reflect.Type;
import java.util.List;

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
    }

    @Override
    protected void initData() {
        commentPostAdapter = new ReplyCommentPostAdapter(mActivity);
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
}
