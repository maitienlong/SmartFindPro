package com.poly.smartfindpro.ui.post.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.post.model.ImageInforPost;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImageInforPostAdapter extends RecyclerView.Adapter<ImageInforPostAdapter.ViewHolder> {

    private Context context;
    private List<ImageInforPost> mList;

    public ImageInforPostAdapter(Context context, List<ImageInforPost> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_infor_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvTitle.setText(mList.get(position).getmName());
        holder.img_infor_post.setImageURI(mList.get(position).getUri());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_infor_post;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_infor_post = itemView.findViewById(R.id.img_infor_post);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}


