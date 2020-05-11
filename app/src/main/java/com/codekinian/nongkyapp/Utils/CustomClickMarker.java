package com.codekinian.nongkyapp.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codekinian.nongkyapp.Model.PlaceModel;
import com.codekinian.nongkyapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.shashank.sony.fancytoastlib.FancyToast;

import static com.codekinian.nongkyapp.Helpers.DatabaseContract.CONTENT_URI;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_LATITUDE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_LONGITUDE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PHOTO;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PLACE_ID;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_RATING;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_TITLE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_VICINITY;

@SuppressLint("ValidFragment")
public class CustomClickMarker implements GoogleMap.OnInfoWindowClickListener {

    private Context context;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView name, address, distance;
    private SimpleRatingBar ratingBar;
    private ImageView imageView;
    private ImageView coverView;
    private LinearLayout btnDirect, btnFav, btnShare;
    private Boolean isFav = false;


    @SuppressLint("ValidFragment")
    public CustomClickMarker(Context context) {
        this.context = context;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        PlaceModel placeModel = (PlaceModel) marker.getTag();
        View bottomSheetViewgroup = (View) ((Activity)context).findViewById(R.id.bottom_sheet1);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetViewgroup);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        name = (TextView) ((Activity)context).findViewById(R.id.name_place);
        address = (TextView) ((Activity)context).findViewById(R.id.address_detail);
        distance = (TextView) ((Activity)context).findViewById(R.id.distance_detail);
        ratingBar = (SimpleRatingBar) ((Activity)context).findViewById(R.id.detail_rate);

        btnDirect = (LinearLayout) ((Activity)context).findViewById(R.id.direct_detail);
        btnFav = (LinearLayout) ((Activity)context).findViewById(R.id.bookmark_detail);
        btnShare = (LinearLayout) ((Activity)context).findViewById(R.id.share_detail);
        imageView = (ImageView) ((Activity)context).findViewById(R.id.bookmark_icon);
        coverView = (ImageView) ((Activity)context).findViewById(R.id.image_place);

        btnShare.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            String shareBody = "[Hitzgenic] \nAyo kunjungi " + placeModel.getPlace()+". Aku tunggu kamu disana ya! " +
                    "http://hitzgenic.castcoding.web.id/"+placeModel.getPlaceId();
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Hitzgenic");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            context.startActivity(Intent.createChooser(sharingIntent, "Share location to your friends"));
        });
        btnFav.setOnClickListener(view -> {
            if (isFav) DeleteFavorite(placeModel);
            else SaveFavorite(placeModel);
            isFav = !isFav;

            SetFavorite();
        });

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.ic_info_white_24dp);

        name.setText(placeModel.getPlace());
        address.setText(placeModel.getVicinity());
        distance.setText(placeModel.getDistance());
        Glide.with(context).load(placeModel.getmPhotoReference())
                .apply(requestOptions)
                .into(coverView);
        ratingBar.setRating((float) placeModel.getRating());
    }


    private void SetFavorite() {
        if (isFav) imageView.setImageResource(R.drawable.ic_bookmark_black_24dp);
        else imageView.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
    }

    private void SaveFavorite(PlaceModel placeModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PLACE_ID, placeModel.getPlaceId());
        contentValues.put(COLUMN_TITLE, placeModel.getPlace());
        contentValues.put(COLUMN_LATITUDE, placeModel.getLatitude());
        contentValues.put(COLUMN_LONGITUDE, placeModel.getLongitude());
        contentValues.put(COLUMN_RATING, placeModel.getRating());
        contentValues.put(COLUMN_VICINITY, placeModel.getVicinity());
        contentValues.put(COLUMN_PHOTO, placeModel.getmPhotoReference());

        ((Activity)context).getContentResolver().insert(CONTENT_URI, contentValues);
        FancyToast.makeText(((Activity)context), "Menambahkan ke Favorite", FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS, true)
                .show();
    }

    private void DeleteFavorite(PlaceModel placeModel) {
        ((Activity)context).getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + placeModel.getPlaceId()),null,null
        );
        FancyToast.makeText(((Activity)context), "Menghapus dari Favorite", FancyToast.LENGTH_SHORT,
                FancyToast.WARNING, true)
                .show();
    }
}
