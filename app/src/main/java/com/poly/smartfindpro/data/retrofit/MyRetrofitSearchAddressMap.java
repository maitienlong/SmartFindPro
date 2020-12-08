package com.poly.smartfindpro.data.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofitSearchAddressMap {

    private static ListServices listService;

    private static final String mapApi = "https://maps.googleapis.com";

    public static final int CONNECT_TIMEOUT = 30 * 1000000;

    public static ListServices getInstanceArea() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .retryOnConnectionFailure(true);

        if (listService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mapApi)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();

            listService = retrofit.create(ListServices.class);
        }
        return listService;
    }



}
