package com.poly.smartfindpro.ui.post.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.post.model.ImageInforPost;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

//import com.squareup.picasso.Picasso;

public class ShowImagePostAdapter extends RecyclerView.Adapter<ShowImagePostAdapter.ViewHolder> {

    private Context context;
    private List<Bitmap> mList;

    public ShowImagePostAdapter(Context context) {
        this.context = context;

    }

    public void setItemView(List<Bitmap> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_infor_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bitmap item = mList.get(position);

        holder.img_infor_post.setImageBitmap(item);


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


//    public Bitmap convertStringToBitmap(String encodedString) {
//        try {
//            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            return bitmap;
//        } catch (Exception e) {
//            e.getMessage();
//            return null;
//        }
//    }
}


