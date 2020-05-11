package com.codekinian.nongkyapp.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;

public class ClusterModel implements com.google.maps.android.clustering.ClusterItem {
    private final LatLng location;
    private String title;
    private String snippet;
    private LatLng latLng;
    private String cover;

    public ClusterModel(@NonNull LatLng location, String title, LatLng latLng, String cover) {
        this.location = location;
        this.title = title;
        this.latLng = latLng;
        this.cover = cover;
    }

    @Override
    public LatLng getPosition() {
        return location;
    }

    public String getSnippet(){
        Double dis = SphericalUtil.computeDistanceBetween(latLng, location);
        DecimalFormat df = new DecimalFormat("0.#");
        return String.valueOf(df.format(dis/1000))+" Km";
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }
}