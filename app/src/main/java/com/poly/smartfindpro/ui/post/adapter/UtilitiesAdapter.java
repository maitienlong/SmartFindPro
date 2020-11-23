package com.poly.smartfindpro.ui.post.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.ArrayList;
import java.util.List;

public class UtilitiesAdapter extends RecyclerView.Adapter<UtilitiesAdapter.ViewHolder> {
    private Context context;
    List<UtilitiesModel> mListItems;
    List<UtilitiesModel> mListItemsChoosen = new ArrayList<>();


    public UtilitiesAdapter(Context context) {
        this.context = context;
    }

    public void setListItem( List<UtilitiesModel> mListItems) {
       this.mListItems = mListItems;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_utilitie, null);
        return new UtilitiesAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UtilitiesModel  item = mListItems.get(position);
        holder.bindData(item.getTitle(),item.getImage(),item.isStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isStatus()){
                    item.setStatus(false);
                }else {
                    item.setStatus(true);
                }
                if(callback != null) {
                    callback.onOptionClick();
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItems != null ? mListItems.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private ImageView img_Utiliti;
        private LinearLayout layoutParent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_Title);
            img_Utiliti = itemView.findViewById(R.id.imgUtiliti);
            layoutParent = itemView.findViewById(R.id.layoutParent);
        }

        public void bindData(String item, int image, boolean status) {
            itemName.setText(item);
            img_Utiliti.setImageResource(image);
            if (status){
                img_Utiliti.setBackgroundColor(Color.BLUE);
                itemName.setTextColor(Color.BLUE);
            }
        }
    }

    public interface OnOptionClick{
        void onOptionClick();
    }

    private OnOptionClick callback;

    public void setOnOptionClick(OnOptionClick onOptionClick){
        callback = onOptionClick;
    }

    public List<UtilitiesModel> getListUltilities() {
        mListItemsChoosen.clear();
        for (int i = 0; i < mListItems.size(); i++){
            if(mListItems.get(i).isStatus()){
                mListItemsChoosen.add(mListItems.get(i));
            }
        }
        return mListItemsChoosen;
    }
}
