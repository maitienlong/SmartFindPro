package com.poly.smartfindpro.ui.post.adapter;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {
    private List<String> mListItem;

    public MainSliderAdapter(List<String> mListItem) {
        this.mListItem = mListItem;
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        String item = mListItem.get(position);
        imageSlideViewHolder.bindImageSlide(item);

    }
}
