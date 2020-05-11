package com.codekinian.nongkyapp.View.Home;

import android.app.Activity;
import android.content.Intent;

import com.codekinian.nongkyapp.Base.BasePresenter;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceModel;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceResult;
import com.codekinian.nongkyapp.Network.NetworkCallback;
import com.codekinian.nongkyapp.View.DetailPlace.DetailActivity;

public class HomePresenter extends BasePresenter<HomeView> {

    HomePresenter(HomeView view) {
        super.attachView(view);
    }

    void loadData(String page, String location, String key, String type, int radius) {
        view.showLoading();
        addSubscribe(apiStores.getAllPlace(location, key, type, true, radius, page), new NetworkCallback<GPlaceModel>() {
            @Override
            public void onSuccess(GPlaceModel model) {
                view.getDataSuccess(model);
            }

            @Override
            public void onFailure(String message) {
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    void loadMore(String page, String location, String key, String type, int radius) {
        view.showLoading();
        addSubscribe(apiStores.getAllPlace(location, key, type, true, radius, page), new NetworkCallback<GPlaceModel>() {
            @Override
            public void onSuccess(GPlaceModel model) {
                view.getMoreData(model);
            }

            @Override
            public void onFailure(String message) {
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    void getItem(GPlaceResult item, Activity activity) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("id", item.getPlaceId());
        view.moveToDetail(intent);
    }
    
}