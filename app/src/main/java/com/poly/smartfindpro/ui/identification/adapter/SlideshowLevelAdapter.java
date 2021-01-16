package com.poly.smartfindpro.ui.identification.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.poly.smartfindpro.R;

import java.util.List;

public class SlideshowLevelAdapter extends PagerAdapter {
    List<RankAccount> mListPhotoLevel;
    private Context mContex;
    private LayoutInflater mLayoutInflater;

    public SlideshowLevelAdapter(Context mContex, List<RankAccount> mListPhoto) {
        this.mContex = mContex;
        this.mListPhotoLevel = mListPhoto;
    }

    @Override
    public int getCount() {
        return mListPhotoLevel != null ? mListPhotoLevel.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = mLayoutInflater.inflate(R.layout.item_slideshow_level, null);

        RankAccount item = mListPhotoLevel.get(position);

        ImageView imageView = view.findViewById(R.id.img_slideshow);
        TextView tvFuntion = view.findViewById(R.id.tv_funtion);

        imageView.setImageResource(item.getImge());
        tvFuntion.setText(item.getFuntion());

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
