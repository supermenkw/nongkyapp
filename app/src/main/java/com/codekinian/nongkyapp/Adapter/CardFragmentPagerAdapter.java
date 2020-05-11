package com.codekinian.nongkyapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codekinian.nongkyapp.View.Home.HomeFragment;

public class CardFragmentPagerAdapter  extends FragmentPagerAdapter {
    private float mBaseElevation;
    String[] title = new String[]{
            "Taman", "Museum", "Cafe", "Restoran"
    };

    public CardFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new HomeFragment("park");
                break;
            case 1:
                fragment = new HomeFragment("museum");
                break;
            case 2:
                fragment = new HomeFragment("cafe");
                break;
            case 3:
                fragment = new HomeFragment("restaurant");
                break;
            default:
                fragment = new HomeFragment("park");
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
