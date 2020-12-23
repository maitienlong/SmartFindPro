package com.poly.smartfindpro.ui.detailpost.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.comment.initrecomment.req.CommentDetailRequest;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;
import com.poly.smartfindpro.data.model.initfavorite.InitFavorite;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.detailpost.DetailPostContact;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentPostAdapter extends RecyclerView.Adapter<CommentPostAdapter.ViewHolder> {
    private Context context;

    List<Comments> listItem;

    private DetailPostContact.ViewModel mViewmodel;

    public CommentPostAdapter(Context context, DetailPostContact.ViewModel mViewmodel) {
        this.context = context;
        this.mViewmodel = mViewmodel;
    }

    public CommentPostAdapter(Context context) {
        this.context = context;
        this.mViewmodel = mViewmodel;
    }

    public void setItem(List<Comments> listItem) {
        this.listItem = listItem;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comments item = listItem.get(position);

        // ten
        holder.customerName.setText(item.getComment().getUser().getFullname());

        // noi dung
        holder.content.setText(item.getComment().getTitle());

        // avatar
        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + item.getComment().getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(holder.avatar);

        //  time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        DateFormat dateFormatter = sdf;

        try {
            Date date = dateFormatter.parse(item.getComment().getCreateAt());
            holder.time.setText(getTime(date));
        } catch (Exception e) {

        }

        // star count
        holder.sartCount.setText(String.valueOf(item.getFavorites().getCount()));

        // favoris
        if (item.getIsFavorite()) {
            holder.favoris.setTextColor(Color.BLUE);
        } else {
            holder.favoris.setTextColor(Color.BLACK);
        }

        // recomment count
        if (item.getReply().getCount() == 0) {
            holder.tv_recoment_view.setVisibility(View.GONE);
            holder.recommentCount.setText(String.valueOf(item.getReply().getCount()));
        } else {
            holder.tv_recoment_view.setVisibility(View.VISIBLE);
            holder.recommentCount.setText(String.valueOf(item.getReply().getCount()));
        }

        // yeu thich
        holder.favoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitFavorite request = new InitFavorite();
                request.setUser(Config.TOKEN_USER);
                request.setProduct(item.getComment().getProduct());
                request.setComment(item.getComment().getId());

                if (item.getIsFavorite()) {
                    item.setIsFavorite(false);
                    item.getFavorites().setCount(item.getFavorites().getCount() - 1);
                    onChange();
                } else {
                    item.setIsFavorite(true);
                    item.getFavorites().setCount(item.getFavorites().getCount() + 1);
                    onChange();
                }

                MyRetrofitSmartFind.getInstanceSmartFind().initFavorite(request).enqueue(new Callback<CheckPhoneResponse>() {
                    @Override
                    public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {
                        if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {

                        } else {
                            Toast.makeText(context, "Hiện tại bạn không thể thích bình luận này", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckPhoneResponse> call, Throwable t) {
                        Toast.makeText(context, "Hiện tại bạn không thể thích bình luận này", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // reply comment

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDetailRequest commentDetailRequest = new CommentDetailRequest(Config.TOKEN_USER, item.getComment().getId());

                mViewmodel.onCallBackAdapter(commentDetailRequest);
            }
        });
        holder.tv_amount_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDetailRequest commentDetailRequest = new CommentDetailRequest(Config.TOKEN_USER, item.getComment().getId());

                mViewmodel.onCallBackAdapter(commentDetailRequest);
            }
        });

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
        ImageView avatar;
        TextView customerName, time, favoris, reply, sartCount, content, recommentCount, tv_amount_reply;
        LinearLayout tv_recoment_view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.img_avt_comment);
            customerName = itemView.findViewById(R.id.tv_per_name);
            content = itemView.findViewById(R.id.tv_content);
            time = itemView.findViewById(R.id.tv_time);
            favoris = itemView.findViewById(R.id.btn_favorite);
            reply = itemView.findViewById(R.id.btn_recomment);
            sartCount = itemView.findViewById(R.id.tv_count_favorite);
            recommentCount = itemView.findViewById(R.id.tv_recomment_count);
            tv_recoment_view = itemView.findViewById(R.id.tv_recoment_view);
            tv_amount_reply = itemView.findViewById(R.id.tv_amount_reply);
        }
    }
}
