package com.poly.smartfindpro.ui.user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.product.res.Image;
import com.poly.smartfindpro.data.model.product.res.Product;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;

    private List<Product> productList;

    public ProfileAdapter(Context context) {
        this.context = context;
        getTime();
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

        List<String> image = new ArrayList<>();

        holder.tv_username_post.setText(item.getUser().getUserName());
        holder.tv_adress_profile.setText(item.getAddress().getDetailAddress() + "," + item.getAddress().getCommuneWardTown() + "," + item.getAddress().getDistrictsTowns() + "," + item.getAddress().getProvinceCity());
        holder.tv_price_product.setText(item.getInformation().getPrice().toString());
        holder.tv_title_post.setText(item.getContent());
        Log.d("ngay tao", item.getCreateAt());
        holder.tv_time_post.setText(item.getCreateAt());

        if (item.getInformation().getImage().size() < 4) {
            for (int i = 0; i < item.getInformation().getImage().size(); i++) {
                image.add(MyRetrofitSmartFind.smartFind + item.getInformation().getImage().get(i));
            }
        } else {
            for (int i = 0; i < 3; i++) {
                image.add(MyRetrofitSmartFind.smartFind + item.getInformation().getImage().get(i));
            }
        }

        if (image.size() == 3) {
            Glide.
                    with(context)
                    .load(image.get(0))
                    .placeholder(R.drawable.chucuongvlog)
                    .error(R.drawable.babyred)
                    .into(holder.img1);
            Glide.
                    with(context)
                    .load(image.get(1))
                    .placeholder(R.drawable.chucuongvlog)
                    .error(R.drawable.babyred)
                    .into(holder.img2);
            Glide.
                    with(context)
                    .load(image.get(2))
                    .placeholder(R.drawable.chucuongvlog)
                    .error(R.drawable.babyred)
                    .into(holder.img3);
        } else if (image.size() == 2) {
            Glide.
                    with(context)
                    .load(image.get(0))
                    .placeholder(R.drawable.chucuongvlog)
                    .error(R.drawable.babyred)
                    .into(holder.img1);
            Glide.
                    with(context)
                    .load(image.get(1))
                    .placeholder(R.drawable.chucuongvlog)
                    .error(R.drawable.babyred)
                    .into(holder.img2);

        } else {
            Glide.
                    with(context)
                    .load(image.get(0))
                    .placeholder(R.drawable.chucuongvlog)
                    .error(R.drawable.babyred)
                    .into(holder.img1);
        }


        Log.d("HiHi", image.size() + "");

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

    private void getTime() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd hhmmss");
        dateFormatter.setLenient(false);
        Date today = new Date();
        String s = dateFormatter.format(today);
        Log.d("Timenow", s);
        int year = today.getYear();
        int month = today.getMonth();
        int day = today.getDate();
        int hour = today.getHours();
        int minute = today.getMinutes();
        int second = today.getSeconds();

        Log.d("year", String.valueOf(year));
        Log.d("year", String.valueOf(month));
        Log.d("year", String.valueOf(day));
        Log.d("year", String.valueOf(hour));
        Log.d("year", String.valueOf(minute));
        Log.d("year", String.valueOf(second));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_menu;
        private TextView tv_username_post, tv_adress_profile, tv_price_product, tv_time_post, tv_title_post;
        private ImageView img1, img2, img3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_menu = itemView.findViewById(R.id.btn_menu_profile_post);
            tv_username_post = itemView.findViewById(R.id.tv_username_post);
            tv_adress_profile = itemView.findViewById(R.id.tv_adress_profile);
            tv_price_product = itemView.findViewById(R.id.tv_price_product);
            tv_time_post = itemView.findViewById(R.id.tv_time_post);
            tv_title_post = itemView.findViewById(R.id.tv_title_post);
            img1 = itemView.findViewById(R.id.img_product_post1);
            img2 = itemView.findViewById(R.id.img_product_post2);
            img3 = itemView.findViewById(R.id.img_product_post3);
        }
    }
}
