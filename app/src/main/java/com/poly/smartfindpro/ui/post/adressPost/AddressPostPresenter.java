package com.poly.smartfindpro.ui.post.adressPost;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.data.model.area.Location;
import com.poly.smartfindpro.data.model.area.req.AreaReqHeader;
import com.poly.smartfindpro.data.model.area.req.AreaRequest;
import com.poly.smartfindpro.data.model.area.req.BodyReq;
import com.poly.smartfindpro.data.model.area.res.AreaResponse;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.data.retrofit.MyRetrofit;
import com.poly.smartfindpro.ui.login.forgotPassword.ForgotPasswordContract;
import com.poly.smartfindpro.ui.post.model.PostRequest;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressPostPresenter implements AddressPostContract.Presenter {
    private Context context;

    private AddressPostContract.ViewModel mViewModel;

    private ResultArea resultArea;

    public AddressPostPresenter(Context context, AddressPostContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
        initData();
    }

    private void initData() {
        resultArea = new ResultArea();
        BodyReq bodyReq = new BodyReq("D","HNO");
        getDataApiArea("D",new Gson().toJson(bodyReq));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

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
    public void getDataApiArea(String areaType,String jsonData) {
        AreaRequest request = new AreaRequest();
        request.setAreaReqHeader(areaReqHeader());

        byte[] jsonByte = jsonData.getBytes();
        String base64 = new String(Base64.encode(jsonByte,1));

        request.setBody(base64);

        Type type = new TypeToken<ResultArea>() {
        }.getType();

        MyRetrofit.getInstanceArea().getArea(request).enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.code() == 200) {

                    String jsonData = new String(Base64.decode(response.body().getBody(),1));

                    resultArea = new Gson().fromJson(jsonData, type);

                    Log.d("CheckJson", new Gson().toJson(resultArea));

                    if(areaType.equals("D")){
                        mViewModel.onShowDistrict(resultArea);
                    }else if (areaType.equals("C")){
                        mViewModel.onShowCommune(resultArea);
                    }


                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {

            }
        });
    }
}
