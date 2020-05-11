package com.codekinian.nongkyapp.Network;

import com.codekinian.nongkyapp.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    /*private static Retrofit.Builder builder = new Retrofit.Builder()
                                            .baseUrl(BASE_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                                                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public <S> S serviceNOAuth(
            Class<S> serviceClass) {
        httpClient.addInterceptor(logging);
        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }*/

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}