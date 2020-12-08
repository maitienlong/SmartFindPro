package com.poly.smartfindpro.ui.searchProduct.map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.data.model.post.req.Information;
import com.poly.smartfindpro.data.model.post.req.Location;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.FragmentInforPostBinding;
import com.poly.smartfindpro.databinding.FragmentMapsSearchBinding;
import com.poly.smartfindpro.ui.post.adapter.ImageInforPostAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.InforPostContract;
import com.poly.smartfindpro.ui.post.inforPost.InforPostPresenter;
import com.poly.smartfindpro.ui.post.inforPost.RealPathUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MapsFragment extends BaseDataBindFragment<FragmentMapsSearchBinding, MapsPresenter> implements MapsContract.ViewModel, OnMapReadyCallback {

    private List<Products> mListProduct;

    private GoogleMap gMap;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_maps_search;
    }

    private void getData() {
        mListProduct = new ArrayList<>();
        if (getArguments() != null) {
            Type type = new TypeToken<List<Products>>() {
            }.getType();
            mListProduct = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);
            Log.d("CheckMap", new Gson().toJson(mListProduct));
        }

    }

    @Override
    protected void initView() {
        getData();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_search);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onMapReady);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        if (mListProduct != null && mListProduct.size() > 0) {


            gMap = googleMap;

            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            ArrayList<Marker> mListMarker = new ArrayList<>();
            for (int i = 0; i < mListProduct.size(); i++) {
                if (mListProduct.get(i).getAddress().getLocation() != null) {
                    LatLng sydney = new LatLng(Double.valueOf(mListProduct.get(i).getAddress().getLocation().getLatitude()), Double.valueOf(mListProduct.get(i).getAddress().getLocation().getLongitude()));
                    Marker marker = gMap.addMarker(new MarkerOptions().position(sydney));
                    mListMarker.add(marker);
                }
            }

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Marker marker : mListMarker) {
                builder.include(marker.getPosition());
            }

            LatLngBounds bounds = builder.build();

            int padding = 200; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            gMap.moveCamera(cu);

            gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Location location = new Location();
                    location.setLatitude(String.valueOf(marker.getPosition().latitude));
                    location.setLongitude(String.valueOf(marker.getPosition().longitude));
                    onCallBackData(location);
                    return false;
                }
            });

        } else {

        }
    }

    private void onCallBackData(Location location) {
        Intent intent = new Intent();

        intent.putExtra("data", new Gson().toJson(location));

        setResult(RESULT_OK, intent);

        onBackData();
    }
}
