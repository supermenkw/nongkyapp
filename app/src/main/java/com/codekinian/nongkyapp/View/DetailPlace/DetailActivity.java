package com.codekinian.nongkyapp.View.DetailPlace;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codekinian.nongkyapp.Adapter.ReviewsAdapter;
import com.codekinian.nongkyapp.Base.MvpActivity;
import com.codekinian.nongkyapp.Helpers.FavoriteHelper;
import com.codekinian.nongkyapp.Model.FavoriteModel;
import com.codekinian.nongkyapp.Model.GoogleDetailModel.GDetailModel;
import com.codekinian.nongkyapp.Model.GoogleDetailModel.GDetailResult;
import com.codekinian.nongkyapp.Model.GoogleDetailModel.Review;
import com.codekinian.nongkyapp.R;
import com.codekinian.nongkyapp.Utils.VerticalLineDecoration;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.codekinian.nongkyapp.Helpers.DatabaseContract.CONTENT_URI;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_LATITUDE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_LONGITUDE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PHOTO;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PLACE_ID;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_RATING;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_TITLE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_VICINITY;

public class DetailActivity extends MvpActivity<DetailPresenter> implements DetailView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.opening)
    TextView opening;

    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.maps_static)
    ImageView maps_static;

    @BindView(R.id.image_favorite)
    ImageView iv_fav;

    @BindView(R.id.cover)
    SliderLayout cover;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.descriptionLayout)
    CardView descriptionLayout;

    @BindView(R.id.direction_place)
    CardView directionPlace;

    @BindView(R.id.share_place)
    CardView sharePlace;

    @BindView(R.id.favorite_place)
    CardView favoritePlace;

    @BindView(R.id.reviews_list)
    RecyclerView reviewsList;

    private String placeId;
    private GDetailResult list;
    private FavoriteModel listFav;
    private List<Review> listReviews;
    Calendar calendar = Calendar.getInstance();
    private Boolean isFav = false;
    private LinearLayout reviewsDetail;


    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(toolbar);

        reviewsDetail = (LinearLayout) findViewById(R.id.desc_reviews);
        reviewsDetail.setVisibility(View.GONE);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.SRC_ATOP);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(upArrow);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));

        reviewsList.setLayoutManager(new LinearLayoutManager(this));
        reviewsList.addItemDecoration(new VerticalLineDecoration(2));


        directionPlace.setOnClickListener(view -> presenter.getItem(list, activity));
        sharePlace.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            String shareBody = "[NONGKY] \nAyo kunjungi " + list.getName()+". Aku tunggu kamu disana ya! "+
                    "http://mulkyatolul2.web.id/index.php?id="+list.getPlaceId();
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "nongkyapp");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share location to your friends"));
        });

        if (savedInstanceState != null) {
            placeId = savedInstanceState.getString("placeId");
        }

//        placeId = getIntent().getStringExtra("id");

        handleIntent(getIntent());

        favoritePlace.setOnClickListener(view -> {
            if (isFav) DeleteFavorite();
            else SaveFavorite();
            isFav = !isFav;

            SetFavorite();
        });
