package com.codekinian.nongkyapp.Model;

import com.google.android.gms.maps.model.LatLng;

public class PlaceModel {
    private String image;
    private String place;
    private double latitude;
    private double longitude;
    private LatLng from;
    private LatLng to;
    private String vicinity;
    private double rating;
    private String placeId;
    private String mPhotoReference;

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getmPhotoReference() {
        return mPhotoReference;
    }

    public void setmPhotoReference(String mPhotoReference) {
        this.mPhotoReference = mPhotoReference;
    }

    public LatLng getFrom() {
        return from;
    }

    public void setFrom(LatLng from) {
        this.from = from;
    }

    public LatLng getTo() {
        return to;
    }

    public void setTo(LatLng to) {
        this.to = to;
    }

    private String distance;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String  getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
