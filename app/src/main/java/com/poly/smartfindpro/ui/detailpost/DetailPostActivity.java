package com.poly.smartfindpro.ui.detailpost;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

import java.lang.reflect.Type;
import java.util.List;

public class DetailPostActivity extends BaseDataBindActivity<ActivityInformationPostBinding, DetailPostPresenter>
        implements DetailPostContact.ViewModel, OnFragmentCloseCallback {
    private Products mProduct;

    private DetailImageAdapter adapter;

    private CommentPostAdapter commentPostAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_post;
    }

    @Override
    protected void initView() {
        getData();

        mPresenter = new DetailPostPresenter(this, this, mBinding);
        mBinding.setPresenter(mPresenter);
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
        PopupMenu popupMenu = new PopupMenu(this, mBinding.btnShare);
        popupMenu.inflate(R.menu.menu_item_share);

        popupMenu.show();
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
}