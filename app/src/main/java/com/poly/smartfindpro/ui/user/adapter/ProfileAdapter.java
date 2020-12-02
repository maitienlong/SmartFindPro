package com.poly.smartfindpro.ui.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.post.adapter.UtilitiesAdapter;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesContract;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;
import com.poly.smartfindpro.ui.user.profile.ProfileContact;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;
    ProfileContact.ViewModel mViewModel;

    public ProfileAdapter(Context context, ProfileContact.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_profile, null);
        return new ProfileAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
