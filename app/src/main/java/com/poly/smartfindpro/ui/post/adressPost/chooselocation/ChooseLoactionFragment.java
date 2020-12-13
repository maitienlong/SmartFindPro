package com.poly.smartfindpro.ui.post.adressPost.chooselocation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.addressgoogle.Candidate;
import com.poly.smartfindpro.data.model.post.req.Address;
import com.poly.smartfindpro.data.model.post.req.Location;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.databinding.FragmentMapsChooseBinding;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ChooseLoactionFragment extends BaseDataBindFragment<FragmentMapsChooseBinding, ChooseLoactionPresenter> implements ChooseLocationContract.ViewModel, OnMapReadyCallback {

    private GoogleMap gMap;

    private List<Candidate> mListLocation;

    private PostRequest postRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_maps_choose;
    }

    private void getData() {
        Type type = new TypeToken<List<Candidate>>() {
        }.getType();

        Type typePostRequest = new TypeToken<PostRequest>() {
        }.getType();
        if (getArguments().getString(Config.DATA_CALL_BACK) != null && getArguments().getString(Config.POST_BUNDEL_RES) != null) {
            mListLocation = new Gson().fromJson(getArguments().getString(Config.DATA_CALL_BACK), type);
            postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), typePostRequest);
        }

    }

    @Override
    protected void initView() {
        getData();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_search_dialog);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        ArrayList<Marker> mListMarker = new ArrayList<>();
        for (int i = 0; i < mListLocation.size(); i++) {
            LatLng sydney = new LatLng(mListLocation.get(i).getGeometry().getLocation().getLat(), mListLocation.get(i).getGeometry().getLocation().getLng());
            Marker marker = gMap.addMarker(new MarkerOptions().position(sydney).title(mListLocation.get(i).getName()));
            mListMarker.add(marker);
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Marker marker : mListMarker) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();

        int padding = 360; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        gMap.moveCamera(cu);

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Address address = postRequest.getAddress();
                Location location = new Location();
                String longitude = String.valueOf(marker.getPosition().longitude);
                String latitude = String.valueOf(marker.getPosition().latitude);
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                address.setLocation(location);
                postRequest.setAddress(address);

                onShowDialog(marker.getTitle());

                return false;
            }
        });
    }


    private void onNextFragment(String jsonData) {
        Log.d("CheckLog", new Gson().toJson(postRequest));

        Intent intent = new Intent();

        intent.putExtra(Config.DATA_CALL_BACK, "3");

        intent.putExtra(Config.POST_BUNDEL_RES, jsonData);

        intent.putExtra(Config.POST_BUNDEL_RES_PHOTO, getArguments().getString(Config.POST_BUNDEL_RES_PHOTO));


        setResult(RESULT_OK, intent);

        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, jsonData);
        bundle.putString(Config.POST_BUNDEL_RES_PHOTO, getArguments().getString(Config.POST_BUNDEL_RES_PHOTO));

        onBackData();

        getBaseActivity().goToFragmentCallBackData(R.id.fl_post, new UtilitiesPostFragment(), bundle, getOnFragmentDataCallBack());
    }

    private void onShowDialog(String msg) {
        showAlertDialog("Xác nhật vị trí", "Bạn muốn chọn vị trí " + msg, "Xác nhận", "Hủy", true, new AlertDialogListener() {
            @Override
            public void onAccept() {
                if (postRequest.getAddress().getLocation() != null) {
                    onNextFragment(new Gson().toJson(postRequest));
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
