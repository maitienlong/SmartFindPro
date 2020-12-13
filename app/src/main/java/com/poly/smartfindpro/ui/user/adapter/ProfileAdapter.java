package com.poly.smartfindpro.ui.user.adapter;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.detailpost.DetailPostActivity;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;

    private FragmentManager mFragmentManager;

    private List<Products> productList;


    public ProfileAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.mFragmentManager = fragmentManager;
    }

    public void setItemList(List<Products> productList) {
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

        Products item = productList.get(position);

        if (item != null) {


            List<String> image = new ArrayList<>();

            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date date = dateFormatter.parse(item.getCreateAt());

                holder.tv_time_post.setText(getTime(date));

            } catch (Exception e) {

            }

            holder.tv_username_post.setText(item.getUser().getUserName());
            holder.tv_adress_profile.setText(item.getAddress().getDetailAddress() + "," + item.getAddress().getCommuneWardTown() + "," + item.getAddress().getDistrictsTowns() + "," + item.getAddress().getProvinceCity());
            holder.tv_price_product.setText(NumberFormat.getNumberInstance().format(item.getProduct().getInformation().getPrice()));
            holder.tv_title_post.setText(item.getContent());

            if (item.getProduct().getInformation().getImage() != null) {

                if (item.getProduct().getInformation().getImage().size() < 4) {
                    for (int i = 0; i < item.getProduct().getInformation().getImage().size(); i++) {
                        image.add(MyRetrofitSmartFind.smartFind + item.getProduct().getInformation().getImage().get(i));
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        image.add(MyRetrofitSmartFind.smartFind + item.getProduct().getInformation().getImage().get(i));
                    }
                }


                if (image.size() == 3) {
                    Glide.
                            with(context)
                            .load(image.get(0))
                            .placeholder(R.mipmap.imgplaceholder)
                            .error(R.mipmap.imgplaceholder)
                            .into(holder.img1);
                    Glide.
                            with(context)
                            .load(image.get(1))
                            .placeholder(R.mipmap.imgplaceholder)
                            .error(R.mipmap.imgplaceholder)
                            .into(holder.img2);
                    Glide.
                            with(context)
                            .load(image.get(2))
                            .placeholder(R.mipmap.imgplaceholder)
                            .error(R.mipmap.imgplaceholder)
                            .into(holder.img3);
                } else if (image.size() == 2) {
                    Glide.
                            with(context)
                            .load(image.get(0))
                            .placeholder(R.mipmap.imgplaceholder)
                            .error(R.mipmap.imgplaceholder)
                            .into(holder.img1);
                    Glide.
                            with(context)
                            .load(image.get(1))
                            .placeholder(R.mipmap.imgplaceholder)
                            .error(R.mipmap.imgplaceholder)
                            .into(holder.img2);

                } else {
                    Glide.
                            with(context)
                            .load(image.get(0))
                            .placeholder(R.mipmap.imgplaceholder)
                            .error(R.mipmap.imgplaceholder)
                            .into(holder.img1);
                }
            }
            Glide.
                    with(context)
                    .load(MyRetrofitSmartFind.smartFind + item.getUser().getAvatar())
                    .placeholder(R.mipmap.imgplaceholder)
                    .error(R.mipmap.imgplaceholder)
                    .into(holder.img_avatar);


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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailPostActivity.class);
                    intent.putExtra(Config.POST_BUNDEL_RES, new Gson().toJson(item));
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
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
            return String.valueOf(Integer.valueOf(getTime("HH", today)) - Integer.valueOf(getTime("HH", datePost))) + " giờ";
        } else if (!getTime("mm", today).equals(getTime("mm", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("mm", today)) - Integer.valueOf(getTime("mm", datePost))) + " phút";
        } else if (!getTime("ss", today).equals(getTime("ss", datePost))) {
            return String.valueOf(Integer.valueOf(getTime("ss", today)) - Integer.valueOf(getTime("ss", datePost))) + " giây";
        }
        return dateOK;
    }

    private String getTime(String type, Date date) {
        DateFormat dateFormat = new SimpleDateFormat(type);
        return dateFormat.format(date);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_menu;
        private TextView tv_username_post, tv_adress_profile, tv_price_product, tv_time_post, tv_title_post;
        private ImageView img1, img2, img3, img_avatar;

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
            img_avatar = itemView.findViewById(R.id.img_avatar);
        }
    }
}
