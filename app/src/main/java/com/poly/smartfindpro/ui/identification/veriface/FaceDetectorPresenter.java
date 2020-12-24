package com.poly.smartfindpro.ui.identification.veriface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.identification.RequestIndentifi;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.model.uploadphoto.ResponsePostPhoto;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.ui.identification.veriface.internal.TVSelfieImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FaceDetectorPresenter implements FaceDetectorContract.Presenter {


    private FaceDetectorContract.ViewModel mViewModel;
    private Context mContext;

    private List<Bitmap> mSelfieImages = new ArrayList<>();

    public ObservableField<String> accountName;

    public ObservableField<String> title;

    private RequestIndentifi mProduct;

    private List<File> mListPhotoIdentifi;

    public FaceDetectorPresenter(Context mContext, FaceDetectorContract.ViewModel mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData() {
        accountName = new ObservableField<>();
        mListPhotoIdentifi = new ArrayList<>();
        title = new ObservableField<>("Nhận diện khuôn mặt");
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


    public void setmSelfieImages(List<Bitmap> mSelfieImages) {
        this.mSelfieImages = mSelfieImages;
    }

    public void setmProduct(RequestIndentifi mProduct) {
        this.mProduct = mProduct;
    }

    public void requestUploadSurvey(List<File> fileIamge) {
        mViewModel.showLoading();

        MultipartBody.Part[] surveyImagesParts;

        surveyImagesParts = new MultipartBody.Part[fileIamge.size()];

        for (int i = 0; i < fileIamge.size(); i++) {

            File file = fileIamge.get(i);

            RequestBody resBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            surveyImagesParts[i] = MultipartBody.Part.createFormData("photo", file.getAbsolutePath(), resBody);
        }

        MyRetrofitSmartFind.getInstanceSmartFind().postImageMulti(surveyImagesParts).enqueue(new Callback<ResponsePostPhoto>() {
            @Override
            public void onResponse(Call<ResponsePostPhoto> call, Response<ResponsePostPhoto> response) {
                if (response.code() == 200) {
                    mProduct.setHasFace(response.body().getResponseBody().getAddressImage().get(0));
                    initPhotoIdentifi();
                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(mContext.getString(R.string.services_not_avail) + " - " + response.code() + " - msg: Đăng ảnh không thành công");
                }
            }

            @Override
            public void onFailure(Call<ResponsePostPhoto> call, Throwable t) {
                mViewModel.hideLoading();
                Log.d("CheckUpLoadImage", t.toString());
                mViewModel.showMessage(mContext.getString(R.string.services_not_avail) + " - msg: Đăng ảnh không thành công");
            }
        });

    }

    private void initPhotoIdentifi() {

        List<File> listPhotoIdentifi = new ArrayList<>();

        for (int i = 0; i < mSelfieImages.size(); i++) {
            listPhotoIdentifi.add(storeImage(mSelfieImages.get(i)));
        }

        onUploadPhotoIdentifi(listPhotoIdentifi);
    }

    private void onUploadPhotoIdentifi(List<File> filePhoto) {

        MultipartBody.Part[] surveyImagesParts;

        surveyImagesParts = new MultipartBody.Part[filePhoto.size()];

        for (int i = 0; i < filePhoto.size(); i++) {

            File file = filePhoto.get(i);

            RequestBody resBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            surveyImagesParts[i] = MultipartBody.Part.createFormData("photo", file.getAbsolutePath(), resBody);
        }

        MyRetrofitSmartFind.getInstanceSmartFind().postImageMulti(surveyImagesParts).enqueue(new Callback<ResponsePostPhoto>() {
            @Override
            public void onResponse(Call<ResponsePostPhoto> call, Response<ResponsePostPhoto> response) {
                if (response.code() == 200) {
                    Log.d("CheckResponseFace", new Gson().toJson(response.body()));
                    mProduct.setPrevious(response.body().getResponseBody().getAddressImage().get(0));
                    mProduct.setBehind(response.body().getResponseBody().getAddressImage().get(1));
                    onSubmit(mProduct);
                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(mContext.getString(R.string.services_not_avail) + " - " + response.code() + " - msg: Đăng ảnh không thành công");
                }
            }

            @Override
            public void onFailure(Call<ResponsePostPhoto> call, Throwable t) {
                mViewModel.hideLoading();
                Log.d("CheckUpLoadImage", t.toString());
                mViewModel.showMessage(mContext.getString(R.string.services_not_avail) + " - msg: Đăng ảnh không thành công");
            }
        });

    }

    private void onSubmit(RequestIndentifi request) {

        MyRetrofitSmartFind.getInstanceSmartFind().getUpgrade(request).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                Log.d("CheckResponseFace", new Gson().toJson(response.body()));
                if (response.code() == 200 && response.body().getResponseHeader().getResCode() == 200) {


                    mViewModel.hideLoading();
                    mViewModel.showMessage(response.body().getResponseBody().getStatus());

                } else {
                    mViewModel.hideLoading();
                    mViewModel.showMessage(response.body().getResponseHeader().getResMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                mViewModel.hideLoading();
                mViewModel.showMessage(mContext.getString(R.string.services_not_avail));
            }
        });
    }


    private File storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("CheckFace",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("CheckFace", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("CheckFace", "Error accessing file: " + e.getMessage());
        }

        return pictureFile;
    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        return mediaFile;
    }


    public void onBackClick() {
        mViewModel.onBackClick();
    }

}
