package com.poly.smartfindpro.ui.listProduct;

import android.content.Context;
import android.util.Log;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.FragmentListProductBinding;


import java.util.List;

public class ListProductFragment extends BaseDataBindFragment<FragmentListProductBinding, ListProductPresenter>
implements ListProductContract.ViewModel{


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_product;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void openFragment() {

    }
}