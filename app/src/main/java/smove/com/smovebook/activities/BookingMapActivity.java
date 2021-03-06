package smove.com.smovebook.activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.Menu;
import android.view.MenuItem;
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

import retrofit2.Response;
import smove.com.smovebook.R;
import smove.com.smovebook.networking.response.bookingapi.Datum;
import smove.com.smovebook.networking.response.bookingapi.GetBookingAvailabilityResponse;
import smove.com.smovebook.networking.response.carlocation.GetCarLocationResponse;
import smove.com.smovebook.utilities.SmoveConstants;

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
    private GetBookingAvailabilityResponse  bookAvailabilityData;
    private String bookAvailabilityDataStr;
    Bitmap redMarker, greenMarker;
    int height = 90;
    int width = 70;
    Response<GetBookingAvailabilityResponse> bookingAvailabilityData;
    private Response<GetCarLocationResponse> locationTrackingData;
    private boolean mapload=false;
    private String entryFrom;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_map);
        mActivity = this;
        checkAndRequestPermissions();

        //testing =  getIntent().getParcelableArrayListExtra("bookingavailability");
       // bookAvailabilityDataStr = getIntent().getStringExtra("bookingavailability");
       // bookAvailabilityData = bookAvailabilityDataStr;

        entryFrom = getIntent().getStringExtra("comingFrom");
        if(entryFrom.equalsIgnoreCase("bookingtaxi")){
            bookingAvailabilityData = SmoveConstants.BOOK_RESPONSE;
            setAppToolbar("Book a car",R.drawable.ic_back_arrow);
            Log.d("TAG","Booking availability::"+ bookingAvailabilityData.body().getData().size());
        } else if(entryFrom.equalsIgnoreCase("carlocations")){
            locationTrackingData = SmoveConstants.CAR_LOCATION;
            setAppToolbar("Locate a car",R.drawable.ic_back_arrow);
        }


        mMapView = (MapView) findViewById(R.id.mapBooking);
        mMapView.onCreate(savedInstanceState);
        mLocationManager = (LocationManager) mActivity.getSystemService(LOCATION_SERVICE);
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_redcar);
        Bitmap b = bitmapdraw.getBitmap();
        redMarker = Bitmap.createScaledBitmap(b, width, height, false);
        BitmapDrawable bitmapd = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_greencarr);
        Bitmap b1 = bitmapd.getBitmap();
        greenMarker = Bitmap.createScaledBitmap(b1, width, height, false);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            Log.e("TAG","Maps initializer"+e);
        }
        showLocation();

    }

//This method is common for both car location and booking availability. based on condition we are executing business logic.
    public void showLocation(){

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // Add a marker in Sydney, Australia, and move the camera.
                if(entryFrom.equalsIgnoreCase("bookingtaxi")) {
                    if (bookingAvailabilityData.body().getData().size() > 0) {
                        mapload = true;
                        for (int i = 0; i < bookingAvailabilityData.body().getData().size(); i++) {
                            if (bookingAvailabilityData.body().getData().get(i).getAvailableCars() > 0) {
                                markerOptions = new MarkerOptions().position(new LatLng(bookingAvailabilityData.body().getData().get(i).getLocation().get(0), bookingAvailabilityData.body().getData().get(i).getLocation().get(1))).title(bookingAvailabilityData.body().getData().get(i).getDropoffLocations().size() + " Drop off locations available");
                                marker = mMap.addMarker(markerOptions);
                                marker.setTag(bookingAvailabilityData.body().getData().get(i));
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(greenMarker));
                            }
                        }
                    } else {
                        mapload = false;
                    }
                } else if(entryFrom.equalsIgnoreCase("carlocations")){
                    Log.d("TAG","invoke location tracking map");
                    if (locationTrackingData.body().getData().size() > 0) {
                        mapload = true;
                        for (int i = 0; i < locationTrackingData.body().getData().size(); i++) {
                            if (locationTrackingData.body().getData().get(i).getIsOnTrip()) {
                                markerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(locationTrackingData.body().getData().get(i).getLatitude()), Double.parseDouble(locationTrackingData.body().getData().get(i).getLongitude())));//.title(bookingAvailabilityData.body().getData().get(i).getDropoffLocations().size() + " Drop off locations available");
                                marker = mMap.addMarker(markerOptions);
                                marker.setTag(locationTrackingData.body().getData().get(i));
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(greenMarker));
                            } else {
                                markerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(locationTrackingData.body().getData().get(i).getLatitude()), Double.parseDouble(locationTrackingData.body().getData().get(i).getLongitude())));//.title(bookingAvailabilityData.body().getData().get(i).getDropoffLocations().size() + " Drop off locations available");
                                marker = mMap.addMarker(markerOptions);
                                marker.setTag(locationTrackingData.body().getData().get(i));
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(redMarker));
                            }
                        }
                    } else {
                        mapload = false;
                    }

                }


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
                //Marker click currently enabled only for booking car availability functionality.
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(entryFrom.equalsIgnoreCase("bookingtaxi")) {
                            Datum indexItem = (Datum) (marker.getTag());
                            Log.d("TAG", "Clicked drop location::" + indexItem.getDropoffLocations().size());
                            if (marker.isInfoWindowShown()) {
                                marker.hideInfoWindow();
                            } else {
                                marker.showInfoWindow();
                            }
                            return false;
                        }
                        return false;
                    }
                });


            }
        });


    }

    // Location listner
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


    //Runtime permission request from Marshmallow version
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

// show the alert if user doesn't give the pernmission
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
