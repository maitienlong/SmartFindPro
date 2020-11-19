package com.poly.smartfindpro.ui.post.adressPost;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    private void ReciData(){
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);
    }

    @Override
    protected void initView() {
        ReciData();
        Spinner spinner = mBinding.spnProvince;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mActivity,
                R.array.province_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Spinner spinner1 = mBinding.spnDistrict;

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mActivity,
                R.array.district_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        Spinner spinner2 = mBinding.spnComune;

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mActivity,
                R.array.district_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        EditText edtDetailAdress = mBinding.edtDetialAdress;

        Button btnContinue = mBinding.btnContinue;
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, edtDetailAdress.getText().toString() + spinner2.getSelectedItem().toString() + spinner1.getSelectedItem().toString() + spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                Address address = new Address();
                address.setProvinceCity(spinner.getSelectedItem().toString());
                address.setProvinceCity(spinner1.getSelectedItem().toString());
                address.setProvinceCity(spinner2.getSelectedItem().toString());

                postRequest.setAddress(address);

                Log.d("MyCheck", new Gson().toJson(postRequest));

                Bundle bundle = new Bundle();

                bundle.putString(Config.POST_BUNDEL_RES, new Gson().toJson(postRequest));

                FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();

                UtilitiesPostFragment fragment = new UtilitiesPostFragment();

                fragment.setArguments(bundle);

                fragmentTransaction.add(R.id.fl_post, fragment);

                fragmentTransaction.addToBackStack("utilitiespost");

                fragmentTransaction.commit();

            }
        });
    }

    @Override
    protected void initData() {



    }


}
