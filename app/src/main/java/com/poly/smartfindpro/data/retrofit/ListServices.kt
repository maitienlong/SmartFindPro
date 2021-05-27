package com.poly.smartfindpro.data.retrofit

import com.poly.smartfindpro.data.model.addressgoogle.AddressGoogleResponse
import com.poly.smartfindpro.data.model.area.req.AreaRequest
import com.poly.smartfindpro.data.model.area.res.AreaResponse
import com.poly.smartfindpro.data.model.comment.deleteComment.req.DeleteCommentRequest
import com.poly.smartfindpro.data.model.comment.getcomment.req.CommentRequest
import com.poly.smartfindpro.data.model.comment.getcomment.res.CommentResponse
import com.poly.smartfindpro.data.model.comment.initcomment.InitComment
import com.poly.smartfindpro.data.model.comment.initrecomment.req.CommentDetailRequest
import com.poly.smartfindpro.data.model.comment.initrecomment.res.ReplycommentResponse
import com.poly.smartfindpro.data.model.favorite.ResponseFavoritePost
import com.poly.smartfindpro.data.model.forgotpasswrd.ForgotPasswordRequest
import com.poly.smartfindpro.data.model.home.req.HomeRequest
import com.poly.smartfindpro.data.model.home.res.HomeResponse
import com.poly.smartfindpro.data.model.identification.RequestIndentifi
import com.poly.smartfindpro.data.model.initfavorite.InitFavorite
import com.poly.smartfindpro.data.model.login.logout.LogoutRequest
import com.poly.smartfindpro.data.model.login.req.LoginRequest
import com.poly.smartfindpro.data.model.login.res.LoginResponse
import com.poly.smartfindpro.data.model.notification.req.NotificationRequest
import com.poly.smartfindpro.data.model.notification.res.NotifyResponse
import com.poly.smartfindpro.data.model.post.req.PostRequest
import com.poly.smartfindpro.data.model.post.res.postresponse.PostResponse
import com.poly.smartfindpro.data.model.product.deleteProduct.req.DeleteProductRequest
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse
import com.poly.smartfindpro.data.model.product.req.ProductRequest
import com.poly.smartfindpro.data.model.product.res.ProductResponse
import com.poly.smartfindpro.data.model.product.totalPeopleLease.TotalPeopleLeaseRequest
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest
import com.poly.smartfindpro.data.model.profile.req.UserRequest
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest
import com.poly.smartfindpro.data.model.register.req.CheckPhoneNumberRequest
import com.poly.smartfindpro.data.model.register.resphonenumber.CheckPhoneResponse
import com.poly.smartfindpro.data.model.updateaddress.RequestUpdateAddress
import com.poly.smartfindpro.data.model.updateavatar.RequestUpdateAvatar
import com.poly.smartfindpro.data.model.updateavatar.RequestUpdateCover
import com.poly.smartfindpro.data.model.uploadphoto.ResponsePostPhoto
import retrofit2.Call
import retrofit2.http.*

interface ListServices {
    @POST("/lifecard-app/area/req")
    fun getArea(@Body request: AreaRequest?): Call<AreaResponse?>?

    @POST("/init-product")
    fun initPost(@Body request: PostRequest?): Call<PostResponse?>?

    @Multipart
    @POST("/upload-photo")
    fun postImage(@Part image: Part?): Call<ResponsePostPhoto?>?

    @Multipart
    @POST("/upload-photo-array")
    fun postImageMulti(@Part image: Array<Part?>?): Call<ResponsePostPhoto?>?

    @POST("/find-user")
    fun getProfile(@Body request: ProfileRequest?): Call<ProfileResponse?>?

    @POST("/user-product")
    fun getProduct(@Body request: ProductRequest?): Call<ProductResponse?>?

    @POST("/list-product")
    fun getAllProduct(@Body request: ProductRequest?): Call<ProductResponse?>?

    @POST("/list-product")
    fun getProduct(@Body request: HomeRequest?): Call<HomeResponse?>?

    @GET("/maps/api/place/findplacefromtext/json")
    fun getFindLocation(@Query("input") input: String?,
                        @Query("inputtype") inputtype: String?,
                        @Query("fields") fields: String?,
                        @Query("key") key: String?): Call<AddressGoogleResponse?>?

    @POST("/login")
    fun getLogin(@Body request: LoginRequest?): Call<LoginResponse?>?

    @POST("/check-phone-Number")
    fun getCheckNum(@Body request: CheckPhoneNumberRequest?): Call<CheckPhoneResponse?>?

    @POST("/init-user")
    fun getRegister(@Body request: RegisterRequest?): Call<CheckPhoneResponse?>?

    @POST("/delete-product")
    fun getDeleteProduct(@Body request: DeleteProductRequest?): Call<DeleteProductResponse?>?

    @POST("/delete-comment")
    fun getDeleteComment(@Body request: DeleteCommentRequest?): Call<DeleteProductResponse?>?

    @POST("/product-comment")
    fun getComment(@Body request: CommentRequest?): Call<CommentResponse?>?

    @POST("/init-comment")
    fun initComment(@Body request: InitComment?): Call<CheckPhoneResponse?>?

    @POST("/init-favorite")
    fun initFavorite(@Body request: InitFavorite?): Call<CheckPhoneResponse?>?

    @POST("/find-comment")
    fun getReplyComment(@Body request: CommentDetailRequest?): Call<ReplycommentResponse?>?

    @POST("/product-favorite")
    fun getFavorite(@Body request: InitFavorite?): Call<ResponseFavoritePost?>?

    @POST("/update-user")
    fun getUpdateUser(@Body request: UserRequest?): Call<DeleteProductResponse?>?

    @POST("/update-user")
    fun updateAddress(@Body request: RequestUpdateAddress?): Call<DeleteProductResponse?>?

    @POST("/upgrade-user")
    fun getUpgrade(@Body request: RequestIndentifi?): Call<DeleteProductResponse?>?

    @POST("/update-product")
    fun getUpdateProduct(@Body request: PostRequest?): Call<CheckPhoneResponse?>?

    @POST("/total-people-lease-product")
    fun getTotalPeopleLease(@Body request: TotalPeopleLeaseRequest?): Call<CheckPhoneResponse?>?

    @POST("/find-history")
    fun getNotification(@Body request: NotificationRequest?): Call<NotifyResponse?>?

    @POST("/update-user")
    fun updateAvatar(@Body request: RequestUpdateAvatar?): Call<DeleteProductResponse?>?

    @POST("/update-user")
    fun updateCover(@Body request: RequestUpdateCover?): Call<DeleteProductResponse?>?

    @POST("/update-user")
    fun updateInfor(@Body request: RequestUpdateCover?): Call<DeleteProductResponse?>?

    @POST("/update-user-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest?): Call<DeleteProductResponse?>?

    @POST("/log-out")
    fun logOut(@Body request: LogoutRequest?): Call<DeleteProductResponse?>?
}