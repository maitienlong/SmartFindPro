package com.poly.smartfindpro.ui.user.setting.information;

import android.content.Context;
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
import com.poly.smartfindpro.data.model.product.deleteProduct.req.res.DeleteProductResponse;
import com.poly.smartfindpro.data.model.profile.req.ProfileRequest;
import com.poly.smartfindpro.data.model.profile.req.UserRequest;
import com.poly.smartfindpro.data.model.profile.res.ProfileResponse;
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

    private com.poly.smartfindpro.data.model.home.res.Address address;

    private ResultArea resultArea;

    private ProfileResponse mProfile;

    private User user;

    public AddressPresenter(Context mContext, AddressContact.ViewModel mViewModel, FragmentAddressBinding mBinding) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        this.mBinding = mBinding;
        initData();
    }

    private void initData() {
        resultArea = new ResultArea();
        address = new Address();
//        getInfor();
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
                        for (int i = 0; i < resultArea.getListArea().size(); i++) {
                            if (!resultArea.getListArea().get(i).getProvince().equals("HNO")) {
                                resultArea.getListArea().remove(i);
                            }
                        }
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


    public void setP(String p) {
        address.setProvinceCity(p);
    }

    public void setD(String d) {
        address.setDistrictsTowns(d);
    }

    public void setC(String c) {
        address.setCommuneWardTown(c);
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

    public void onNext() {
        mViewModel.onNext();
        onGetLocation();
        updateUser();
    }


    private void onGetLocation() {
        address.setDetailAddress(mBinding.edtDetialAdress.getText().toString());
        String input = address.getDetailAddress() + ", " + address.getCommuneWardTown() + ", " + address.getDistrictsTowns() + ", " + address.getProvinceCity();
        Log.d("CheckInput", input);
        Log.d("CheckAddress",new Gson().toJson(address));
        getInfor();

    }
//
    public void getInfor() {
        ProfileRequest request = new ProfileRequest();
        request.setId(Config.TOKEN_USER);
        MyRetrofitSmartFind.getInstanceSmartFind().getProfile(request).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code() == 200) {
                    mProfile = response.body();
                    user = mProfile.getResponseBody().getUser();
                    user.setAddress(address);
                } else {

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    public void updateUser() {
        UserRequest request = new UserRequest();

        request.setUserId(Config.TOKEN_USER);

        request.setAddress(address);

        MyRetrofitSmartFind.getInstanceSmartFind().getUpdateUser(request).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                Log.d("CheckUpdate" ,new Gson().toJson(request));
                Log.d("CheckUpdate" ,new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {

            }
        });
    }




}
