package com.codekinian.nongkyapp.View.Maps;

import com.codekinian.nongkyapp.Model.GoogleDetailModel.GDetailModel;

interface DirectMapsView {
    void showLoading();

    void getDataSuccess(GDetailModel model);

    void getDataFail(String message);

    void hideLoading();
}
