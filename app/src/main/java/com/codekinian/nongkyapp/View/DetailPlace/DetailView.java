package com.codekinian.nongkyapp.View.DetailPlace;

import android.content.Intent;

import com.codekinian.nongkyapp.Model.GoogleDetailModel.GDetailModel;

interface DetailView {

    void showLoading();

    void hideLoading();

    void getDataSuccess(GDetailModel item);

    void getDataFail(String message);

    void refreshData();

    void moveToDirect(Intent intent);
}
