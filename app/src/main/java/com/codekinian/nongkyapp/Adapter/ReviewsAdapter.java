package com.codekinian.nongkyapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codekinian.nongkyapp.Model.GoogleDetailModel.Review;
import com.codekinian.nongkyapp.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>  {

    private List<Review> reviews;
    private int rowLayout;
    private Context context;

    static class ReviewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_name)
        TextView name;

        @BindView(R.id.tv_item_date)
        TextView date;

        @BindView(R.id.tv_reviews)
        TextView reviews;

        @BindView(R.id.img_item_photo)
        ImageView cover;

        @BindView(R.id.reviews_rate)
        SimpleRatingBar rate;

        ReviewsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ReviewsAdapter(List<Review> reviews, int rowLayout, Context context) {
        this.reviews = reviews;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ReviewsAdapter.ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.ic_info_white_24dp);

        holder.name.setText(reviews.get(position).getAuthorName());
        holder.date.setText(getDate(reviews.get(position).getTime() * 1000L));
        holder.reviews.setText(reviews.get(position).getText());
        holder.rate.setRating((float) reviews.get(position).getRating());

        Glide.with(context).load(reviews.get(position).getProfilePhotoUrl())
                .apply(requestOptions)
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    private String getDate(long timeStamp){

        try{
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("d MMM");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

}
