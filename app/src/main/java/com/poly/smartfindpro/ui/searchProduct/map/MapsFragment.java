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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.data.model.post.req.Information;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.databinding.FragmentInforPostBinding;
import com.poly.smartfindpro.databinding.FragmentMapsSearchBinding;
import com.poly.smartfindpro.ui.post.adapter.ImageInforPostAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.InforPostContract;
import com.poly.smartfindpro.ui.post.inforPost.InforPostPresenter;
import com.poly.smartfindpro.ui.post.inforPost.RealPathUtil;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MapsFragment extends BaseDataBindFragment<FragmentMapsSearchBinding, MapsPresenter> implements MapsContract.ViewModel, OnMapReadyCallback {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_maps_search;
    }

    @Override
    protected void initView() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_search);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onMapReady);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-1, 1);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
