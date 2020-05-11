package com.codekinian.nongkyapp.View.Maps;

import com.codekinian.nongkyapp.Base.BasePresenter;
import com.codekinian.nongkyapp.Model.GoogleDetailModel.GDetailModel;
import com.codekinian.nongkyapp.Network.NetworkCallback;

public class DirectMapsPresenter extends BasePresenter<DirectMapsView> {
    DirectMapsPresenter(DirectMapsView view) {
        super.attachView(view);
    }

    void loadData(String place_id, String key, String lang) {
        view.showLoading();
        addSubscribe(apiStores.getDetail(key, place_id, lang), new NetworkCallback<GDetailModel>() {
            @Override
            public void onSuccess(GDetailModel model) {
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
}

