package com.poly.smartfindpro.ui.detailpost.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

import java.util.List;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.ViewHolder> {
    private Context context;

    List<String> image;

    public DetailImageAdapter(Context context) {
        this.context = context;
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
        String item = image.get(position);

        Glide.
                with(context)
                .load(MyRetrofitSmartFind.smartFind + item)
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View alertLayout = inflater.inflate(R.layout.dia_log_custom_image_detail, null);
                final ImageView img_detail = (ImageView) alertLayout.findViewById(R.id.img_detail);
                final Button btn_close = (Button) alertLayout.findViewById(R.id.btn_close);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setView(alertLayout);
                alert.setCancelable(true);
                AlertDialog dialog = alert.create();
                dialog.show();
                Glide.
                        with(context)
                        .load(MyRetrofitSmartFind.smartFind + item)
                        .placeholder(R.mipmap.imgplaceholder)
                        .error(R.mipmap.imgplaceholder)
                        .into(img_detail);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });
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
