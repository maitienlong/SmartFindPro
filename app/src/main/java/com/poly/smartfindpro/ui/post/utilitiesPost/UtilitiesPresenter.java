package com.poly.smartfindpro.ui.post.utilitiesPost;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.ArrayList;
import java.util.List;

public class UtilitiesPresenter implements UtilitiesContract.Presenter {
    private List<UtilitiesModel> mListUtilities;

    private List<String> utilitiesModelList;
    private List<UtilitiesModel> mListUpdate;

    private Context context;

    private UtilitiesContract.ViewModel mViewModel;

    private PostRequest postRequest;


    public UtilitiesPresenter(Context context, UtilitiesContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData() {
        mListUpdate = new ArrayList<>();
    }

    public void setmListUpdate(List<UtilitiesModel> mListUpdate) {
        this.mListUpdate = mListUpdate;
    }

    public void setPostRequest(PostRequest postRequest) {
        this.postRequest = postRequest;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public List<UtilitiesModel> CreateData() {
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

        return mListUtilities;
    }

    public void onSubmit() {

        List<String> utilitieList = new ArrayList<>();

        if (postRequest != null) {
            if (postRequest.getInformation() == null) {
                mViewModel.showMessage("Thông tin bài viết đang trống");
            } else if (postRequest.getAddress() == null) {
                mViewModel.showMessage("Địa chỉ bài viết đang trống");
            } else if (postRequest.getCategory() == null) {
                mViewModel.showMessage("Thể loại đang trống");
            } else {

                if (postRequest.getInformation().getAmountPeople() == null) {
                    Toast.makeText(context, "Số lượng người đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getInformation().getDeposit() == null) {
                    Toast.makeText(context, "Số tiền đặt cọc đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getInformation().getElectricBill() == null) {
                    Toast.makeText(context, "Giá tiền điện đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getInformation().getGender() == null) {
                    Toast.makeText(context, "Giới tính đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getInformation().getPrice() == null) {
                    Toast.makeText(context, "Giá tiền đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getInformation().getWaterBill() == null) {
                    Toast.makeText(context, "Giá tiền nước đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getCategory() == null) {
                    Toast.makeText(context, "Thể loại bài đăng đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getAddress().getLocation() == null) {
                    Toast.makeText(context, "Vị trí địa chỉ đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getAddress().getProvinceCity() == null) {
                    Toast.makeText(context, "Địa chỉ đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getAddress().getDistrictsTowns() == null) {
                    Toast.makeText(context, "Địa chỉ đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getAddress().getCommuneWardTown() == null) {
                    Toast.makeText(context, "Địa chỉ đang trống", Toast.LENGTH_SHORT).show();
                } else if (postRequest.getAddress().getDetailAddress() == null) {
                    Toast.makeText(context, "Địa chỉ đang trống", Toast.LENGTH_SHORT).show();
                } else {
                    for (UtilitiesModel item : mListUpdate) {
                        if (item.isStatus()) {
                            utilitieList.add(item.getTitle());
                        }
                    }

                    if (utilitieList.isEmpty()) {
                        Toast.makeText(context, "Tiện ích đang trống", Toast.LENGTH_SHORT).show();

                    } else {
                        postRequest.setUtilities(utilitieList);

                        mViewModel.onNext(new Gson().toJson(postRequest));
                    }

                }

            }


        }


    }
}