//        setBanner();

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();

        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            placeId = appLinkData.getLastPathSegment();
        } else if(getIntent().getExtras() != null) {
            placeId = getIntent().getStringExtra("id");
        } else {
            placeId = null;
        }

        getDataFav(placeId);
        presenter.loadData(placeId, getString(R.string.google_maps_api_key), "in-ID");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("placeId", placeId);
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

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        descriptionLayout.setVisibility(View.GONE);
//        placeTips.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        descriptionLayout.setVisibility(View.VISIBLE);
//        placeTips.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getDataSuccess(GDetailModel item) {
        this.list = item.getResult();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.ic_info_white_24dp);
        String open = "Buka";
        int day = DayNumber(calendar.get(Calendar.DAY_OF_WEEK));

        if(list.getOpeningHours() != null){
            if(list.getOpeningHours().getWeekdayText() != null){
                open = list.getOpeningHours().getWeekdayText().get(day);
            }else{
                open = list.getOpeningHours().getOpenNow() ? "Sedang Buka" : "Sedang Tutup";
            }
        }

        String url = "http://maps.google.com/maps/api/staticmap?center=" + list.getGeometry().getLocation().getLat() + ","
                + list.getGeometry().getLocation().getLng() + "&zoom=18&size=600x400&&markers=color:red%7C"+ list.getGeometry().getLocation().getLat() + ","
                + list.getGeometry().getLocation().getLng();
        Glide.with(this).load(url)
                .apply(requestOptions)
                .into(maps_static);
        opening.setText(open);
        address.setText(list.getFormattedAddress() == null ? "-" : list.getFormattedAddress());
        phone.setText(list.getFormattedPhoneNumber() == null ? "-" : list.getFormattedPhoneNumber());
        collapsingToolbarLayout.setTitle(list.getName());

        if(list.getReviews() != null) {
            reviewsDetail.setVisibility(View.VISIBLE);
            this.listReviews = list.getReviews();
            reviewsList.setAdapter(new ReviewsAdapter(listReviews, R.layout.reviews_item_list,
                    this));
        }

        setBanner();
    }

    @Override
    public void getDataFail(String message) {
        FancyToast.makeText(this,"Please load again",
                FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
    }

    @Override
    public void refreshData() {
        presenter.loadData(placeId, getString(R.string.google_maps_api_key), "in-ID");
    }

    @Override
    public void moveToDirect(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        refreshData();
    }

    private void setBanner() {

        ArrayList<HashMap<String, String>> arraylist = new ArrayList<>();
        if(list.getPhotos() != null){
            int imgPart = list.getPhotos().size() > 5 ? 5 : list.getPhotos().size();
            for (int i = 0; i < list.getPhotos().size(); i++){
                HashMap<String,String> url_maps = new HashMap<>();
                url_maps.put(list.getName(), "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+
                        list.getPhotos().get(i).getPhotoReference()+"&key="+getString(R.string.google_maps_api_key));
                arraylist.add(url_maps);
                for(String name : url_maps.keySet()){
                    DefaultSliderView textSliderView = new DefaultSliderView(this);
                    textSliderView
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    cover.addSlider(textSliderView);
                    cover.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    cover.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    cover.setDuration(4000);
                }
            }
        }else{
            HashMap<String,String> url_maps = new HashMap<>();
            String url = "http://maps.google.com/maps/api/staticmap?center=" + list.getGeometry().getLocation().getLat() + ","
                    + list.getGeometry().getLocation().getLng() + "&zoom=15&size=600x400&&markers=color:red%7C"+ list.getGeometry().getLocation().getLat() + ","
                    + list.getGeometry().getLocation().getLng();
            url_maps.put(list.getName(), url);
            arraylist.add(url_maps);
            for(String name : url_maps.keySet()){
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                textSliderView
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                cover.addSlider(textSliderView);
                cover.stopAutoCycle();
            }
        }



    }

    private int DayNumber(int day){

        switch (day) {
            case Calendar.SUNDAY:
                return 6;

            case Calendar.MONDAY:
                return 0;

            case Calendar.TUESDAY:
                return 1;

            case Calendar.WEDNESDAY:
                return 2;

            case Calendar.THURSDAY:
                return 3;

            case Calendar.FRIDAY:
                return 4;

            case Calendar.SATURDAY:
                return 4;
        }

        return 0;
    }

    private void getDataFav(String id){
        FavoriteHelper favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + id),null,null,null,null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) isFav = true;
            cursor.close();
        }
        SetFavorite();
    }

    private void SetFavorite() {
        if (isFav) iv_fav.setImageResource(R.drawable.ic_bookmark_black_24dp);
        else iv_fav.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
    }

    private void SaveFavorite() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PLACE_ID, list.getPlaceId());
        contentValues.put(COLUMN_TITLE, list.getName());
        contentValues.put(COLUMN_LATITUDE, list.getGeometry().getLocation().getLat());
        contentValues.put(COLUMN_LONGITUDE, list.getGeometry().getLocation().getLng());
        contentValues.put(COLUMN_RATING, list.getRating());
        contentValues.put(COLUMN_VICINITY, list.getVicinity());
        String photo = null;
        if(list.getPhotos() != null){
            photo = list.getPhotos().get(0).getPhotoReference();
        }else{
            photo = "default.jpg";
        }
        contentValues.put(COLUMN_PHOTO, photo);

        getContentResolver().insert(CONTENT_URI, contentValues);
        FancyToast.makeText(this, "Menambahkan ke Favorite", FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS, true)
                .show();
    }

    private void DeleteFavorite() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + list.getPlaceId()),null,null
        );
        FancyToast.makeText(this, "Menghapus dari Favorite", FancyToast.LENGTH_SHORT,
                FancyToast.WARNING, true)
                .show();
    }
}