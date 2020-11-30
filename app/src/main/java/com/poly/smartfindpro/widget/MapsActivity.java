package com.poly.smartfindpro.widget;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.ui.map.MapFragment;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Initialize fragmetn
        Fragment maFragment = new MapFragment();
        //open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frm_layout, maFragment)
                .commit();
    }
}
