package com.poly.smartfindpro.ui.searchProduct;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivitySearchProductBinding;
import com.google.android.gms.maps.model.*;
import com.poly.smartfindpro.ui.map.MyItem;

public class SearchProductActivity extends BaseDataBindActivity<ActivitySearchProductBinding,
        SearchProductPresenter> implements SearchProductContract.ViewModel, OnMapReadyCallback, GoogleMap.OnCameraIdleListener,
        ClusterManager.OnClusterClickListener<MyItem>,
        ClusterManager.OnClusterInfoWindowClickListener<MyItem>,
        ClusterManager.OnClusterItemClickListener<MyItem>,
        ClusterManager.OnClusterItemInfoWindowClickListener<MyItem>{

    private GoogleMap mMap;
    private ClusterManager<MyItem> clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_product;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter = new SearchProductPresenter(this,this);
        mBinding.setPresenter(mPresenter);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.layoutMapSearchProduct);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void openFragment() {

    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.0717635, 107.9380385), 5f));
        clusterManager = new ClusterManager<MyItem>(getApplicationContext(), mMap);

        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(clusterManager);

        clusterManager.setOnClusterClickListener(this);
        clusterManager.setOnClusterInfoWindowClickListener(this);
        clusterManager.setOnClusterItemClickListener(this);
        clusterManager.setOnClusterItemInfoWindowClickListener(this);
        clusterManager.setAnimation(true);


        addItems(mMap);
    }

    private void addItems(GoogleMap googleMap) {

        // Set some lat/lng coordinates to start with.
        double lat = 16.0717635;
        double lng = 107.9380385;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 6d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng, "Title " + i, "Snippet " + i);
            clusterManager.addItem(offsetItem);
        }

        clusterManager.setRenderer(new MyRenderer(getApplicationContext(), googleMap, clusterManager));
    }

    @Override
    public boolean onClusterClick(Cluster<MyItem> cluster) {
        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<MyItem> cluster) {

    }

    @Override
    public boolean onClusterItemClick(MyItem item) {
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(MyItem item) {

    }

    private class MyRenderer extends DefaultClusterRenderer<MyItem> {

        private IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());;
        private ImageView mImageView;
        private float density;

        public MyRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
            density = context.getResources().getDisplayMetrics().density;
            mImageView = new ImageView(context);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(70, 70));
            mIconGenerator.setContentView(mImageView);
            mIconGenerator.setColor(Color.TRANSPARENT);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
            markerOptions.icon(getItemIcon()).title(item.getTitle());
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
            markerOptions.icon(getClusterIcon(cluster));
        }

        @Override
        protected void onClusterRendered(Cluster<MyItem> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
            // Marker is never null here

        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<MyItem> cluster) {
            return cluster.getSize() > 1;
        }

        private BitmapDescriptor getItemIcon() {
            mImageView.setImageResource(R.drawable.icon_location_blue);
            Bitmap icon = mIconGenerator.makeIcon();
            return BitmapDescriptorFactory.fromBitmap(icon);
        }

        private BitmapDescriptor getClusterIcon(Cluster<MyItem> cluster){
            return BitmapDescriptorFactory.fromBitmap(mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize())));
        }




    }
}