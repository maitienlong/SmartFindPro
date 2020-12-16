package com.poly.smartfindpro.data.retrofit;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofitSmartFind {

    private static ListServices listService;

    public static final String smartFind = "http://128.199.99.9:9090/";

    public static final int CONNECT_TIMEOUT = 10 * 1000000;


    public static ListServices getInstanceSmartFind() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .retryOnConnectionFailure(true);

        if (listService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(smartFind)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();

            listService = retrofit.create(ListServices.class);
        }
        return listService;
    }



}
