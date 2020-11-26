package com.poly.smartfindpro.ui.post.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesContract;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.List;

public class UtilitiesAdapter extends RecyclerView.Adapter<UtilitiesAdapter.ViewHolder> {
    private Context context;
    List<UtilitiesModel> mListItems;
    UtilitiesContract.ViewModel mViewModel;

    public UtilitiesAdapter(Context context, UtilitiesContract.ViewModel viewModel) {
        this.context = context;
        this.mViewModel = viewModel;

    }

    public void setListItem(List<UtilitiesModel> mListItems) {
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
        UtilitiesModel item = mListItems.get(position);
        holder.bindData(item.getTitle(), item.getImage(), item.isStatus());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (item.isStatus()) {
                  //  holder.img_Utiliti.setColorFilter(Color.BLACK);
                 //   holder.itemName.setTextColor(Color.BLACK);
                   onUpdateDataChange(position, false);
                } else {
                 //   holder.img_Utiliti.setColorFilter(Color.BLUE);
                 //   holder.itemName.setTextColor(Color.BLUE);
                    onUpdateDataChange(position, true);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItems != null ? mListItems.size() : 0;
    }

    private void onUpdateDataChange(int position, boolean status) {
        mListItems.get(position).setStatus(status);
        mViewModel.onBackData(mListItems);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private ImageView img_Utiliti;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_Title);
            img_Utiliti = itemView.findViewById(R.id.imgUtiliti);
        }

        public void bindData(String item, int image, boolean status) {
            itemName.setText(item);
            img_Utiliti.setImageResource(image);
            if (status) {
                img_Utiliti.setColorFilter(Color.BLUE);
                itemName.setTextColor(Color.BLUE);
            }else {
                img_Utiliti.setColorFilter(Color.BLACK);
                itemName.setTextColor(Color.BLACK);
            }
        }
    }

}
