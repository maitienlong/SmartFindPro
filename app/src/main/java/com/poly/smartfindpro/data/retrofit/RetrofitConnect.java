package com.poly.smartfindpro.data.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnect {
    private static ListServices listServices;
    private static final String myAPI = "http://192.168.0.102:9090/";
    public static final int CONNECT_TIMEOUT = 30 * 1000000;


    public static ListServices getInstanceSmartFind() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .retryOnConnectionFailure(true);

        if (listServices == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(myAPI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();

            listServices = retrofit.create(ListServices.class);
        }
        return listServices;
    }


}
