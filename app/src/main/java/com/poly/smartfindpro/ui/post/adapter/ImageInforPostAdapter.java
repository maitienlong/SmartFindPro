package com.poly.smartfindpro.ui.post.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
//import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
        ImageInforPost  item = mList.get(position);
        holder.img_infor_post.setImageBitmap(item.getBitmap());
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


    public Bitmap loadBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 1080);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}


