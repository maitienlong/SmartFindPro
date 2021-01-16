package com.poly.smartfindpro.ui.user.setting.information;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.addressgoogle.AddressGoogleResponse;
import com.poly.smartfindpro.data.model.area.req.AreaReqHeader;
import com.poly.smartfindpro.data.model.area.req.AreaRequest;
import com.poly.smartfindpro.data.model.area.res.AreaResponse;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.model.base.Location;
import com.poly.smartfindpro.data.model.base.User;
import com.poly.smartfindpro.data.model.home.res.Address;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.req.UserRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
import com.poly.smartfindpro.data.model.updateaddress.AddressUpdate;
import com.poly.smartfindpro.data.model.updateaddress.RequestUpdateAddress;
import com.poly.smartfindpro.data.retrofit.MyRetrofit;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSearchAddressMap;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentAddressBinding;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressPresenter implements AddressContact.Presenter {

    private Context mContext;

    private AddressContact.ViewModel mViewModel;

    private FragmentAddressBinding mBinding;

    private Address mAddress;

    private ResultArea resultArea;

    private ProfileResponse mProfile;




    public AddressPresenter(Context mContext, AddressContact.ViewModel mViewModel, FragmentAddressBinding mBinding) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {
        resultArea = new ResultArea();
        mAddress = new Address();
        getInfor();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


    public void getDataApiArea(int areaType, String jsonData) {
        AreaRequest request = new AreaRequest();
        request.setAreaReqHeader(areaReqHeader());

        byte[] jsonByte = jsonData.getBytes();
        String base64 = new String(Base64.encode(jsonByte, 2));

        request.setBody(base64);

        Type type = new TypeToken<ResultArea>() {
        }.getType();

        MyRetrofit.getInstanceArea().getArea(request).enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.code() == 200) {

                    String jsonData = new String(Base64.decode(response.body().getBody(), 1));

                    resultArea = new Gson().fromJson(jsonData, type);

                    if (areaType == 0) {
                        mViewModel.onShowProvince(resultArea);
                    } else if (areaType == 1) {
                        mViewModel.onShowDistrict(resultArea);
                    } else if (areaType == 2) {
                        mViewModel.onShowCommune(resultArea);
                    }


                } else {
                    Log.d("CheckJson", response.message() + "");
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                Log.d("CheckJson", t.toString());
            }
        });
    }

    @Override
    public void onNext() {
        if (mBinding.edtDetialAdress.getText().toString().trim().isEmpty()) {
            mViewModel.showMessage("Vui lòng nhập địa chỉ chi tiết");
        } else {
            mAddress.setDetailAddress(mBinding.edtDetialAdress.getText().toString().trim());
            mViewModel.onNext(new Gson().toJson(mAddress));
        }
    }


    public void setP(String p) {
        mAddress.setProvinceCity(p);
    }

    public void setD(String d) {
        mAddress.setDistrictsTowns(d);
    }

    public void setC(String c) {
        mAddress.setCommuneWardTown(c);
    }

    private AreaReqHeader areaReqHeader() {
        AreaReqHeader areaReqHeader = new AreaReqHeader();
        areaReqHeader.setChannelCode("VIVIET_APP");
        areaReqHeader.setClientAddress("127.0.0.1");
        areaReqHeader.setClientRequestId("1234567");
        areaReqHeader.setClientSessionId("");
        areaReqHeader.setDeviceId("abc-123-def-456");
        areaReqHeader.setExchangeIV("");
        areaReqHeader.setSystemCode("VIVIET");
        areaReqHeader.setLanguage("vi");
        Location location = new Location();
        location.setLatitude("0");
        location.setLongitude("0");
        areaReqHeader.setLocation(location);
        areaReqHeader.setPlatform("android");
        areaReqHeader.setPlatformVersion("");
        areaReqHeader.setSdkId("123");
        areaReqHeader.setSecretKey("");
        areaReqHeader.setSignature("");

        return areaReqHeader;
    }


    @Override
    public void onBackClick() {
        mViewModel.onBackClick();
    }


    public void getInfor() {
        ProfileRequest request = new ProfileRequest();
        request.setId(Config.TOKEN_USER);
        MyRetrofitSmartFind.getInstanceSmartFind().getProfile(request).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code() == 200) {
                    mProfile = response.body();
                } else {

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    public void updateAddress() {
        RequestUpdateAddress request = new RequestUpdateAddress();
        request.setUserId(Config.TOKEN_USER);
        AddressUpdate addressUpdate = new AddressUpdate();
        addressUpdate.setId(mProfile.getResponseBody().getUser().getAddress().getId());
        addressUpdate.setProvinceCity(mAddress.getProvinceCity());
        addressUpdate.setDetailAddress(mBinding.edtDetialAdress.getText().toString());
        addressUpdate.setCommuneWardTown(mAddress.getCommuneWardTown());
        addressUpdate.setDistrictsTowns(mAddress.getDistrictsTowns());
        request.setAddress(addressUpdate);

        MyRetrofitSmartFind.getInstanceSmartFind().updateAddress(request).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.body().getResponseHeader().getResCode() == 200 &&
                        response.body().getResponseHeader().getResMessage().equalsIgnoreCase("Success")) {
//                    Log.d("checkStatus", new Gson().toJson(response.body()));
//                    Log.d("checkUpdate", new Gson().toJson(request));
//                    mAddress.setId(addressUpdate.getId());
//                    mAddress.setProvinceCity(addressUpdate.getId());
//                    mAddress.setDetailAddress(addressUpdate.getDetailAddress());
//                    mAddress.setCommuneWardTown(addressUpdate.getCommuneWardTown());
//                    mAddress.setDistrictsTowns(addressUpdate.getDistrictsTowns());

                    initData();
                    Toast.makeText(mContext, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("checkStatus", new Gson().toJson(response.body()));
                    Toast.makeText(mContext, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {

            }
        });
    }


}
