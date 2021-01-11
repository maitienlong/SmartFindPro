package com.poly.smartfindpro.ui.notification.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.notification.res.History;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.notification.NotificationContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;

    List<History> listItem;

    private NotificationContract.ViewModel mViewmodel;

    public NotificationAdapter(Context context, NotificationContract.ViewModel mViewmodel) {
        this.context = context;
        this.mViewmodel = mViewmodel;
    }

    public void setItem(List<History> listItem) {
        this.listItem = listItem;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_notification, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History item = listItem.get(position);
        // ten
        holder.tvContent.setText(item.getUser().getFullName() + " đã " + item.getStatus());

        // avatar
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + item.getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(holder.imgAvatar);

        //  time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        DateFormat dateFormatter = sdf;

        try {
            Date date = dateFormatter.parse(item.getCreateAt());
            holder.tvTime.setText(getTime(date));
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    private void onChange() {
        notifyDataSetChanged();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircularImageView imgAvatar;
        TextView tvContent;
        TextView tvTime;
        CircularImageView imgContent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (CircularImageView) itemView.findViewById(R.id.imgAvatar);
            tvContent = (TextView) itemView.findViewById(R.id.tv_Notification_Content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_Notification_Time);
            imgContent = (CircularImageView) itemView.findViewById(R.id.imgContent);
        }
    }
}
