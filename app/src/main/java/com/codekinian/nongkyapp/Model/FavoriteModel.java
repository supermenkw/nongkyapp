package com.codekinian.nongkyapp.Model;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import static android.provider.BaseColumns._ID;
import static com.codekinian.nongkyapp.Helpers.DatabaseContract.getColumnDouble;
import static com.codekinian.nongkyapp.Helpers.DatabaseContract.getColumnInt;
import static com.codekinian.nongkyapp.Helpers.DatabaseContract.getColumnString;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_LATITUDE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_LONGITUDE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PHOTO;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PLACE_ID;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_RATING;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_TITLE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_VICINITY;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FavoriteModel {
    @SerializedName("_id")
    private int mId;
    @SerializedName("place_id")
    private String mName;
    @SerializedName("place_id")
    private String mPlaceId;
    @SerializedName("rating")
    private Double mRating;
    @SerializedName("latitude")
    private Double mLatitude;
    @SerializedName("longitude")
    private Double mLongitude;
    @SerializedName("vicinity")
    private String mVicinity;
    @SerializedName("photo_reference")
    private String mPhotoReference;

    public FavoriteModel(Cursor list) {
        this.mId = getColumnInt(list, _ID);
        this.mPlaceId = getColumnString(list, COLUMN_PLACE_ID);
        this.mName = getColumnString(list, COLUMN_TITLE);
        this.mVicinity = getColumnString(list, COLUMN_VICINITY);
        this.mRating = getColumnDouble(list, COLUMN_RATING);
        this.mLatitude = getColumnDouble(list, COLUMN_LATITUDE);
        this.mLongitude = getColumnDouble(list, COLUMN_LONGITUDE);
        this.mPhotoReference = getColumnString(list, COLUMN_PHOTO);
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(String mPlaceId) {
        this.mPlaceId = mPlaceId;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double mRating) {
        this.mRating = mRating;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public void setVicinity(String mVicinity) {
        this.mVicinity = mVicinity;
    }

    public String getPhotoReference() {
        return mPhotoReference;
    }

    public void setPhotoReference(String mPhotoReference) {
        this.mPhotoReference = mPhotoReference;
    }
}
