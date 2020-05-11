package com.codekinian.nongkyapp.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.codekinian.nongkyapp.Model.PlaceModel;
import com.codekinian.nongkyapp.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.SphericalUtil;
import com.squareup.picasso.Callback;

import java.text.DecimalFormat;

public class CustomInfoWindows implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    private boolean first = false;
    private Resources resources;

    public CustomInfoWindows(Activity ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getInfoContents(Marker marker) {
        View view = (context).getLayoutInflater()
                .inflate(R.layout.custom_infowindows, null);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_error_white_24dp);
        requestOptions.error(R.drawable.ic_info_white_24dp);

        TextView title = view.findViewById(R.id.title);
        TextView distance = view.findViewById(R.id.distance);
//        ImageView cover = view.findViewById(R.id.cover);

        PlaceModel placeModel = (PlaceModel) marker.getTag();
        Double dis = SphericalUtil.computeDistanceBetween(placeModel.getFrom(), placeModel.getTo())/1000;
        DecimalFormat df = new DecimalFormat("0.#");

        title.setText(placeModel.getPlace());
        distance.setText(df.format(dis)+" Km");
        /*Picasso.with(context).load(placeModel.getImage())
                .into(cover, new MarkerCallback(marker));*/


        return view;
    }

    static class MarkerCallback implements Callback
    {
        Marker marker = null;

        MarkerCallback(Marker marker)
        {
            this.marker = marker;
        }

        @Override
        public void onError() {}

        @Override
        public void onSuccess()
        {
            if (marker == null)
            {
                return;
            }

            if (!marker.isInfoWindowShown())
            {
                return;
            }

            // If Info Window is showing, then refresh it's contents:

            marker.hideInfoWindow(); // Calling only showInfoWindow() throws an error
            marker.showInfoWindow();
        }
    }
}
