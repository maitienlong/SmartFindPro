package com.poly.smartfindpro.ui.detailcomment;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;

import java.util.List;

public interface DetailCommentContact {

    interface ViewModel extends BaseView {

        void onClose();

        void onShowComment(List<Comments> responseList);

    }

    interface Presenter extends BasePresenter {
        void onComment();

        void onClose();

        void onFavorite();
    }
}
