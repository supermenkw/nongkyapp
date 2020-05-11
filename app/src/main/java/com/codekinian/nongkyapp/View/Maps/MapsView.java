package com.codekinian.nongkyapp.View.Maps;

import android.content.Intent;

import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceModel;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceResult;

import java.util.List;

public interface MapsView {
    void showLoading();

    void initMarker(List<GPlaceResult> result);

    void getDataSuccess(GPlaceModel model);

    void getDataFail(String message);

    void hideLoading();

    void moveToDirect(Intent intent);
}
