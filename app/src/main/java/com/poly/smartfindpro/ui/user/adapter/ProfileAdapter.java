package com.poly.smartfindpro.ui.user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.product.res.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;

    private List<Product> productList;

    public ProfileAdapter(Context context) {
        this.context = context;

    }

    public void setItemList(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_profile, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(lp);
        return new ProfileAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        Product item = productList.get(position);
        holder.tv_username_post.setText(item.getUser().getUserName());
        holder.tv_adress_profile.setText(item.getAddress().getDetailAddress() + "," + item.getAddress().getCommuneWardTown() + "," + item.getAddress().getDistrictsTowns() + "," + item.getAddress().getProvinceCity());
        holder.tv_price_product.setText(item.getInformation().getPrice().toString());
        holder.tv_title_post.setText(item.getContent());
        Log.d("ngay tao", item.getCreateAt());
        holder.tv_time_post.setText(item.getCreateAt());

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        Log.d("Time Now", currentDateTimeString);

        String strDate = item.getCreateAt();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
            Log.d("converts", date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        holder.btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.btn_menu);
                popupMenu.inflate(R.menu.menu_item_profile);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.btn_delete_menu:
                                Toast.makeText(context, "Xóa", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.btn_edit_menu:
                                Toast.makeText(context, "Sửa", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }

                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_menu;
        private TextView tv_username_post, tv_adress_profile, tv_price_product, tv_time_post, tv_title_post;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_menu = itemView.findViewById(R.id.btn_menu_profile_post);
            tv_username_post = itemView.findViewById(R.id.tv_username_post);
            tv_adress_profile = itemView.findViewById(R.id.tv_adress_profile);
            tv_price_product = itemView.findViewById(R.id.tv_price_product);
            tv_time_post = itemView.findViewById(R.id.tv_time_post);
            tv_title_post = itemView.findViewById(R.id.tv_title_post);
        }
    }
}
