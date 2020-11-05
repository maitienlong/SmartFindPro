package com.poly.smartfindpro.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Product> mListItem;
    private ArrayList<String> mListItemTest;
    HomeContract.ViewModel viewModel;

    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListItem(ArrayList<Product> listItem) {
        mListItem = listItem;
        notifyDataSetChanged();
    }
    public void setListItemTest(ArrayList<String> listItem) {
        mListItemTest = listItem;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_motel_room, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(lp);
        return new HomeAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = mListItemTest.get(position);
        // Anh
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = LoadImage(item.getAvatar());
//                holder.imgAvatar.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        holder.imgAvatar.setImageBitmap(bitmap);
//                    }
//                });
//            }
//        });
//        thread.start();
        holder.tvContent.setText(item);
    }

    @Override
    public int getItemCount() {
        return mListItemTest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvAccountName;
        TextView tvDate;
        TextView tvContent;
        TextView tvPrice;
        TextView tvAddress;
        TextView tvType;
        ImageView imgSex;
        TextView tvAmount;
        ImageButton imgComment;
        ImageButton imgShare;
        RecyclerView rvListImage;
        ImageView imgAddress;
        ImageView imgPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            tvAccountName = (TextView) itemView.findViewById(R.id.tvAccountName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            imgSex = (ImageView) itemView.findViewById(R.id.imgSex);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            imgComment = (ImageButton) itemView.findViewById(R.id.imgComment);
            imgShare = (ImageButton) itemView.findViewById(R.id.imgShare);
            rvListImage = (RecyclerView) itemView.findViewById(R.id.rvListImage);
            imgAddress = (ImageView) itemView.findViewById(R.id.imgAddress);
            imgPrice = (ImageView) itemView.findViewById(R.id.imgPrice);

        }
    }

    private Bitmap LoadImage(String link) {

        URL url;
        Bitmap bitmap = null;
        try {
            url = new URL(link);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            Log.e("LoadImage", e + "");
        }

        return bitmap;

    }
}

