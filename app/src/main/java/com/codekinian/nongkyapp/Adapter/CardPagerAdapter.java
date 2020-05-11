package com.codekinian.nongkyapp.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codekinian.nongkyapp.Model.CardItem;
import com.codekinian.nongkyapp.R;

import java.util.List;

public class CardPagerAdapter extends RecyclerView.Adapter<CardPagerAdapter.ViewHolder> {
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;

    public CardPagerAdapter(Context context, List<CardItem> mData) {
        this.context = context;
        this.mData = mData;
    }

    public CardPagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new CardPagerAdapter.ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(CardPagerAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
//            textView = itemView.findViewById(R.id.title_card);
        }
    }
}