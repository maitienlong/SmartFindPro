package com.poly.smartfindpro.data.retrofit;

import com.poly.smartfindpro.data.model.addressgoogle.AddressGoogleResponse;
import com.poly.smartfindpro.data.model.area.req.AreaRequest;
import com.poly.smartfindpro.data.model.area.res.AreaResponse;
import com.poly.smartfindpro.data.model.home.req.HomeRequest;
import com.poly.smartfindpro.data.model.home.res.HomeResponse;
import com.poly.smartfindpro.data.model.login.req.LoginRequest;
import com.poly.smartfindpro.data.model.login.res.LoginResponse;
import com.poly.smartfindpro.data.model.post.res.ResImagePost;
import com.poly.smartfindpro.data.model.post.res.postresponse.PostResponse;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.model.post.req.PostRequest;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ListServices {
    @POST("/lifecard-app/area/req")
    Call<AreaResponse> getArea(@Body AreaRequest request);

    @POST("/init-product")
    Call<PostResponse> initPost(@Body PostRequest request);

    @Multipart
    @POST("/upload-photo")
    Call<ResImagePost> postImage(@Part MultipartBody.Part image);

    @Multipart
    @POST("/upload-photo-array")
    Call<ResImagePost> postImageMulti(@Part MultipartBody.Part[] image);

    @POST("/find-user")
    Call<ProfileResponse> getProfile(@Body ProfileRequest request);

    @POST("/user-product")
    Call<ProductResponse> getProduct(@Body ProductRequest request);

    @POST("/list-product")
    Call<ProductResponse> getAllProduct(@Body ProductRequest request);

    @POST("/list-product")
    Call<HomeResponse> getProduct(@Body HomeRequest request);

    @GET("/maps/api/place/findplacefromtext/json")
    Call<AddressGoogleResponse> getFindLocation(@Query("input") String input,
                                                @Query("inputtype") String inputtype,
                                                @Query("fields") String fields,
                                                @Query("key") String key);

    @POST("/login")
    Call<LoginResponse> getLogin(@Body LoginRequest request);
}
