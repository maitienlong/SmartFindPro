package com.poly.smartfindpro.ui.identification.step;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.poly.smartfindpro.BuildConfig;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.identification.RequestIndentifi;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.databinding.FragmentIdentificationStepBinding;
import com.poly.smartfindpro.ui.identification.veriface.FaceDetectorActivity;
import com.poly.smartfindpro.ui.identification.veriface.internal.TVSelfieImage;
import com.poly.smartfindpro.ui.searchProduct.adapter.SpinnerCatalory;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StepFragment extends BaseDataBindFragment<FragmentIdentificationStepBinding, StepPresenter> implements StepContract.ViewModel {

    static final int REQUEST_IMAGE_CAPTURE = 5;

    private String currentPhotoPath;

    private int TRUOC = 0;

    private RequestIndentifi mProduct;

    private List<ImageInforPost> mImage;

    private String[] imagePath = {"", "", ""};

    private Bitmap[] imagePaths = new Bitmap[2];

    private List<TVSelfieImage> mListBitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_identification_step;
    }

    @Override
    protected void initView() {

        mPresenter = new StepPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

        List<String> listTypeCard = new ArrayList<>();
        listTypeCard.add("--- Chọn thể loại ---");
        listTypeCard.add("Chứng minh nhân dần 9 số");
        listTypeCard.add("Chứng minh nhân dân 12 số");
        listTypeCard.add("Thẻ căn cước công dân");

        List<String> listGender = new ArrayList<>();
        listGender.add("--- Giới tính ---");
        listGender.add("Nam");
        listGender.add("Nữ");

        SpinnerCatalory spinnerTypeCard = new SpinnerCatalory(mActivity, listTypeCard);

        mBinding.spnTypeIdentification.setAdapter(spinnerTypeCard);

        SpinnerCatalory spinnerGender = new SpinnerCatalory(mActivity, listGender);

        mBinding.spnSex.setAdapter(spinnerGender);


    }

    @Override
    protected void initData() {
        mProduct = new RequestIndentifi();

        mImage = new ArrayList<>();

        mListBitmap = new ArrayList<>();

        mBinding.spnTypeIdentification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mPresenter.setTypeCard("");
                    mPresenter.setMaxLength(0);
                } else if (position == 1) {
                    String item = (String) parent.getItemAtPosition(position);
                    mPresenter.setTypeCard(item);
                    mPresenter.setMaxLength(9);
                } else if (position == 2) {
                    String item = (String) parent.getItemAtPosition(position);
                    mPresenter.setTypeCard(item);
                    mPresenter.setMaxLength(12);
                } else if (position == 3) {
                    String item = (String) parent.getItemAtPosition(position);
                    mPresenter.setTypeCard(item);
                    mPresenter.setMaxLength(12);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBinding.spnSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mPresenter.setGender("");
                } else if (position == 1) {
                    String item = (String) parent.getItemAtPosition(position);
                    mPresenter.setGender(item);
                } else if (position == 2) {
                    String item = (String) parent.getItemAtPosition(position);
                    mPresenter.setGender(item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onTakeCMNDTruoc() {
        TRUOC = 0;
        dispatchTakePictureIntent();
    }

    @Override
    public void onTakeCMNDSau() {
        TRUOC = 1;
        dispatchTakePictureIntent();
    }

    // ------------------------------------------------------
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("LOI", ex.getMessage());

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mActivity,
                        BuildConfig.APPLICATION_ID + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                        Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mActivity.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        if (TRUOC == 0) {
            ImageView imageView = mBinding.imgCmndTruoc;
            // Get the dimensions of the View
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;

            bmOptions.inSampleSize = scaleFactor;

            bmOptions.inPurgeable = true;

            imagePath[0] = currentPhotoPath;

            mPresenter.setImageCardTruoc(imagePath[0]);

            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            imageView.setImageBitmap(bitmap);

            imagePaths[0] = bitmap;

        } else if (TRUOC == 1) {
            ImageView imageView = mBinding.imgCmndSau;
            // Get the dimensions of the View
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            imagePath[1] = currentPhotoPath;

            mPresenter.setImageCardSau(imagePath[1]);

            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            imageView.setImageBitmap(bitmap);

            imagePaths[1] = bitmap;
        }

    }

    @Override
    public void onNextVeriFace(String jsonData) {
        List<Bitmap> mListImage = new ArrayList<>();
        for (int i = 0; i < imagePaths.length; i++) {
            mListImage.add(imagePaths[i]);
        }
        Intent intent = new Intent(mActivity, FaceDetectorActivity.class);
        intent.putExtra(Config.POST_BUNDEL_RES, jsonData);
        intent.putExtra(Config.POST_BUNDEL_RES_PHOTO, new Gson().toJson(mListImage));
        startActivity(intent);
    }

    //----------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            setPic();
            galleryAddPic();
        }
    }
}
