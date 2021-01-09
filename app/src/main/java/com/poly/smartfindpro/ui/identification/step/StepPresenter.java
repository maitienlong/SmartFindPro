package com.poly.smartfindpro.ui.identification.step;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.identification.RequestIndentifi;
import com.poly.smartfindpro.databinding.FragmentIdentificationStepBinding;
import com.poly.smartfindpro.ui.identification.tutorial.TutorialContract;

public class StepPresenter implements StepContract.Presenter {

    private Context mContext;

    private StepContract.ViewModel mViewmodel;

    private RequestIndentifi mProduct;

    private FragmentIdentificationStepBinding mBinding;

    private String typeCard;

    private String gender;

    private String imageCardTruoc = "";

    private String imageCardSau = "";

    public ObservableField<Integer> maxLength;

    public ObservableField<String> title;

    public StepPresenter(Context mContext, StepContract.ViewModel mViewmodel, FragmentIdentificationStepBinding binding) {
        this.mContext = mContext;
        this.mViewmodel = mViewmodel;
        this.mBinding = binding;
        initData();
    }

    private void initData() {
        mProduct = new RequestIndentifi();
        maxLength = new ObservableField<>();
        title = new ObservableField<>("Thông tin định danh");
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public String getTypeCard() {
        return typeCard;
    }

    public void setTypeCard(String typeCard) {
        this.typeCard = typeCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageCardTruoc() {
        return imageCardTruoc;
    }

    public void setImageCardTruoc(String imageCardTruoc) {
        this.imageCardTruoc = imageCardTruoc;
    }

    public String getImageCardSau() {
        return imageCardSau;
    }

    public void setImageCardSau(String imageCardSau) {
        this.imageCardSau = imageCardSau;
    }

    @Override
    public void onTakeCMNDTruoc() {
        mViewmodel.onTakeCMNDTruoc();
    }

    @Override
    public void onTakeCMNDSau() {
        mViewmodel.onTakeCMNDSau();
    }

    @Override
    public void onSubmit() {
        if (typeCard.isEmpty()) {
            mViewmodel.showMessage("Không được bỏ trống loại thẻ định danh");
        } else if (mBinding.edtFullname.getText().toString().trim().isEmpty()) {
            mViewmodel.showMessage("Vui lòng nhập họ và tên");
        } else if (mBinding.edtNumberIdentifi.getText().toString().isEmpty()) {
            mViewmodel.showMessage("Vui lòng nhập số CMND/CCCD");
        } else if (mBinding.edtNumberIdentifi.getText().toString().length() != maxLength.get()) {
            mViewmodel.showMessage("Số CMND/CCCD phải là "+maxLength.get()+" số");
        }  else if (mBinding.edtDateSupply.getText().toString().isEmpty()) {
            mViewmodel.showMessage("Vui lòng nhập ngày cấp");
        } else if (mBinding.edtAddressSupply.getText().toString().isEmpty()) {
            mViewmodel.showMessage("Vui lòng nhập nơi cấp");
        } else if (mBinding.edtBirthDay.getText().toString().isEmpty()) {
            mViewmodel.showMessage("Vui lòng nhập ngày sinh");
        } else if (mBinding.edtAddressNow.getText().toString().isEmpty()) {
            mViewmodel.showMessage("Vui lòng nhập địa chỉ hiện tại");
        } else if (gender.isEmpty()) {
            mViewmodel.showMessage("Vui lòng chọn giới tính");
        } else if (mBinding.edtHomeTown.getText().toString().isEmpty()) {
            mViewmodel.showMessage("Vui lòng nhập quê quán");
        } else if (mBinding.edtNationality.getText().toString().isEmpty()) {
            mViewmodel.showMessage("Vui lòng nhập quốc tịch");
        } else if (imageCardTruoc.isEmpty()) {
            mViewmodel.showMessage("Ảnh mặt trước không được để trống");
        } else if (imageCardSau.isEmpty()) {
            mViewmodel.showMessage("Ảnh mặt sau không được để trống");
        } else {

            mProduct.setUserId(Config.TOKEN_USER );

            mProduct.setType(typeCard);

            mProduct.setGender(gender);

            mProduct.setName(mBinding.edtFullname.getText().toString().trim());

            mProduct.setCode(mBinding.edtNumberIdentifi.getText().toString().trim());

            mProduct.setExpiryDate(mBinding.edtDateSupply.getText().toString().trim());

            mProduct.setIssuedBy(mBinding.edtAddressSupply.getText().toString().trim());

            mProduct.setDate(mBinding.edtBirthDay.getText().toString());

            mProduct.setResident(mBinding.edtAddressNow.getText().toString());

            mProduct.setHomeTown(mBinding.edtHomeTown.getText().toString());

            mProduct.setNationality(mBinding.edtNationality.getText().toString());

            mProduct.setPrevious(imageCardTruoc);

            mProduct.setBehind(imageCardSau);

            mViewmodel.onNextVeriFace(new Gson().toJson(mProduct));

        }

    }

    @Override
    public void setMaxLength(int number) {
        maxLength.set(number);
    }

    @Override
    public void onBackClick() {
        mViewmodel.onBackClick();
    }
}
