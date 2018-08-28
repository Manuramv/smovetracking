package smove.com.smovebook.activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smove.com.smovebook.R;

public class BookingMapActivity extends CustomBaseActivity {
    private GoogleMap mMap;
    MapView mMapView;
    Marker marker;
    MarkerOptions markerOptions;
    Activity mActivity;
    LocationManager mLocationManager;
    private LatLngBounds.Builder builder;
    String provider;
    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private boolean locationPermissionGranted = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_map);
        mActivity = this;
        checkAndRequestPermissions();

        mMapView = (MapView) findViewById(R.id.mapBooking);
        mMapView.onCreate(savedInstanceState);
        mLocationManager = (LocationManager) mActivity.getSystemService(LOCATION_SERVICE);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            Log.e("TAG","Maps initializer"+e);
        }
        showLocation();

    }


    public void showLocation(){

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // Add a marker in Sydney, Australia, and move the camera.
                /*if (facilityDetailCommonJsonLists.size() > 0) {
                    mapload = true;
                    for (int i = 0; i < facilityDetailCommonJsonLists.size(); i++) {
                        if (facilityDetailCommonJsonLists.get(i).getSource().equalsIgnoreCase("H")&& selectedOption.equalsIgnoreCase("H")) {
                            markerOptions = new MarkerOptions().position(new LatLng(facilityDetailCommonJsonLists.get(i).getLatitude(), facilityDetailCommonJsonLists.get(i).getLongitude()));
                            marker = mMap.addMarker(markerOptions);
                            marker.setTag(facilityDetailCommonJsonLists.get(i));
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(orangeMarker));
                            // mMap.addMarker(new MarkerOptions().position(new LatLng(facilityDetailCommonJsonLists.get(i).getLatitude(), facilityDetailCommonJsonLists.get(i).getLongitude()))).setIcon(BitmapDescriptorFactory.fromBitmap(orangeMarker));
                        } else if((facilityDetailCommonJsonLists.get(i).getSource().equalsIgnoreCase("C"))&&(selectedOption.equalsIgnoreCase("C"))) {
                            //mMap.addMarker(new MarkerOptions().position(new LatLng(facilityDetailCommonJsonLists.get(i).getLatitude(), facilityDetailCommonJsonLists.get(i).getLongitude()))).setIcon(BitmapDescriptorFactory.fromBitmap(blueMarker));
                            markerOptions = new MarkerOptions().position(new LatLng(facilityDetailCommonJsonLists.get(i).getLatitude(), facilityDetailCommonJsonLists.get(i).getLongitude()));
                            marker = mMap.addMarker(markerOptions);
                            marker.setTag(facilityDetailCommonJsonLists.get(i));
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(blueMarker));
                        }
                    }
                } else {
                    mapload = false;
                }*/
                if (ActivityCompat.checkSelfPermission(BookingMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);

                Criteria criteria = new Criteria();

                provider = mLocationManager.getBestProvider(criteria, false);
                final Location location = mLocationManager.getLastKnownLocation(provider);
                if (location != null) {
                    try {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                                .zoom(13)                   // Sets the zoom
                                //.bearing(90)                // Sets the orientation of the camera to east
                                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    } catch (Exception e) {
                        Log.e("exception ==>", e.getMessage() + "<=====");
                    }
                } else {
                    try {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.290270, 103.851959), 13));

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                                .zoom(13)                   // Sets the zoom
                                //.bearing(90)                // Sets the orientation of the camera to east
                                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    } catch (Exception e) {
                        Log.e("exception ==>", e.getMessage() + "<=====");
                    }

                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                            0, mLocationListener);
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                       /* FacilityDetailCommonJsonList indexItem = (FacilityDetailCommonJsonList) (marker.getTag());
                        if (indexItem != null) {
                            Intent fd = new Intent(mContext, FacilityDetails.class);
                            fd.putExtra("facility", indexItem);
                            //fd.putExtra("CURRENTLOCATION", location);
                            //fd.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(fd);
                        }*/
                        return false;
                    }
                });


            }
        });


    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            try {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(13)                   // Sets the zoom
                        //.bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } catch (Exception e) {
                Log.e("exception ==>", e.getMessage() + "<=====");
            }
            mLocationManager.removeUpdates(mLocationListener);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };






    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkAndRequestPermissions() {

        int locationPermission = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            mActivity.requestPermissions( listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult( requestCode,  permissions,  grantResults);

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) != null && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                        locationPermissionGranted = true;
                        showLocation();
                    } else {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showDialogOK("Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }

                    }
                }
            }
        }
    }


    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }


}
