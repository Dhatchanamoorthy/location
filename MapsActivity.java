package com.example.second.getlocation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class MapsActivity extends ActionBarActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<User> list1;
    private ArrayList<User> LatLng;
    private static String TAG="MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list1 = (ArrayList) getIntent().getSerializableExtra("getData");
        for (int i = 0; i < list1.size(); i++) {
            Log.i(TAG, String.valueOf(list1.get(i)));

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }}


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        for(int i=0;i<list1.size();i++) {


            // Add a marker in Sydney and move the camera
            LatLng loc = new LatLng(list1.get(i).getLatitude(),list1.get(i).getLongitude());

            mMap.addMarker(new MarkerOptions().position(loc).title("UserId: " +list1.get(i).getUserId()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }


      }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;

            case R.id.menu_map:
                Intent intent = new Intent(getApplicationContext(), TLActivity.class);

                intent.putExtra("getData", list1);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    } }