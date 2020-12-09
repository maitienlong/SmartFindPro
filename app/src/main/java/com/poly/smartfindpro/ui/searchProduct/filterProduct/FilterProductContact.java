package com.poly.smartfindpro.ui.searchProduct.filterProduct;

import com.poly.smartfindpro.data.model.home.res.Product;
import com.poly.smartfindpro.data.model.product.res.Products;

import java.util.List;

public interface FilterProductContact {
    public interface ViewModel {
        void onShow(List<Products> products);

        void onClickFilter();

        void onShowMsg(String msg);
    }

    public interface Presenter {
        void onClickFilter();

        void setData(String theLoai, int soLuong, int giaTien, int tienDien, int tienNuoc, String gioiTinh);
    }
}
