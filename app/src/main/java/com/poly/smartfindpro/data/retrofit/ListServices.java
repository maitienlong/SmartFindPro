package com.poly.smartfindpro.data.retrofit;

import com.poly.smartfindpro.data.model.addressgoogle.AddressGoogleResponse;
import com.poly.smartfindpro.data.model.area.req.AreaRequest;
import com.poly.smartfindpro.data.model.area.res.AreaResponse;
import com.poly.smartfindpro.data.model.comment.initcomment.InitComment;
import com.poly.smartfindpro.data.model.comment.initrecomment.req.CommentDetailRequest;
import com.poly.smartfindpro.data.model.comment.initrecomment.res.ReplycommentResponse;
import com.poly.smartfindpro.data.model.comment.getcomment.req.CommentRequest;
import com.poly.smartfindpro.data.model.comment.getcomment.res.CommentResponse;
import com.poly.smartfindpro.data.model.favorite.ResponseFavoritePost;
import com.poly.smartfindpro.data.model.home.req.HomeRequest;
import com.poly.smartfindpro.data.model.home.res.HomeResponse;
import com.poly.smartfindpro.data.model.initfavorite.InitFavorite;
import com.poly.smartfindpro.data.model.login.req.LoginRequest;
import com.poly.smartfindpro.data.model.login.res.LoginResponse;
import com.poly.smartfindpro.data.model.post.res.postresponse.PostResponse;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.DeleteProductRequest;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.model.product.req.ProductRequest;
import com.poly.smartfindpro.data.model.product.res.ProductResponse;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.data.model.register.req.CheckPhoneNumberRequest;
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse;
import com.poly.smartfindpro.data.model.uploadphoto.ResponsePostPhoto;

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
    Call<ResponsePostPhoto> postImage(@Part MultipartBody.Part image);

    @Multipart
    @POST("/upload-photo-array")
    Call<ResponsePostPhoto> postImageMulti(@Part MultipartBody.Part[] image);

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

    @POST("/check-phone-Number")
    Call<CheckPhoneResponse> getCheckNum(@Body CheckPhoneNumberRequest request);

    @POST("/init-user")
    Call<RegisterResponse> getRegister(@Body RegisterRequest request);

    @POST("/delete-product")
    Call<DeleteProductResponse> getDeleteProduct(@Body DeleteProductRequest request );


    @POST("/product-comment")
    Call<CommentResponse> getComment(@Body CommentRequest request);

    @POST("/init-comment")
    Call<CheckPhoneResponse> initComment(@Body InitComment request);

    @POST("/init-favorite")
    Call<CheckPhoneResponse> initFavorite(@Body InitFavorite request);

    @POST("/find-comment")
    Call<ReplycommentResponse> getReplyComment(@Body CommentDetailRequest request);

    @POST("/product-favorite")
    Call<ResponseFavoritePost> getFavorite(@Body InitFavorite request);


}
