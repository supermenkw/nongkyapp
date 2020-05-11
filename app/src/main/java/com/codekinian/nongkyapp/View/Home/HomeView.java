package com.codekinian.nongkyapp.View.Home;

import android.content.Intent;

import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceModel;

public interface HomeView {
        void showLoading();

        void hideLoading();

        void getDataSuccess(GPlaceModel model);

        void getDataFail(String message);

        void moveToDetail(Intent intent);

        void getMoreData(GPlaceModel model);
}