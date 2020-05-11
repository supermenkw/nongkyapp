package com.codekinian.nongkyapp.View.Maps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.codekinian.nongkyapp.Base.MvpActivity;
import com.codekinian.nongkyapp.BuildConfig;
import com.codekinian.nongkyapp.Model.GoogleDetailModel.GDetailModel;
import com.codekinian.nongkyapp.Model.GoogleDetailModel.GDetailResult;
import com.codekinian.nongkyapp.Model.PlaceModel;
import com.codekinian.nongkyapp.R;
import com.codekinian.nongkyapp.Utils.GPSTracker;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirectMapsActivity extends MvpActivity<DirectMapsPresenter> implements DirectMapsView,OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, RoutingListener {

    private GDetailResult list;
    private UiSettings mUiSettings;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    GoogleApiClient mGoogleApiClient;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    LatLng latLng = new LatLng(-0.0, 0.0);
    private Context mContext = DirectMapsActivity.this;
    private String placeId;
    private LatLng origin;

    @Override
    protected DirectMapsPresenter createPresenter() {
        return new DirectMapsPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.SRC_ATOP);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(upArrow);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Direct Maps");
        getSupportActionBar().setElevation(3);

        polylines = new ArrayList<>();

        if (savedInstanceState != null) {
            placeId = savedInstanceState.getString("placeId");
            origin = new LatLng(savedInstanceState.getDouble("latitude"),
                    savedInstanceState.getDouble("longitude"));
        }
        placeId = getIntent().getStringExtra("id");

        getCurrentLocation();
        /*if(latLng.latitude == 0.0 && latLng.longitude == 0.0){
            origin = new LatLng(Double.parseDouble("-6.88649"), Double.parseDouble("107.61624"));
        }*/

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map, mapFragment);
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("placeId", placeId);
        outState.putDouble("latitude", origin.latitude);
        outState.putDouble("longitude", origin.latitude);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void initMarker(LatLng destination) {
        mMap.clear();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.addMarker(new MarkerOptions().position(destination)
                .title(list.getName())
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));


        getRouteToMarker(destination);
    }

    private void getRouteToMarker(LatLng destination) {
        if (destination != null && origin != null){
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(false)
                    .waypoints(origin, destination)
                    .key(getString(R.string.key_direct))
                    .build();
            routing.execute();
        }
    }

    @Override
    public void getDataSuccess(GDetailModel model) {
        this.list = model.getResult();
        LatLng destination = new LatLng(list.getGeometry().getLocation().getLat(),
                list.getGeometry().getLocation().getLng());
        initMarker(destination);
    }

    @Override
    public void getDataFail(String message) {

    }

    @Override
    public void showLoading() {

    }


    @Override
    public void hideLoading() {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        @SuppressLint("RestrictedApi") LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        origin = new LatLng(location.getLatitude(), location.getLongitude());

        PlaceModel placeModel = new PlaceModel();
        placeModel.setPlace("Lokasi Anda");
        placeModel.setImage(BuildConfig.BASE_URL_IMG+"default.jpg");
        placeModel.setLatitude(latLng.latitude);
        placeModel.setLongitude(latLng.longitude);
        placeModel.setFrom(latLng);
        placeModel.setTo(latLng);

        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                .title("Lokasi Anda")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        marker.setTag(placeModel);

        //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(12).build();

        CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        //menghentikan pembaruan lokasi
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public void getCurrentLocation(){
        GPSTracker gpsTracker = new GPSTracker(mContext);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            origin = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        }
        else
        {
            gpsTracker.showSettingsAlert();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Izin diberikan.
                    if (ContextCompat.checkSelfPermission(mContext,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Izin ditolak.
                    FancyToast.makeText(mContext,
                            "Akses Ditolak", FancyToast.LENGTH_SHORT, FancyToast.WARNING,
                            true)
                            .show();
                }
                return;
            }
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(false);
        mUiSettings.setMapToolbarEnabled(false);
        presenter.loadData(placeId, getString(R.string.google_maps_api_key), "in-ID");
    }

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorDirection};
    @Override
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            FancyToast.makeText(this, "Error: " + e.getMessage(), FancyToast.LENGTH_LONG).show();
        }else {
            FancyToast.makeText(this, "Something went wrong, Try again", FancyToast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRoutingStart() {
    }
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }


        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(12 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);
        }

    }
    @Override
    public void onRoutingCancelled() {
    }
    private void erasePolylines(){
        for(Polyline line : polylines){
            line.remove();
        }
        polylines.clear();
    }
}
