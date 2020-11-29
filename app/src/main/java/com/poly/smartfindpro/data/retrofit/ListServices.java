package com.poly.smartfindpro.data.retrofit;

import com.poly.smartfindpro.data.model.area.req.AreaRequest;
import com.poly.smartfindpro.data.model.area.res.AreaResponse;
import com.poly.smartfindpro.data.model.post.PostResponse;
import com.poly.smartfindpro.data.model.post.res.ResImagePost;
import com.poly.smartfindpro.ui.post.model.PostRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
}
