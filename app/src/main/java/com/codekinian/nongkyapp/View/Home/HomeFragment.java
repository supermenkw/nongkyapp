package com.codekinian.nongkyapp.View.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.codekinian.nongkyapp.Adapter.PlacesAdapter;
import com.codekinian.nongkyapp.Base.MvpFragment;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceModel;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceResult;
import com.codekinian.nongkyapp.R;
import com.codekinian.nongkyapp.Utils.GPSTracker;
import com.codekinian.nongkyapp.Utils.RecyclerItemClickListener;
import com.codekinian.nongkyapp.Utils.VerticalLineDecoration;
import com.codekinian.nongkyapp.View.ErrorFragment;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class HomeFragment extends MvpFragment<HomePresenter> implements HomeView, LocationListener {
    private List<GPlaceResult> list;
    protected Unbinder unbinder;
    private boolean itShouldLoadMore = false;
    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;
    //    private static final String API_KEY = "AIzaSyCsGTJiOqz5jRLhqRewAUDKPqrKei-uQDQ";
    private String page = null;
    private String loc;
    private String PLACE_TYPE = "park";
    int currentItems, totalItems, scrollOutItems;
    LinearLayoutManager manager;
    LatLng latLng = new LatLng(Double.parseDouble("0.0"), Double.parseDouble("0.0"));


    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.progressTwo)
    ProgressBar progressTwo;

    @BindView(R.id.activity_main)
    SwipeRefreshLayout swipeRefreshLayout;

    /*@BindView(R.id.slider)
    BannerSlider sliderLayout;*/


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(String type) {
        // Required empty public constructor
        this.PLACE_TYPE = type;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("latitude", latLng.latitude);
        outState.putDouble("longitude", latLng.longitude);
        outState.putString("title", "Home");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.unbinder = ButterKnife.bind(this, view);

        manager = new LinearLayoutManager(getActivity());
        presenter = new HomePresenter(this);

        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(() -> refresh());

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new VerticalLineDecoration(2));
        recyclerView.addOnItemTouchListener(selectItemOnRecyclerView());

        getCurrentLocation();
        if(latLng.latitude == 0.0 && latLng.longitude == 0.0){
            latLng = new LatLng(Double.parseDouble("-6.88649"), Double.parseDouble("107.61624"));
        }
        recyclerView.addOnScrollListener(onScrollLoad());
        Objects.requireNonNull(getActivity()).setTitle("Home");

        if (savedInstanceState != null) {
            latLng = new LatLng(savedInstanceState.getDouble("latitude"),
                    savedInstanceState.getDouble("longitude"));
            Objects.requireNonNull(getActivity()).setTitle(savedInstanceState.getString("title"));
        }

        loc = latLng.latitude + "," + latLng.longitude;

        presenter.loadData("", loc, getString(R.string.google_maps_api_key), PLACE_TYPE, 3000);

        return view;
    }

    private RecyclerView.OnScrollListener onScrollLoad() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    itShouldLoadMore = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                swipeRefreshLayout.setEnabled(manager.findFirstCompletelyVisibleItemPosition() == 0);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (itShouldLoadMore && (currentItems + scrollOutItems == totalItems) && page != null) {
                    itShouldLoadMore = false;
                    presenter.loadMore(page, loc, getString(R.string.google_maps_api_key), PLACE_TYPE, 3000);
                }
            }
        };
    }

    private RecyclerItemClickListener selectItemOnRecyclerView() {
        return new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.getItem(list.get(position), activity);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                presenter.getItem(list.get(position), activity);
            }
        });
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        progressTwo.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(manager.findFirstCompletelyVisibleItemPosition() == 0);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        progressTwo.setVisibility(View.GONE);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void getDataSuccess(GPlaceModel places) {
        this.list = places.getResults();
//        setBanner(this.list);
        if (places.getNextPageToken() != null) {
            page = places.getNextPageToken();
        } else {
            page = null;
        }

        recyclerView.setAdapter(new PlacesAdapter(list, R.layout.place_item_list,
                getContext(), latLng));
    }

    @Override
    public void getMoreData(GPlaceModel places) {
        list.addAll(places.getResults());
        if (places.getNextPageToken() != null) {
            page = places.getNextPageToken();
        } else {
            page = null;
        }
        PlacesAdapter placesAdapter = new PlacesAdapter(list, R.layout.place_item_list,
                getContext(), latLng);

        placesAdapter.notifyDataChanged();
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

//        FancyToast.makeText(getActivity().getApplicationContext(), message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
    }

    @Override
    public void moveToDetail(Intent intent) {
        startActivity(intent);
    }

    public void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.loadData("", loc, getString(R.string.google_maps_api_key), PLACE_TYPE, 3000);
    }

    /*private void getData(int page) {
        if (page != 0) {
            //this.page = page;
            presenter.loadData(page);
        }
    }*/

    /*private void setBanner(List<GPlaceResult> setList) {
        List<Banner> banners=new ArrayList<>();

        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            int n = rand.nextInt(setList.size()-1);
            if(setList.get(n).getPhotos() != null){
                banners.add(new RemoteBanner("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+
                        setList.get(i).getPhotos().get(0).getPhotoReference()+"&key="+API_KEY));
            }
        }
        sliderLayout.setBanners(banners);
        sliderLayout.setInterval(4000);
        sliderLayout.setHideIndicators(true);
        sliderLayout.setOnBannerClickListener(position -> presenter.getItem(list.get(position), activity));
    }*/

    @Override
    public void onDestroyView() {
        if (this.unbinder != null) {
            this.unbinder.unbind();
        }

        super.onDestroyView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case LOCATION_REQUEST:
                if (canAccessLocation()) {
                    getCurrentLocation();
                } else {
                    FancyToast.makeText(getActivity(), "Akses Ditolak", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true)
                            .show();
                }
                break;
        }
    }

    private boolean hasPermission(String perm) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (PackageManager.PERMISSION_GRANTED == Objects.requireNonNull(getContext()).checkSelfPermission(perm));
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    public void getCurrentLocation() {
        GPSTracker gpsTracker = new GPSTracker(Objects.requireNonNull(getActivity()).getApplicationContext());

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            latLng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }
}