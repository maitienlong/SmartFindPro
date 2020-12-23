package com.poly.smartfindpro.ui.post.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;

import java.util.List;

//import com.squareup.picasso.Picasso;

public class ShowImagePostAdapter extends RecyclerView.Adapter<ShowImagePostAdapter.ViewHolder> {

    private Context context;
    private List<ImageInforPost> mList;

    public ShowImagePostAdapter(Context context) {
        this.context = context;

    }

    public void setItemView(List<ImageInforPost> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_imageview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ImageInforPost item = mList.get(position);

        holder.imageView.setImageBitmap(item.getBitmap());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(item);
                setItemView(mList);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_show);
            btnDelete = itemView.findViewById(R.id.btn_delete_image);
        }
    }

}


