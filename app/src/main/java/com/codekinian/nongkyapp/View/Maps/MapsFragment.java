package com.codekinian.nongkyapp.View.Maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codekinian.nongkyapp.Base.MvpFragment;
import com.codekinian.nongkyapp.BuildConfig;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceModel;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceResult;
import com.codekinian.nongkyapp.Model.PlaceModel;
import com.codekinian.nongkyapp.R;
import com.codekinian.nongkyapp.Utils.CustomClickMarker;
import com.codekinian.nongkyapp.Utils.CustomInfoWindows;
import com.codekinian.nongkyapp.Utils.GPSTracker;
import com.codekinian.nongkyapp.View.ErrorFragment;
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
import com.google.maps.android.SphericalUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.angmarch.views.NiceSpinner;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class MapsFragment extends MvpFragment<MapsPresenter> implements MapsView,OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private List<GPlaceResult> list;
    private UiSettings mUiSettings;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    GoogleApiClient mGoogleApiClient;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    LatLng latLng = new LatLng(Double.parseDouble("0.0"), Double.parseDouble("0.0"));
    private String PLACE_TYPE = "park";
    private BottomSheetBehavior mBottomSheetBehavior;
    private Marker marker;
    private Marker previousMarker = null;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.detail_rate)
    SimpleRatingBar ratingBar;

    @BindView(R.id.name_place)
    TextView namePlace;

    @BindView(R.id.address_detail)
    TextView addressDetail;

    @BindView(R.id.distance_detail)
    TextView distanceDetail;

    View bottomSheetBehavior;
    private NiceSpinner niceSpinner;

    @Override
    protected MapsPresenter createPresenter() {
        return new MapsPresenter(this);
    }

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("latitude", latLng.latitude);
        outState.putDouble("longitude", latLng.longitude);
        outState.putString("title", "Maps");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        getCurrentLocation();

        if(latLng.latitude == 0.0 && latLng.longitude == 0.0){
            latLng = new LatLng(Double.parseDouble("-6.88649"), Double.parseDouble("107.61624"));
        }
        bottomSheetBehavior = view.findViewById(R.id.bottom_sheet1);
        niceSpinner = (NiceSpinner) view.findViewById(R.id.nice_spinner);
        Objects.requireNonNull(getActivity()).setTitle("Maps");

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }

        if (savedInstanceState != null) {
            latLng = new LatLng(savedInstanceState.getDouble("latitude"),
                    savedInstanceState.getDouble("longitude"));
            Objects.requireNonNull(getActivity()).setTitle(savedInstanceState.getString("title"));
        }

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetBehavior);
        mBottomSheetBehavior.setPeekHeight(120);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        List<String> datasets = new LinkedList<>(Arrays.asList("Taman", "Museum", "Kafe", "Restoran"));
        niceSpinner.attachDataSource(datasets);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                previousMarker = null;
                switch (niceSpinner.getSelectedIndex()){
                    case 0:
                        PLACE_TYPE = "park";
                        break;
                    case 1:
                        PLACE_TYPE = "museum";
                        break;
                    case 2:
                        PLACE_TYPE = "cafe";
                        break;
                    case 3:
                        PLACE_TYPE = "restaurant";
                        break;
                    default:
                        PLACE_TYPE = "park";
                        break;
                }

                String loc = latLng.latitude+","+latLng.longitude;
                presenter.loadData(loc, getString(R.string.google_maps_api_key), PLACE_TYPE, 3000 );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        return view;
    }

    @Override
    public void initMarker(List<GPlaceResult> result) {
        mMap.clear();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()).getApplicationContext(),
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

/*        ClusterManager<ClusterModel> clusterManager = new ClusterManager<>(getActivity().getApplicationContext(), mMap);
        final CustomClusterRenderer renderer = new CustomClusterRenderer(getActivity().getApplicationContext(),
                mMap, clusterManager);*/

//        clusterManager.setRenderer(renderer);

        /*mMap.setOnCameraIdleListener(clusterManager);*/


        /*mMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
        mMap.setOnMarkerClickListener(clusterManager);*/
//        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(customInfoWindow);

        for (int i=0; i<result.size(); i++){
            CustomInfoWindows customInfoWindow = new CustomInfoWindows(getActivity());
            mMap.setInfoWindowAdapter(customInfoWindow);
            //set latlng nya

            LatLng location = new LatLng(result.get(i).getGeometry().getLocation().getLat(),
                    result.get(i).getGeometry().getLocation().getLng());

            Double distance = SphericalUtil.computeDistanceBetween(latLng, location);
            DecimalFormat df = new DecimalFormat("0.#");

            double userRating = 0.0;
            if(result.get(i).getRating() != null){
                userRating = result.get(i).getRating();
            }

            String url = "http://maps.google.com/maps/api/staticmap?center=" + result.get(i).getGeometry().getLocation().getLat() + ","
                    + result.get(i).getGeometry().getLocation().getLng() + "&zoom=15&size=600x400&&markers=color:red%7C"
                    + result.get(i).getGeometry().getLocation().getLat() + ","
                    + result.get(i).getGeometry().getLocation().getLng();

            String img_url = result.get(i).getPhotos() != null ?
                    "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+
                            result.get(i).getPhotos().get(0).getPhotoReference()+"&key="
                            + getResources().getString(R.string.google_maps_api_key) : url;

            PlaceModel placeModel = new PlaceModel();
            placeModel.setPlace(result.get(i).getName());
            placeModel.setFrom(latLng);
            placeModel.setTo(location);
            placeModel.setLatitude(result.get(i).getGeometry().getLocation().getLat());
            placeModel.setLongitude(result.get(i).getGeometry().getLocation().getLat());
            placeModel.setPlaceId(result.get(i).getPlaceId());
            placeModel.setDistance(String.valueOf(df.format(distance/1000))+" Km");
            placeModel.setVicinity(result.get(i).getVicinity());
            placeModel.setRating(userRating);
            placeModel.setmPhotoReference(img_url);

            /*ClusterModel clusterModel = new ClusterModel(location, result.get(i).getNamaTempat(), latLng, BuildConfig.BASE_URL_IMG+result.get(i).getCover());
            clusterManager.addItem(clusterModel);*/

            marker = mMap.addMarker(new MarkerOptions().position(location).title(result.get(i).getName()));
            marker.setTag(placeModel);
            mMap.setOnInfoWindowClickListener(new CustomClickMarker(getContext()));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (previousMarker != null) {
                        previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    previousMarker = marker;
                    return false;
                }
            });

        }
//        clusterManager.cluster();
    }



    @Override
    public void getDataSuccess(GPlaceModel model) {
        this.list = model.getResults();
        initMarker(list);

    }

    @Override
    public void getDataFail(String message) {
        Fragment fragment = null;
        Class fragmentClass = ErrorFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    @Override
    public void moveToDirect(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(false);
        mUiSettings.setMapToolbarEnabled(false);
        String loc = latLng.latitude+","+latLng.longitude;
        presenter.loadData(loc, getString(R.string.google_maps_api_key), PLACE_TYPE, 3000 );
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        @SuppressLint("RestrictedApi") LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()).getApplicationContext(),
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
        GPSTracker gpsTracker = new GPSTracker(Objects.requireNonNull(getActivity()).getApplicationContext());

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            latLng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()).getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
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
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Izin ditolak.
                    FancyToast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Akses Ditolak", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true)
                            .show();
                }
                return;
            }
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(Objects.requireNonNull(getActivity()).getApplicationContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }
}
