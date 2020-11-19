package com.poly.smartfindpro.ui.post.adressPost;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.poly.smartfindpro.databinding.FragmentAddressPostBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.loginFragment.LoginContract;
import com.poly.smartfindpro.ui.login.loginFragment.LoginPresenter;
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
        Spinner mSpnProvince = mBinding.spnProvince;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mActivity,
                R.array.province_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnProvince.setAdapter(adapter);


        Spinner mSpnDistrict = mBinding.spnDistrict;

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mActivity,
                R.array.district_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnDistrict.setAdapter(adapter1);

        Spinner mSpnComnune = mBinding.spnComune;

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mActivity,
                R.array.district_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnComnune.setAdapter(adapter2);

        EditText edtDetailAdress = mBinding.edtDetialAdress;

        Button btnContinue = mBinding.btnContinue;
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Address address = new Address();

                address.setProvinceCity(mSpnProvince.getSelectedItem().toString());
                address.setDistrictsTowns(mSpnDistrict.getSelectedItem().toString());
                address.setCommuneWardTown(mSpnComnune.getSelectedItem().toString());
                address.setDetailAddress(edtDetailAdress.getText().toString());

                postRequest.setAddress(address);

                onNext(new Gson().toJson(postRequest));


            }
        });
    }

    @Override
    protected void initData() {


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
