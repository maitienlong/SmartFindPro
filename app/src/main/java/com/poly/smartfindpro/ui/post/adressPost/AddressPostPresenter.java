package com.poly.smartfindpro.ui.post.adressPost;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.addressgoogle.AddressGoogleResponse;
import com.poly.smartfindpro.data.model.area.req.AreaReqHeader;
import com.poly.smartfindpro.data.model.area.req.AreaRequest;
import com.poly.smartfindpro.data.model.area.res.AreaResponse;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.model.base.Location;
import com.poly.smartfindpro.data.retrofit.MyRetrofit;
import com.poly.smartfindpro.data.model.post.req.Address;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSearchAddressMap;
import com.poly.smartfindpro.databinding.FragmentAddressPostBinding;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressPostPresenter implements AddressPostContract.Presenter {
    private Context context;

    private AddressPostContract.ViewModel mViewModel;

    private ResultArea resultArea;

    private Address address;

    private FragmentAddressPostBinding mBinding;

    public AddressPostPresenter(Context context, AddressPostContract.ViewModel mViewModel, FragmentAddressPostBinding binding) {
        this.context = context;
        this.mViewModel = mViewModel;
        this.mBinding = binding;
        initData();
    }

    private void initData() {
        resultArea = new ResultArea();
        address = new Address();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

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


    @Override
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
                            if(!resultArea.getListArea().get(i).getProvince().equals("HNO")){
                                resultArea.getListArea().remove(i);
                            }
                        }
                        mViewModel.onShowProvince(resultArea);
                    } else if (areaType == 1) {
                        mViewModel.onShowDistrict(resultArea);
                    } else if(areaType == 2){
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

    public void onNext(){
        onGetLocation();
     //   mViewModel.onSubmitData(address);
    }

    private void onGetLocation(){
        mViewModel.showLoading();
        address.setDetailAddress(mBinding.edtDetialAdress.getText().toString());
        String input = address.getDetailAddress()+", "+address.getCommuneWardTown()+", "+address.getDistrictsTowns()+", "+address.getProvinceCity();
        Log.d("CheckInput", input);
        MyRetrofitSearchAddressMap.getInstanceArea().getFindLocation(input, "textquery", "photos,formatted_address,name,geometry", "AIzaSyCmxFS2arHibTbROQAfTkZAJRkEpz8LErU").enqueue(new Callback<AddressGoogleResponse>() {
            @Override
            public void onResponse(Call<AddressGoogleResponse> call, Response<AddressGoogleResponse> response) {
                mViewModel.hideLoading();
                if(response.code() == 200){
                    if(response.body().getCandidates() != null){
                        if(response.body().getCandidates().size() > 1){
                            mViewModel.onSubmitData(address, 1, response.body().getCandidates());
                        }else if(response.body().getCandidates().size() == 1) {
                            String log = String.valueOf(response.body().getCandidates().get(0).getGeometry().getLocation().getLng()) ;
                            String lat = String.valueOf(response.body().getCandidates().get(0).getGeometry().getLocation().getLat()) ;
                            Location location = new Location();
                            location.setLongitude(log);
                            location.setLatitude(lat);
                            address.setLocation(location);
                            mViewModel.onSubmitData(address, 0, null);
                        }else {
                            mViewModel.showMessage("Không tìm thấy vị trí");
                        }
                    }else {
                        mViewModel.showMessage("Không tìm thấy vị trí");
                    }

                }else {
                    mViewModel.showMessage(context.getString(R.string.services_not_avail));
                }
            }

            @Override
            public void onFailure(Call<AddressGoogleResponse> call, Throwable t) {
                mViewModel.hideLoading();
            }
        });

    }
}
