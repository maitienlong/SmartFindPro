package com.poly.smartfindpro.ui.detailpost;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;

import java.util.ArrayList;
import java.util.List;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> mListProduct;
    private ArrayList<String> mListImage;

    public DetailImageAdapter(Context context, List<String> mListImage) {
        this.context = context;
        this.mListImage = (ArrayList<String>) mListImage;
    }
//
//    public void setListProduct(ArrayList<Product> listProduct) {
//        mListProduct = listProduct;
//        notifyDataSetChanged();
//    }
//
//    public void setListImage(ArrayList<String> listImage) {
//        mListImage = listImage;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_infor_post_image, null);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.user);
    }

    @Override
    public int getItemCount() {
        return mListImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.imageView);
        }
    }
}
