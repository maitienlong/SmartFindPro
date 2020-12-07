package com.poly.smartfindpro.ui.post.confirmPost;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.post.res.ResImagePost;
import com.poly.smartfindpro.data.model.post.res.postresponse.PostResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentConfirmPostBinding;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.data.model.post.req.Information;
import com.poly.smartfindpro.data.model.post.req.PostRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPostPresenter implements ConfirmPostContract.Presenter {

    private PostRequest postRequest;

    private List<ImageInforPost> imageInforPost;

    private List<String> listImageResult;

    private FragmentConfirmPostBinding mBinding;

    private Context contex;

    private ConfirmPostContract.ViewModel mViewModel;

    public ObservableField<String> theLoai;

    public ObservableField<String> soLuong;

    public ObservableField<String> gia;

    public ObservableField<String> datCoc;

    public ObservableField<String> gioiTinh;

    public ObservableField<String> diaChi;

    public ObservableField<String> tienDien;

    public ObservableField<String> tienNuoc;

    public ObservableField<String> tienIch;

    public ObservableField<String> moTa;

    public ConfirmPostPresenter(Context context, ConfirmPostContract.ViewModel mViewModel, FragmentConfirmPostBinding binding) {
        this.contex = context;
        this.mViewModel = mViewModel;
        this.mBinding = binding;
        initData();
    }

    public void initData() {
        listImageResult = new ArrayList<>();
        theLoai = new ObservableField<>();
        soLuong = new ObservableField<>();
        gia = new ObservableField<>();
        datCoc = new ObservableField<>();
        gioiTinh = new ObservableField<>();
        diaChi = new ObservableField<>();
        tienDien = new ObservableField<>();
        tienNuoc = new ObservableField<>();
        tienIch = new ObservableField<>();
        moTa = new ObservableField<>();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void setPostRequest(PostRequest postRequest, List<ImageInforPost> imageInforPost) {
        this.postRequest = postRequest;
        this.imageInforPost = imageInforPost;
    }


    public void setTheLoai(String theLoai) {
        this.theLoai.set(theLoai);
    }

    public void setSoLuong(int soLuong) {
        this.soLuong.set(String.valueOf(soLuong));
    }

    public void setGia(int gia) {
        this.gia.set(String.valueOf(gia));
    }

    public void setDatCoc(int datCoc) {
        this.datCoc.set(String.valueOf(datCoc));
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh.set(gioiTinh);
    }

    public void setDiaChi(String diaChi) {
        this.diaChi.set(diaChi);
    }

    public void setTienDien(int tienDien) {
        this.tienDien.set(String.valueOf(tienDien));
    }

    public void setTienNuoc(int tienNuoc) {
        this.tienNuoc.set(String.valueOf(tienNuoc));
    }

    public void setTienIch(List<String> tienIch) {
        String tIch = "";
        for (String item : tienIch) {
            if (tIch.isEmpty()) {
                tIch = tIch + item;
            } else {
                tIch = tIch + ", " + item;
            }

        }
        this.tienIch.set(tIch);
    }

    public void setMoTa(String moTa) {
        this.moTa.set(moTa);
    }


    public void onSubmitToServer(List<String> photoList) {
        Information information = postRequest.getInformation();

        information.setImage(photoList);

        postRequest.setId(Config.TOKEN_USER);

        postRequest.setContent(mBinding.edtTitle.getText().toString());

        postRequest.setInformation(information);

        MyRetrofitSmartFind.getInstanceSmartFind().initPost(postRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.code() == 200) {
                    mViewModel.hideLoading();
                    if (response.body().getPostResponseHeader().getResCode() == 200) {
                        mViewModel.onConfirm();
                    } else {
                        mViewModel.showMessage("Bài đăng bị lỗi: Code " + response.body().getPostResponseHeader().getResCode() + " - msg: " + response.body().getPostResponseHeader().getResMessage());
                    }
                    Log.d("postProduct", new Gson().toJson(response.body()));
                } else {
                    mViewModel.hideLoading();

                    mViewModel.showMessage(contex.getString(R.string.services_not_avail) + " - msg: Đăng bài viết không thành công");

                    Log.d("postProduct", response.code() + " - " + response.message());

                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                mViewModel.showMessage(contex.getString(R.string.services_not_avail) + " - msg: Đăng bài viết không thành công");
                mViewModel.hideLoading();
            }
        });

    }

    public void requestUploadSurvey() {
        mViewModel.showLoadingDialog();
        MultipartBody.Part propertyImagePart;

        MultipartBody.Part[] surveyImagesParts;

        if (imageInforPost.size() > 0 && imageInforPost.size() < 2) {
            File propertyImageFile = new File(imageInforPost.get(0).getRealPath());

            RequestBody resBody = RequestBody.create(MediaType.parse("multipart/form-data"), propertyImageFile);

            propertyImagePart = MultipartBody.Part.createFormData("photo", propertyImageFile.getAbsolutePath(), resBody);

            MyRetrofitSmartFind.getInstanceSmartFind().postImage(propertyImagePart).enqueue(new Callback<ResImagePost>() {
                @Override
                public void onResponse(Call<ResImagePost> call, Response<ResImagePost> response) {
                    if (response.code() == 200) {
                        onSubmitToServer(response.body().getAddressImage());
                    } else {
                        mViewModel.hideLoading();
                        mViewModel.showMessage(contex.getString(R.string.services_not_avail) + " - " + response.code() + " - msg: Đăng ảnh không thành công");
                    }
                }

                @Override
                public void onFailure(Call<ResImagePost> call, Throwable t) {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(contex.getString(R.string.services_not_avail) + " - msg: Đăng ảnh không thành công");
                }
            });

        } else {
            surveyImagesParts = new MultipartBody.Part[imageInforPost.size()];

            for (int i = 0; i < imageInforPost.size(); i++) {

                File file = new File(imageInforPost.get(i).getRealPath());

                RequestBody resBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                surveyImagesParts[i] = MultipartBody.Part.createFormData("photo", file.getAbsolutePath(), resBody);
            }

            MyRetrofitSmartFind.getInstanceSmartFind().postImageMulti(surveyImagesParts).enqueue(new Callback<ResImagePost>() {
                @Override
                public void onResponse(Call<ResImagePost> call, Response<ResImagePost> response) {
                    if (response.code() == 200) {
                        onSubmitToServer(response.body().getAddressImage());
                    } else {
                        mViewModel.hideLoading();
                        mViewModel.showMessage(contex.getString(R.string.services_not_avail) + " - " + response.code() + " - msg: Đăng ảnh không thành công");
                    }
                }

                @Override
                public void onFailure(Call<ResImagePost> call, Throwable t) {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(contex.getString(R.string.services_not_avail) + " - msg: Đăng ảnh không thành công");
                }
            });
        }
    }

    public void onRequestToServer() {
        if (mBinding.edtTitle.getText().toString().isEmpty()) {
            mViewModel.showMessage("Vui lòng nhập tiêu đề bài đăng !");
        } else {
            requestUploadSurvey();
        }
    }
}
