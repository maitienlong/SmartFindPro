package com.poly.smartfindpro.widget;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.map.MapFragment;
import com.poly.smartfindpro.ui.map.MyItem;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends AppCompatActivity {
    private ClusterManager<MyItem> clusterManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Khởi tạo fragment
        Fragment maFragment = new MapFragment();
        // Mở fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frm_layout, maFragment)
                .commit();
    }

    private void setUpClusterer() {
        // Vị trí map.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Khởi tạo nội dung và bản đồ
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = new ClusterManager<MyItem>(context, map);

        // Điểm lắng nghe sự kiện từ map và thực thi cluster
        // manager.
        map.setOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);
        //Thêm các mục cụm (điểm đánh dấu) vào trình quản lý cụm
        addItems();
    }

    private void addItems() {
        // đưa 1 số tọa độ bắt đầu
        double lat = null;
        double lng = null;

        // thêm 10 cụm gần nhau
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng, "Title " + i, "Snippet " + i);
            clusterManager.addItem(offsetItem);
        }
    }
    //lấy tọa độ các điểm đã được chọn để


}
