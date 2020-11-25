package com.poly.smartfindpro.ui.post.adressPost;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.area.req.BodyReq;
import com.poly.smartfindpro.data.model.area.result.ListArea;
import com.poly.smartfindpro.data.model.area.result.ResultArea;
import com.poly.smartfindpro.databinding.FragmentAddressPostBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.loginFragment.LoginContract;
import com.poly.smartfindpro.ui.login.loginFragment.LoginPresenter;
import com.poly.smartfindpro.ui.post.adapter.SpinnerAreaAdapter;
import com.poly.smartfindpro.ui.post.model.Address;
import com.poly.smartfindpro.ui.post.model.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

import java.lang.reflect.Type;

public class AddressPostFragment extends BaseDataBindFragment<FragmentAddressPostBinding, AddressPostPresenter> implements AddressPostContract.ViewModel {
    private PostRequest postRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_post;
    }

    private void ReciData() {
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);

    }

    @Override
    protected void initView() {

        ReciData();

        mPresenter = new AddressPostPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);


//        Button btnContinue = mBinding.btnContinue;
//        btnContinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Address address = new Address();
//
//                address.setDetailAddress(edtDetailAdress.getText().toString());
//
//                postRequest.setAddress(address);
//
//                onNext(new Gson().toJson(postRequest));
//
//
//            }
//        });

        mBinding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BodyReq bodyReq = new BodyReq("D", "HNO");
                String jsonData = new Gson().toJson(bodyReq);
                Log.d("CheckBase", new String(Base64.decode(jsonData.getBytes(),1)));
                mPresenter.getDataApiArea("D",jsonData);
            }
        });
//        mBinding.spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ListArea listArea = (ListArea) adapterView.getItemAtPosition(i);
//                BodyReq bodyReq = new BodyReq("C", listArea.getParentCode());
//                String jsonData = new Gson().toJson(bodyReq);
//                Log.d("CheckJson", jsonData);
//                mPresenter.getDataApiArea("C",jsonData);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    @Override
    protected void initData() {


    }

    public void onShowDistrict(ResultArea resultArea) {
        SpinnerAreaAdapter adDistrict = new SpinnerAreaAdapter(mActivity, resultArea.getListArea());

        mBinding.spnDistrict.setAdapter(adDistrict);
    }

    public void onShowCommune(ResultArea resultArea) {

        SpinnerAreaAdapter adDistrict = new SpinnerAreaAdapter(mActivity, resultArea.getListArea());

        mBinding.spnComune.setAdapter(adDistrict);
    }

    public void onNext(String jsonData) {
        Fragment fragment = new UtilitiesPostFragment();

        Bundle bundle = new Bundle();

        bundle.putString(Config.POST_BUNDEL_RES, new Gson().toJson(postRequest));

        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();

        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fl_post, fragment);

        fragmentTransaction.addToBackStack("utilitiespost");

        fragmentTransaction.commit();

        Log.d("CheckLog", new Gson().toJson(postRequest));
    }


}
