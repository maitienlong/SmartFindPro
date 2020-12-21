package com.poly.smartfindpro.ui.detailpost;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.comment.initrecomment.req.CommentDetailRequest;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;

import java.util.List;

public interface DetailPostContact {
    interface ViewModel extends BaseView {
        void onBackClick();

        void onClickCall();

        void onClickInbox();

        void onClickProfile();

        void onClickShare();

        void onClickLike();

        void onShowComment(List<Comments> responseList);

        void onComment();

        void onCallBackAdapter(CommentDetailRequest commentDetailRequest);
    }

    interface Presenter extends BasePresenter {
        void onBackClick();

        void onClickCall();

        void onClickInbox();

        void onClickProfile();

        void onClickShare();

        void onClickLike();

        void onComment();

        void onRefeshComment();
    }
}
