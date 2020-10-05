package com.poly.smartfindpro.utils;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.smartfindpro.widget.CommonToolbar;
import com.poly.smartfindpro.widget.MainToolbar;
import com.poly.smartfindpro.widget.VerticalSpaceItemDecoration;

public class BindingUtils {

    @BindingAdapter(value = {"android:adapter", "android:divider"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter,
                                  boolean divider) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    @BindingAdapter(value = {"android:adapter", "android:divider", "android:column"}, requireAll = false)
    public static void setColumnAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter,
                                        boolean divider, int column) {
        recyclerView.setLayoutManager(
                new GridLayoutManager(recyclerView.getContext(), column));
        recyclerView.setHasFixedSize(true);
        if (divider) {
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(30));
        }
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"android:horizontalAdapter", "android:divider"}, requireAll = false)
    public static void setHorizontalAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter,
                                            boolean divider) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(recyclerView.getContext(), RecyclerView.HORIZONTAL,
                        false));
        recyclerView.setHasFixedSize(true);
        if (divider) {
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(30));
        }
        recyclerView.setAdapter(adapter);
    }


    @InverseBindingAdapter(attribute = "selectedValue",event = "selectedValueAttrChanged")
    public static String captureSelectedValue(Spinner spinner){
        return (String) spinner.getSelectedItem();
    }

    @BindingAdapter("onCheckedChangedListener")
    public static void setOnCheckedChangedListener(SwitchCompat v,
                                                   CompoundButton.OnCheckedChangeListener listener) {
        v.setOnCheckedChangeListener(listener);
    }

    @BindingAdapter("title")
    public static void setTitle(MainToolbar toolbar, String title) {
        toolbar.setTitle(title);
    }

    @BindingAdapter("onBackClick")
    public static void setOnBackClickListener(CommonToolbar toolbar, View.OnClickListener listener) {
        toolbar.setOnBackClickListener(listener);
    }

    @BindingAdapter("onNotiClick")
    public static void setOnNotiClickListener(CommonToolbar toolbar, View.OnClickListener listener) {

    }

    @BindingAdapter("onCloseClick")
    public static void setOnCloseClickListener(CommonToolbar toolbar, View.OnClickListener listener) {
        toolbar.setOnCloseClickListener(listener);
    }

    @BindingAdapter("title")
    public static void setTitle(CommonToolbar toolbar, String title) {
        toolbar.setTitle(title);
    }

    @BindingAdapter("notiNumber")
    public static void setNotiNumber(CommonToolbar toolbar, String number) {

    }

    @BindingAdapter("hideBackIcon")
    public static void hideBackIcon(CommonToolbar toolbar, boolean hide) {
        toolbar.hideBackIcon(hide);
    }

    @BindingAdapter("hideNotiIcon")
    public static void hiddenCloseIcon(CommonToolbar toolbar, boolean hide) {
        toolbar.hiddenCloseIcon(hide);
    }

    @BindingAdapter("invisibleBackIcon")
    public static void invisibleBackIcon(CommonToolbar toolbar, boolean hide) {
        toolbar.invisibleBackIcon(hide);
    }

    @BindingAdapter("invisibleNotiIcon")
    public static void invisibleNotiIcon(CommonToolbar toolbar, boolean invisible) {

    }

    @BindingAdapter("showCloseIcon")
    public static void showCloseIcon(CommonToolbar toolbar, boolean hide) {
        toolbar.showCloseIcon(hide);
    }
}
