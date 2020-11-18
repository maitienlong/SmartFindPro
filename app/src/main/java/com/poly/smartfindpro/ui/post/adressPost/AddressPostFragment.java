package com.poly.smartfindpro.ui.post.adressPost;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentAddressPostBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.loginFragment.LoginContract;
import com.poly.smartfindpro.ui.login.loginFragment.LoginPresenter;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

public class AddressPostFragment extends BaseDataBindFragment<FragmentAddressPostBinding, AddressPostPresenter> implements AddressPostContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_post;
    }

    @Override
    protected void initView() {
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
                FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fl_post, new UtilitiesPostFragment());
                fragmentTransaction.addToBackStack("utilitiespost");
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    protected void initData() {

    }
}
