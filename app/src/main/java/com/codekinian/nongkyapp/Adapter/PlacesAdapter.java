package com.codekinian.nongkyapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codekinian.nongkyapp.Helpers.FavoriteHelper;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceResult;
import com.codekinian.nongkyapp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;


import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codekinian.nongkyapp.Helpers.DatabaseContract.CONTENT_URI;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>  {

    private List<GPlaceResult> places;
    private int rowLayout;
    private Context context;
    private LatLng latLng;
    private Boolean isFav = false;
//    ImageView iv_fav;

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.rating)
        TextView rating;

        @BindView(R.id.distance)
        TextView distance;

        @BindView(R.id.cover)
        ImageView cover;

        @BindView(R.id.ratingBarID)
        RatingBar iv_vote;

        @BindView(R.id.btnFav)
        ImageView iv_fav;

        PlaceViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public PlacesAdapter(List<GPlaceResult> places, int rowLayout, Context context, LatLng location) {
        this.places = places;
        this.rowLayout = rowLayout;
        this.context = context;
        this.latLng = location;
    }

    @Override
    public PlacesAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
//        iv_fav = (ImageView) view.findViewById(R.id.btnFav);
        return new PlaceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(PlaceViewHolder holder, final int position) {
        getDataFav(places.get(position).getPlaceId());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.ic_info_white_24dp);

        LatLng from = latLng;
        LatLng to = new LatLng(places.get(position).getGeometry().getLocation().getLat(),
                places.get(position).getGeometry().getLocation().getLng());
        Double distance = SphericalUtil.computeDistanceBetween(from, to);
        DecimalFormat df = new DecimalFormat("0.#");

        double userRating = 0.0;
        if(places.get(position).getRating() != null){
           userRating = places.get(position).getRating();
        }

        holder.title.setText(places.get(position).getName());
        holder.address.setText(places.get(position).getVicinity());
        holder.distance.setText(String.valueOf(df.format(distance/1000))+" Km");
        holder.rating.setText(String.valueOf(userRating));
        holder.iv_vote.setRating((float) userRating);

        String url = "http://maps.google.com/maps/api/staticmap?center=" + places.get(position).getGeometry().getLocation().getLat() + ","
                + places.get(position).getGeometry().getLocation().getLng() + "&zoom=20&size=600x400&&markers=color:red%7C"+ places.get(position).getGeometry().getLocation().getLat() + ","
                + places.get(position).getGeometry().getLocation().getLng();

        String img_url = places.get(position).getPhotos() != null ?
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+
                        places.get(position).getPhotos().get(0).getPhotoReference()+"&key="
                        + context.getResources().getString(R.string.google_maps_api_key) :
                url;

        Glide.with(context).load(img_url)
                .apply(requestOptions)
                .into(holder.cover);

        if (isFav) holder.iv_fav.setImageResource(R.drawable.ic_bookmark_black_24dp);
        else holder.iv_fav.setImageResource(R.drawable.ic_bookmark_border_black_24dp);

        isFav = false;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    private void getDataFav(String id){
        FavoriteHelper favoriteHelper = new FavoriteHelper(context);
        favoriteHelper.open();

        Cursor cursor = context.getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + id),null,null,null,null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) isFav = true;
            cursor.close();
        }
    }

}
