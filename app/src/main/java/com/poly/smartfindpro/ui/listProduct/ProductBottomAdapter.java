package com.poly.smartfindpro.ui.listProduct;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.ui.detailpost.DetailPostActivity;

import java.util.List;

public class ProductBottomAdapter extends RecyclerView.Adapter<ProductBottomAdapter.ViewHolder> {
    private List<Products> mList;
    private Context mContext;

    public ProductBottomAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setItemList(List<Products> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ProductBottomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_product_bottom, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(lp);
        return new ProductBottomAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product = mList.get(position);

        holder.tvType.setText(product.getProduct().getCategory());
        holder.tvGender.setText("Giới tính: " + product.getProduct().getInformation().getGender());
        holder.tvAddress.setText("Địa chỉ: " + product.getAddress().getDetailAddress() + ", " + product.getAddress().getCommuneWardTown()
                + ", " + product.getAddress().getDistrictsTowns() + ", " + product.getAddress().getProvinceCity());
        holder.tvPrice.setText("Giá tiền: " + product.getProduct().getInformation().getPrice());
        holder.tvAmount.setText("Số người: " + product.getProduct().getInformation().getAmountPeople());
        holder.imgMarker.setImageResource(R.drawable.ic_marker);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailPostActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Config.POST_BUNDEL_RES,new Gson().toJson(product));
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvType, tvAddress, tvGender, tvPrice, tvAmount;
        private ImageView imgAvatar, imgMarker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvType = itemView.findViewById(R.id.tvType);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgMarker = itemView.findViewById(R.id.imgMarker);


        }
    }
}
