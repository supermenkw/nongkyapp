package com.codekinian.nongkyapp.View.Favorite;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codekinian.nongkyapp.Adapter.FavoriteAdapter;
import com.codekinian.nongkyapp.Base.MvpFragment;
import com.codekinian.nongkyapp.Model.FavoriteModel;
import com.codekinian.nongkyapp.R;
import com.codekinian.nongkyapp.Utils.GPSTracker;
import com.codekinian.nongkyapp.Utils.RecyclerItemClickListener;
import com.codekinian.nongkyapp.Utils.VerticalLineDecoration;
import com.google.android.gms.maps.model.LatLng;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.codekinian.nongkyapp.Helpers.DatabaseContract.CONTENT_URI;

public class FavoriteFragment extends MvpFragment<FavoritePresenter> implements FavoriteView {
    private FavoriteModel list;
    protected Unbinder unbinder;
    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;
    private String page = null;
    private String loc;
    private Cursor mCursor;
    private FavoriteAdapter adapter;
    private Context context;
    LinearLayoutManager manager;
    LatLng latLng = new LatLng(Double.parseDouble("-6.917464"), Double.parseDouble("107.619123"));

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.progressTwo)
    ProgressBar progressTwo;

    /*@BindView(R.id.slider)
    BannerSlider sliderLayout;*/


    @Override
    protected FavoritePresenter createPresenter() {
        return new FavoritePresenter(this);
    }

    public FavoriteFragment() {
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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        context = view.getContext();
        this.unbinder = ButterKnife.bind(this, view);

        manager = new LinearLayoutManager(getActivity());
        presenter = new FavoritePresenter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new VerticalLineDecoration(2));
        recyclerView.addOnItemTouchListener(selectItemOnRecyclerView());

        getCurrentLocation();
        Objects.requireNonNull(getActivity()).setTitle("Home");

        if (savedInstanceState != null) {
            latLng = new LatLng(savedInstanceState.getDouble("latitude"),
                    savedInstanceState.getDouble("longitude"));
            Objects.requireNonNull(getActivity()).setTitle(savedInstanceState.getString("title"));
        }

        loc = latLng.latitude + "," + latLng.longitude;

        adapter = new FavoriteAdapter(mCursor, R.layout.place_item_list,
                getContext(), latLng);
        recyclerView.setAdapter(adapter);

        new loadDataAsync().execute();

        return view;
    }

    private RecyclerItemClickListener selectItemOnRecyclerView() {
        return new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                list = getItem(position);
                presenter.getItem(list.getPlaceId(), activity);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                list = getItem(position);
                presenter.getItem(list.getPlaceId(), activity);
            }
        });
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        progressTwo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        progressTwo.setVisibility(View.GONE);
    }


    @Override
    public void moveToDetail(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.unbinder != null) {
            this.unbinder.unbind();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadDataAsync().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            //showLoading();
            return context.getContentResolver().query(
                    CONTENT_URI,null,null,null,null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            hideLoading();
            mCursor = cursor;
            adapter.replaceAll(mCursor);
        }
    }

    private FavoriteModel getItem(int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new FavoriteModel(mCursor);
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
}