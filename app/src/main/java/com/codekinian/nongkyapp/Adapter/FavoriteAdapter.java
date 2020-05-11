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
import com.codekinian.nongkyapp.Model.FavoriteModel;
import com.codekinian.nongkyapp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codekinian.nongkyapp.Helpers.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>  {

    private Cursor places;
    private int rowLayout;
    private Context context;
    private LatLng latLng;

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
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


        private Boolean isFav = false;

        FavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(FavoriteModel item, LatLng latLng) {
            getDataFav(item.getPlaceId());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.placeholder);
            requestOptions.error(R.drawable.ic_info_white_24dp);

            LatLng from = latLng;
            LatLng to = new LatLng(item.getLatitude(),
                    item.getLongitude());
            Double dis = SphericalUtil.computeDistanceBetween(from, to);
            DecimalFormat df = new DecimalFormat("0.#");

            double userRating = 0.0;
            if(item.getRating() != null){
                userRating = item.getRating();
            }

            title.setText(item.getName());
            address.setText(item.getVicinity());
            distance.setText(String.valueOf(df.format(dis/1000))+" Km");
            rating.setText(String.valueOf(userRating));
            iv_vote.setRating((float) userRating);

            String url = "http://maps.google.com/maps/api/staticmap?center=" + item.getLatitude() + ","
                    + item.getLatitude() + "&zoom=20&size=600x400&&markers=color:red%7C"
                    + item.getLatitude() + ","
                    + item.getLongitude();

            String img_url = item.getPhotoReference() != null ?
                    "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+
                            item.getPhotoReference()+"&key="+ itemView.getResources().getString(R.string.google_maps_api_key) :
                    url;

            Glide.with(itemView).load(img_url)
                    .apply(requestOptions)
                    .into(cover);

            if (isFav) iv_fav.setImageResource(R.drawable.ic_bookmark_black_24dp);
            else iv_fav.setImageResource(R.drawable.ic_bookmark_border_black_24dp);

            isFav = false;
        }

        private void getDataFav(String id){
            FavoriteHelper favoriteHelper = new FavoriteHelper(itemView.getContext());
            favoriteHelper.open();

            Cursor cursor = itemView.getContext().getContentResolver().query(
                    Uri.parse(CONTENT_URI + "/" + id),null,null,null,null
            );
            if (cursor != null) {
                if (cursor.moveToFirst()) isFav = true;
                cursor.close();
            }
        }
    }

    public FavoriteAdapter(Cursor cursor, int rowLayout, Context context, LatLng location) {
        this.places = cursor;
        this.rowLayout = rowLayout;
        this.context = context;
        this.latLng = location;
    }

    public void replaceAll(Cursor items) {
        places = items;
        notifyDataSetChanged();
    }

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, final int position) {
        holder.bind(getItem(position), latLng);
    }

    @Override
    public int getItemCount() {
        if (places == null) return 0;
        return places.getCount();
    }

    private FavoriteModel getItem(int position) {
        if (!places.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new FavoriteModel(places);
    }

}