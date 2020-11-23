package com.poly.smartfindpro.ui.post.utilitiesPost;

import android.content.Context;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.login.createPassword.CreatePasswordContract;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.ArrayList;
import java.util.List;

public class UtilitiesPresenter implements UtilitiesContract.Presenter {
    List<UtilitiesModel> mListUtilities;
    private Context context;

    private UtilitiesContract.ViewModel mViewModel;

    public UtilitiesPresenter(Context context, UtilitiesContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public List<UtilitiesModel> createData() {
        mListUtilities = new ArrayList<>();
        UtilitiesModel wifi = new UtilitiesModel(R.drawable.ic_baseline_wifi_24, "Wifi", false);
        UtilitiesModel anninh = new UtilitiesModel(R.drawable.ic_baseline_security_24, "An Ninh", false);
        UtilitiesModel wc = new UtilitiesModel(R.drawable.ic_baseline_wc_24, "Nhà vệ sinh", false);
        UtilitiesModel dexe = new UtilitiesModel(R.drawable.ic_baseline_two_wheeler_24, "Để xe", false);
        UtilitiesModel thucung = new UtilitiesModel(R.drawable.ic_baseline_pets_24, "Thú cưng", false);
        UtilitiesModel time = new UtilitiesModel(R.drawable.ic_baseline_alarm_24, "Giờ giấc", false);
        UtilitiesModel nhaan = new UtilitiesModel(R.drawable.ic_baseline_restaurant_24, "Nhà ăn", false);
        UtilitiesModel key = new UtilitiesModel(R.drawable.ic_baseline_vpn_key_24, "Phòng riêng", false);
        UtilitiesModel giuong = new UtilitiesModel(R.drawable.ic_baseline_airline_seat_flat_24, "Giường", false);
        UtilitiesModel baby = new UtilitiesModel(R.drawable.ic_baseline_child_care_24, "Trẻ em", false);
        UtilitiesModel nhaan1 = new UtilitiesModel(R.drawable.ic_baseline_restaurant_24, "Nhà ăn", false);
        UtilitiesModel key1 = new UtilitiesModel(R.drawable.ic_baseline_vpn_key_24, "Phòng riêng", false);
        UtilitiesModel giuong1 = new UtilitiesModel(R.drawable.ic_baseline_airline_seat_flat_24, "Giường", false);
        UtilitiesModel baby1 = new UtilitiesModel(R.drawable.ic_baseline_child_care_24, "Trẻ em", false);

        mListUtilities.add(wifi);
        mListUtilities.add(anninh);
        mListUtilities.add(wc);
        mListUtilities.add(dexe);
        mListUtilities.add(thucung);
        mListUtilities.add(time);
        mListUtilities.add(nhaan);
        mListUtilities.add(key);
        mListUtilities.add(giuong);
        mListUtilities.add(baby);
        mListUtilities.add(nhaan1);
        mListUtilities.add(key1);
        mListUtilities.add(giuong1);
        mListUtilities.add(baby1);

        return mListUtilities;
    }
}
