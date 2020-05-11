package com.codekinian.nongkyapp.View.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codekinian.nongkyapp.Adapter.CardFragmentPagerAdapter;
import com.codekinian.nongkyapp.Adapter.CardPagerAdapter;
import com.codekinian.nongkyapp.Base.MvpFragment;
import com.codekinian.nongkyapp.R;
import com.codekinian.nongkyapp.Utils.ShadowTransformer;
import com.codekinian.nongkyapp.Utils.TabLayout.CustomTabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends MvpFragment<MainPresenter> implements MainView {
    private CardPagerAdapter mCardAdapter;
    public static String POSITION = "POSITION";
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private Unbinder unbinder;
    private Unbinder unbinderTwo;
    private FragmentActivity myContext;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    CustomTabLayout tabLayout;


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.unbinder = ButterKnife.bind(this, view);

        View headerView = inflater.inflate(R.layout.category_card, container, false);

        CardView parkTab = (CardView) headerView.findViewById(R.id.park_tab);
        CardView museumTab = (CardView) headerView.findViewById(R.id.museum_tab);
        CardView kafeTab = (CardView) headerView.findViewById(R.id.kafe_tab);
        CardView restoTab = (CardView) headerView.findViewById(R.id.resto_tab);

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mFragmentCardAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(parkTab);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(museumTab);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(kafeTab);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setCustomView(restoTab);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        if(savedInstanceState == null){
            viewPager.setCurrentItem(0);
        }


        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        if (this.unbinder != null) {
            this.unbinder.unbind();
        }

        super.onDestroyView();
    }
}
