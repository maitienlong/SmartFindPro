package com.poly.smartfindpro.data.retrofit;

import com.poly.smartfindpro.data.model.area.req.AreaRequest;
import com.poly.smartfindpro.data.model.area.res.AreaResponse;
import com.poly.smartfindpro.data.model.product.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ListServices {
    @POST("/lifecard-app/area/req")
    Call<AreaResponse> getArea(@Body AreaRequest request);

    @POST("/list-product")
    Call<Product> getAllProduct();

}
