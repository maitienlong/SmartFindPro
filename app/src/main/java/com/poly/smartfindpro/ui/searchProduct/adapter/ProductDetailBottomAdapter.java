package com.poly.smartfindpro.ui.searchProduct.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.detailpost.DetailPostActivity;
import com.poly.smartfindpro.ui.searchProduct.SearchProductContract;

import java.util.List;

public class ProductDetailBottomAdapter extends RecyclerView.Adapter<ProductDetailBottomAdapter.ViewHolder> {
    private List<Products> mList;

    private Context mContext;

    private SearchProductContract.ViewModel mViewModel;

    public ProductDetailBottomAdapter(Context mContext, SearchProductContract.ViewModel mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
    }

    public void setItemList(List<Products> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductDetailBottomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_product_detail_bottom, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(lp);
        return new ProductDetailBottomAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products item = mList.get(position);
        holder.tv_nguoi_dang.setText(item.getUser().getFullname());
        holder.tv_the_loai.setText(item.getProduct().getCategory());
        holder.tv_so_luong.setText(item.getProduct().getInformation().getAmountPeople() + "");
        holder.tv_dia_chi.setText(item.getAddress().getDetailAddress() + ", " + item.getAddress().getCommuneWardTown() + ", " + item.getAddress().getDistrictsTowns() + ", " + item.getAddress().getProvinceCity());
        holder.tv_gia.setText(item.getProduct().getInformation().getPrice() + "");
        holder.tv_title.setText(item.getContent());


        Glide.
                with(mContext)
                .load(MyRetrofitSmartFind.smartFind + item.getProduct().getInformation().getImage().get(0))
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgerror)
                .into(holder.img_detai);

        Glide.
                with(mContext)
                .load(MyRetrofitSmartFind.smartFind + item.getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgerror)
                .into(holder.imgAvatar);

        holder.btn_xem_chi_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailPostActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Config.POST_BUNDEL_RES, new Gson().toJson(item));
                mContext.startActivity(intent);
            }
        });

        holder.btn_show_map.setOnClickListener(v -> mViewModel.onResultAdapter(item.getId()));

        holder.btnShowGoogleMap.setOnClickListener(v -> Config.openGMap(mContext, item.getAddress().getLocation().getLatitude(), item.getAddress().getLocation().getLongitude()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nguoi_dang, tv_the_loai, tv_so_luong, tv_dia_chi, tv_gia, tv_title;
        private ImageView img_detai;
        private CircularImageView imgAvatar;
        private CardView btn_xem_chi_tiet, btn_show_map, btnShowGoogleMap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nguoi_dang = itemView.findViewById(R.id.tv_nguoi_dang);
            tv_the_loai = itemView.findViewById(R.id.tv_the_loai);
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong);
            tv_dia_chi = itemView.findViewById(R.id.tv_dia_chi);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_title = itemView.findViewById(R.id.tv_title);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            img_detai = itemView.findViewById(R.id.img_detai);

            btn_xem_chi_tiet = itemView.findViewById(R.id.btn_xem_chi_tiet);
            btn_show_map = itemView.findViewById(R.id.btn_show_map);
            btnShowGoogleMap = itemView.findViewById(R.id.btn_show_gmap);

        }
    }
}
