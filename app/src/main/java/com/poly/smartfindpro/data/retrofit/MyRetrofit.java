package com.poly.smartfindpro.data.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static ListServices listService;

    private static final String areaApi = "https://lifecardtest.viviet.vn";

    public static String Authorization = "eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.GsHvc-8OefGRoN5FZPgHTz9P29NyWTb44s_LzdGRQo7ow3FNIIk9Vv8NFYxDSHeOevknSG891jKa83vMeNOA1p2rgAlVpwlY3VzsWld9QVnu3timrhicmynney9RjDEHSreeBLK919Ei9J0OUwyS-TMuCaDSlPqTKWnQPepRiAJv5eoVBQ2aR72ffAQFWyHQQ01Jq8JsfbHbePt7fSNMByz44SfhsVuKAgf9UZKmcPWIovsUWsygKhJ5XBJQeVfibGXDXNVSl7JV6ews0oNyMHq85PlFuTHUUsgBZRNyslrMKxQHs2bR8VPmDmSIfndWcAGcYeQL44J-wymN_5z1ZA.RfVbT4K2jbbeU3j8.Hc38QUh-1gT-te5cMcfJiTI9PBmLuN8NQKs0P6lf8X2rHtTUpPjx7t3Th1WCENlW7LGLgiRgoAr-KtkEgNFh-ZCv0weDTKrhvER0WGnaORxFjo_M2QVOMmKstTNaTzJTCqA7AsVGZ0s5_L-jFQ-ZPPTZAiVQWKzaMHxCDox2QrhGc_FCiSoGrPA0h2cSLCASmBPClk3og8Kaom0BVB0y1IFDZs96WfzQRLlBDQshqt8jZxLIQQTJ7XAs0ZjCfN11FZCAbAFWg7VZ2fexdaLve-2N21UL_QO6x3TZ70MCHAt6z2illMlHD1mkN9d-yyLuWIX6ZQ1ZGNxsHe3XIbiBdtwjisdIxHPUHtQcSHjLsO6bIazor5E-nfssRcKS5YLEF_UUbzTuI5UaDYaYOEl3RAsJTzdACIR59MENjrjC52VGIgwYLVGKeUOs05oej2B7q2yo7KYKKz5orTSeE0MxOU-zbkBtV0plrgUWEFFt8ud5KYXbAorh9R9beZmgoLF7TfCyvEn-r65CrV7rrto0MHovS4Qc8y7r9KAuNMOcoJxXAjmC_59Qsis6yTrfGRjzGhu7zHHvk4d5BtQrqY_qquju42F7rlE2YLgViykn2WFj41QWRvTP_gOpBqLAkgTnm6a6liwjlUn89CoEywJcECZOZPPoi9ZF_c1r721rz75Mqy63j-aIjGkPa5bGeWY_g4exPLJisjG2txQFF9737IWelJLosxnCAXi6vkpqb1jhqsC_DXHAaWrPJas7w4hWNXCfacdJVtJbUysJcH8JehrJzXDgzv5f5fo4iUo3qBSLOiFt1PAw5ztrWzunGL07d4KxhH4pcrPrlVnPqCw9rZCycmrkZn_nWm3BcahC07uMhg_AwbJJYZ_rFoM.psevOPdyiO-S_dNsct81Lw";


    public static final int CONNECT_TIMEOUT = 30 * 1000000;

    public static ListServices getInstanceArea() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Authorization", Authorization).build();
                        return chain.proceed(request);
                    }
                });

        if (listService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(areaApi)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();

            listService = retrofit.create(ListServices.class);
        }
        return listService;
    }

}
