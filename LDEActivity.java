package com.example.second.getlocation;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class LDEActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener,LocationListener {
    com.firebase.client.Firebase ref;
    protected static final String TAG = "Location_Update_Sample";
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected final static String REQUISTING_LOCATION_UPDATES_KEY = "requisting_location_updates_key";
    protected final static String LOCATION_KEY = "location_key";
    protected final static String LAST_UPDATE_TIME_STRING_KEY = "last_update_time_string_key";
    protected GoogleApiClient mGoogleApiClient;
    EditText editText;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    protected TextView mLastUpdateTimeTextView;
    protected TextView mLatitudeTextView;
    protected TextView mLongitudeTextView;
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;
    protected Boolean mRequistingLocationUpdates;
    protected  TextView mAddressTextView;
    protected String mAddressLabel1;

    String locality;
    String subLocality;

    protected String mLastUpdateTime;
    String userId="";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lde);
        userId = getIntent().getStringExtra("UserId");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("BDE" +userId );


        com.firebase.client.Firebase.setAndroidContext(this);
        ref = new com.firebase.client.Firebase("https://bdelocation.firebaseio.com/");


        mStartUpdatesButton = (Button) findViewById(R.id.start_update_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);
        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_Update_Time_Text);
        mAddressTextView = (TextView)findViewById(R.id.Address);
        mLatitudeLabel = getResources().getString(R.string.Latitude_Label1);
        mLongitudeLabel = getResources().getString(R.string.Longitude_Label1);
        mLastUpdateTime = getResources().getString(R.string.last_update_time_text);
        mLatitudeLabel = getResources().getString(R.string.Latitude_Label1);
        mLongitudeLabel = getResources().getString(R.string.Longitude_Label1);
        mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_text);

        mRequistingLocationUpdates = false;
        mLastUpdateTime = "";
        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient();

    }
    private void add(Location location) {

        Firebase dinaRef = ref.child("User/" + userId);
        User user = new User();
        user.setUserId(userId);
        user.setLatitude((long) mCurrentLocation.getLatitude());
        user.setLongitude((long) mCurrentLocation.getLongitude());
        user.setLocality(locality);
        user.setSubLocality(subLocality);
        user.setLastUpdateTime(mLastUpdateTime);
        dinaRef.setValue(user);
    }



    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUISTING_LOCATION_UPDATES_KEY)) {
                mRequistingLocationUpdates = savedInstanceState.getBoolean(REQUISTING_LOCATION_UPDATES_KEY);
                setButtonsEnabledState();
                if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                    mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);

                }
                if (savedInstanceState.keySet().contains(LAST_UPDATE_TIME_STRING_KEY)) {
                    mLastUpdateTime = savedInstanceState.getString(LAST_UPDATE_TIME_STRING_KEY);
                }
                updateUI();
            }
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mCurrentLocation.getLatitude();
        mCurrentLocation.getLongitude();
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(), 1);

            if(addresses != null) {
                android.location.Address returnedAddress = addresses.get(0);
                locality=returnedAddress.getLocality();
                subLocality=returnedAddress.getSubLocality();
                // returnedAddress.getAddressLine();

                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");

                strReturnedAddress.append(subLocality + ""+",");

                strReturnedAddress.append(locality + ""+"\n");
                mAddressTextView.setText(strReturnedAddress.toString());
            }
            else{
                mAddressTextView.setText("No Address returned!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            mAddressTextView.setText("Canont get Address!");
        }
        Toast.makeText(this, getResources().getString(R.string.location_updated_message),Toast.LENGTH_SHORT).show();
        add(location);
    }



    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startUpdatesButtonHandler(View view) {
        if (!mRequistingLocationUpdates) {
            mRequistingLocationUpdates = true;
            setButtonsEnabledState();
            startLocationUpdates();

        }
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && mRequistingLocationUpdates) {
            startLocationUpdates();
        }}
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }}
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    public void stopUpdatesButtonHandler(View view) {
        if (mRequistingLocationUpdates) {
            mRequistingLocationUpdates = false;
            setButtonsEnabledState();
            stopLocationUpdates();

        }

    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");

        //  if (mCurrentLocation == null) {
        //    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //  mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        //updateUI();

        //}
        if (mRequistingLocationUpdates) {
            startLocationUpdates();
        }
    }
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUISTING_LOCATION_UPDATES_KEY, mRequistingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATE_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }

    private void updateUI() {
        mLatitudeTextView.setText(String.format("%s: %f", mLatitudeLabel,
                mCurrentLocation.getLatitude()));
        mLongitudeTextView.setText(String.format("%s: %f", mLongitudeLabel,
                mCurrentLocation.getLongitude()));
        mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,
                mLastUpdateTime));


    }


    private void setButtonsEnabledState() {
        if (mRequistingLocationUpdates) {
            mStartUpdatesButton.setEnabled(false);
            mStopUpdatesButton.setEnabled(true);
        } else {
            mStartUpdatesButton.setEnabled(true);
            mStopUpdatesButton.setEnabled(false);
        }
    }


}
    
