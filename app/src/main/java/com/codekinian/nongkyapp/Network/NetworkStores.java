package com.codekinian.nongkyapp.Network;

import com.codekinian.nongkyapp.Model.GoogleDetailModel.GDetailModel;
import com.codekinian.nongkyapp.Model.GooglePlaceModel.GPlaceModel;
import com.codekinian.nongkyapp.Model.PlaceResult;
import com.codekinian.nongkyapp.Model.Places;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkStores {
    String key = "";
    @GET("places")
    Observable<Places> getAllPlace(@Query("page") long page);

    @GET("places/{id}")
    Observable<PlaceResult> getDetailPlace(@Path("id") int id);

    @GET("all_places")
    Observable<Places> getAllMarker();

    @GET("details/json")
    Observable<GDetailModel> getDetail(@Query("key") String key,
                                       @Query("place_id") String place_id,
                                       @Query("language") String language);

    @GET("nearbysearch/json")
    Observable<GPlaceModel> getAllPlace(@Query("location") String location,
                                        @Query("key") String key,
                                        @Query("types") String type,
                                        @Query("zagatselected") Boolean zagat,
                                        @Query("radius") int radius,
                                        @Query("pagetoken") String page);

}
