package com.poly.smartfindpro.ui.post.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.area.result.ListArea;

import java.util.List;

public class SpinnerAreaAdapter extends ArrayAdapter<ListArea> {

    public SpinnerAreaAdapter(@NonNull Context context, @NonNull List<ListArea> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_spinner_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.tv_name_spinner);
        ListArea currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getAreaName());
        }
        return convertView;
    }
}
