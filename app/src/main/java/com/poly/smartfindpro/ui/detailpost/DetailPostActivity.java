package com.poly.smartfindpro.ui.detailpost;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Share;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.callback.OnFragmentCloseCallback;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.comment.initrecomment.req.CommentDetailRequest;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;
import com.poly.smartfindpro.ui.detailcomment.DetailCommentFragment;
import com.poly.smartfindpro.ui.detailpost.adapter.CommentPostAdapter;
import com.poly.smartfindpro.ui.detailpost.adapter.DetailImageAdapter;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.user.profile.ProfileFragment;
import com.poly.smartfindpro.utils.BindingUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DetailPostActivity extends BaseDataBindActivity<ActivityInformationPostBinding, DetailPostPresenter>
        implements DetailPostContact.ViewModel, OnFragmentCloseCallback {
    private Products mProduct;

    private DetailImageAdapter adapter;

    private CommentPostAdapter commentPostAdapter;

    private int MY_PERMISSIONS_REQUEST = 2020;

    private CallbackManager callbackManager;

    private ShareButton shareButton;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_post;
    }

    @Override
    protected void initView() {
        getData();

        mPresenter = new DetailPostPresenter(this, this, mBinding);
        mBinding.setPresenter(mPresenter);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void initData() {
        adapter = new DetailImageAdapter(this);
        commentPostAdapter = new CommentPostAdapter(this, this);

        adapter.setImage(mProduct.getProduct().getInformation().getImage());
        mBinding.rvListImage.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.rvListImage.setAdapter(adapter);
        mBinding.rvListImage.setLayoutManager(layoutManager);
        mPresenter.setData(mProduct);

    }


    private void getData() {
        Type type = new TypeToken<Products>() {
        }.getType();
        Intent intent = getIntent();
        mProduct = new Products();
        mProduct = new Gson().fromJson(intent.getStringExtra(Config.POST_BUNDEL_RES), type);

    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onClickCall() {
        String PhoneNum = mBinding.tvPhoneNumber.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(PhoneNum.trim())));
//        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }

    @Override
    public void onClickInbox() {
        Toast.makeText(this, "Chưa thực hiện được nhắn tin ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickProfile() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new ProfileFragment()).commit();
    }

    @Override
    public void onClickShare() {
       AlertDialog.Builder  builder= new AlertDialog.Builder(this);

       View alert = LayoutInflater.from(this).inflate(R.layout.dialog_share_post, null, false);

       builder.setView(alert);

       LinearLayout btnShareFacebook = alert.findViewById(R.id.btn_share_on_facebook);

       btnShareFacebook.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                   if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                       String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                       requestPermissions(permissions, MY_PERMISSIONS_REQUEST);
                   } else {
                       checkLoginFacebook();
                   }
               } else {
                   checkLoginFacebook();
               }
           }
       });

       builder.show();



    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri ur`i = Uri.fromFile(imageFile);
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);

    }


    private void checkLoginFacebook() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {

        } else {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    showMessage("Đăng nhập Facebook không thành công");
                }
            });
        }
    }


    @Override
    public void onClickLike() {

    }

    @Override
    public void onShowComment(List<Comments> responseList) {
        commentPostAdapter.setItem(responseList);
        BindingUtils.setAdapter(mBinding.rvComment, commentPostAdapter, false);
    }

    @Override
    public void onComment() {

    }

    @Override
    public void onCallBackAdapter(CommentDetailRequest commentDetailRequest) {
        Bundle bundle = new Bundle();

        bundle.putString(Config.POST_BUNDEL_RES, new Gson().toJson(commentDetailRequest));

        goToFragment(R.id.fl_post_detail, new DetailCommentFragment(), bundle, this::onClose);
    }


    @Override
    public void onClose(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.onRefeshComment();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLoginFacebook();
            } else {
                showMessage("Quyền truy cập đã được từ chối");
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}