package com.poly.smartfindpro.ui.searchProduct.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

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
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.FragmentMapsSearchBinding;
import com.poly.smartfindpro.ui.post.PostActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MapsFragment extends BaseDataBindFragment<FragmentMapsSearchBinding, MapsPresenter> implements MapsContract.ViewModel, OnMapReadyCallback {

    private List<Products> mListProduct;

    private int MY_PERMISSIONS_REQUEST_LOCATION = 678;
    private int MY_PERMISSIONS_REQUEST_COARSE = 679;

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
            List<Products> mListProductFilter = new ArrayList<>();

            for (Products item : mListProduct) {
                if (item.getAddress().getLocation() != null) {
                    if (item.getAddress().getLocation().getLongitude() != null && item.getAddress().getLocation().getLatitude() != null) {
                        mListProductFilter.add(item);
                    }

                }
            }

            Log.d("CheckMapMarker", new Gson().toJson(mListProductFilter));

            gMap = googleMap;

            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    String[] fineLocation = {Manifest.permission.ACCESS_FINE_LOCATION};
                    String[] coarseLocation = {Manifest.permission.ACCESS_COARSE_LOCATION};
                    requestPermissions(fineLocation, MY_PERMISSIONS_REQUEST_LOCATION);
                    requestPermissions(coarseLocation, MY_PERMISSIONS_REQUEST_COARSE);
                } else {
                    gMap.setMyLocationEnabled(true);
                }
            } else {
                gMap.setMyLocationEnabled(true);
            }

            ArrayList<Marker> mListMarker = new ArrayList<>();
            for (int i = 0; i < mListProductFilter.size(); i++) {
                Log.d("CheckLatLng", mListProductFilter.size() + " - " + mListProductFilter.get(i).getAddress().getLocation().getLatitude().trim() + " - " + mListProductFilter.get(i).getAddress().getLocation().getLongitude().trim());
                LatLng sydney = new LatLng(Double.parseDouble(mListProductFilter.get(i).getAddress().getLocation().getLatitude().trim()), Double.parseDouble(mListProductFilter.get(i).getAddress().getLocation().getLongitude().trim()));
                Marker marker = gMap.addMarker(new MarkerOptions().position(sydney));
                marker.setTag(mListProduct.get(i).getId());
                mListMarker.add(marker);

            }
            if (mListMarker != null && mListMarker.size() > 0) {
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
                        onCallBackData(marker.getTag().toString());
                        return false;
                    }
                });
            }

        } else {

        }
    }

    private void onCallBackData(String tag) {
        Intent intent = new Intent();

        intent.putExtra("data", tag);

        setResult(RESULT_OK, intent);

        onBackData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
