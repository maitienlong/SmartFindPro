package com.poly.smartfindpro.ui.detailpost;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareStoryContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
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
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivityInformationPostBinding;
import com.poly.smartfindpro.ui.detailcomment.DetailCommentFragment;
import com.poly.smartfindpro.ui.detailpost.adapter.CommentPostAdapter;
import com.poly.smartfindpro.ui.detailpost.adapter.DetailImageAdapter;
import com.poly.smartfindpro.ui.user.profile.ProfileFragment;
import com.poly.smartfindpro.utils.BindingUtils;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class DetailPostActivity extends BaseDataBindActivity<ActivityInformationPostBinding, DetailPostPresenter>
        implements DetailPostContact.ViewModel, OnFragmentCloseCallback {
    private Products mProduct;

    private DetailImageAdapter adapter;

    private CommentPostAdapter commentPostAdapter;

    private int MY_PERMISSIONS_REQUEST = 2020;

    private int MY_PERMISSIONS_REQUEST_SMS = 2021;

    private CallbackManager callbackManager;

    private ShareButton btnShareButton;

    @Override
    protected int getLayoutId() {
        Config.setStatusBarGradiant(this);
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
        startActivity(callIntent);
    }

    @Override
    public void onClickInbox() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", mProduct.getUser().getPhoneNumber(), null)));
    }

    @Override
    public void onClickProfile() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new ProfileFragment()).commit();
    }

    @Override
    public void onClickShare() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alert = LayoutInflater.from(this).inflate(R.layout.dialog_share_post, null, false);
        builder.setView(alert);

        btnShareButton = alert.findViewById(R.id.btn_share_on_facebook);
        ShareButton btnShareStory = alert.findViewById(R.id.btn_share_on_facebook_story);

        //  String urlShare = "https://smartfindpro.page.link/?link=http://www.smartfind.me/applinks/?id=" + mProduct.getId() + "&apn=com.poly.smartfindpro";
        String urlShare = "http://www.smartfind.me/applinks/?id=" + mProduct.getId();

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.banner_share);

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();

        SharePhotoContent photoContent = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .setShareHashtag(new ShareHashtag.Builder().setHashtag(urlShare).build())
                .build();

        ShareStoryContent shareStoryContent = new ShareStoryContent.Builder()
                .setBackgroundAsset(photo)
                .setAttributionLink(urlShare)
                .build();

        btnShareButton.setShareContent(photoContent);
        btnShareStory.setShareContent(shareStoryContent);
        callbackManager = CallbackManager.Factory.create();

        builder.show();
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
    public void onCallBackDeleteItem(Comments comments) {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_information, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);

        ImageView imgBottomAvatar;
        TextView tvBottomName;
        LinearLayout lnBottomDelete;
        //bottomsheet
        imgBottomAvatar = (ImageView) view.findViewById(R.id.img_bottom_avatar);
        tvBottomName = (TextView) view.findViewById(R.id.tv_bottom_name);
        lnBottomDelete = (LinearLayout) view.findViewById(R.id.ln_bottom_delete);

        // ten
        tvBottomName.setText(comments.getComment().getUser().getFullname());

        // avatar
        Glide.
                with(this)
                .load(MyRetrofitSmartFind.smartFind + comments.getComment().getUser().getAvatar())
                .placeholder(R.mipmap.imgplaceholder)
                .error(R.mipmap.imgplaceholder)
                .into(imgBottomAvatar);

        if (!comments.getComment().getUser().getId().equalsIgnoreCase(Config.TOKEN_USER)) {
            lnBottomDelete.setVisibility(View.GONE);
        }

        lnBottomDelete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                boolean result = mPresenter.onDeleteComment(comments.getComment().getId());
                Log.d("onClick-lnBottomDelete: ", String.valueOf(result));
                if (result) {
                    Toast.makeText(DetailPostActivity.this, "Xóa bình luận thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(view);
        dialog.show();
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
        } else if (requestCode == MY_PERMISSIONS_REQUEST_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                showMessage("Quyền truy cập đã được từ chối");
            }
        }


    }

    private Bitmap loadImageFromNet(String link) {

        URL url;
        Bitmap bitmap = null;
        try {
            url = new URL(link);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (IOException e) {
            Log.e("LoadImage", e + "");
        }

        return bitmap;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}