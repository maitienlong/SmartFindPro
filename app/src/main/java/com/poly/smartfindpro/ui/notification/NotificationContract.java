package com.poly.smartfindpro.ui.notification;

import com.poly.smartfindpro.basedatabind.BasePresenter;
import com.poly.smartfindpro.basedatabind.BaseView;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;
import com.poly.smartfindpro.data.model.notification.res.Notification;

import java.util.List;

public interface NotificationContract {
    interface ViewModel extends BaseView{

        void onShowNotification(List<Notification> responseList);
    }

    interface Presenter extends BasePresenter{

    }
}
