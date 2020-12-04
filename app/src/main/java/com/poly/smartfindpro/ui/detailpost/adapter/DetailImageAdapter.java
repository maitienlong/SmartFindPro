package com.poly.smartfindpro.ui.detailpost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.detailpost.Product;

import java.util.ArrayList;
import java.util.List;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Products> mListProduct;
    List<String> image = new ArrayList<>();

    public DetailImageAdapter(Context context, List<String> image) {
        this.context = context;
        this.image = (List<String>) image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_infor_post_image, null);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products item = mListProduct.get(position);
        image.add(MyRetrofitSmartFind.smartFind + item.getProduct().getInformation().getImage().get(position));
        holder.imageView.setImageResource(R.mipmap.user);
        Glide.
                with(context)
                .load(image.get(position))
                .placeholder(R.drawable.chucuongvlog)
                .error(R.drawable.babyred)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
