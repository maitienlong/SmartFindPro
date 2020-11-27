package com.poly.smartfindpro.ui.post.confirmPost;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.poly.smartfindpro.ui.post.model.PostRequest;

import java.util.List;

public class ConfirmPostPresenter implements ConfirmPostContract.Presenter {

    private PostRequest postRequest;

    private Context contex;

    private ConfirmPostContract.ViewModel mViewModel;

    public ObservableField<String> theLoai;

    public ObservableField<String> soLuong;

    public ObservableField<String> gia;

    public ObservableField<String> datCoc;

    public ObservableField<String> gioiTinh;

    public ObservableField<String> diaChi;

    public ObservableField<String> tienDien;

    public ObservableField<String> tienNuoc;

    public ObservableField<String> tienIch;

    public ObservableField<String> moTa;


    public ConfirmPostPresenter(Context context, ConfirmPostContract.ViewModel mViewModel) {
        this.contex = context;
        this.mViewModel = mViewModel;

        initData();
    }

    public void initData() {
        theLoai = new ObservableField<>();
        soLuong = new ObservableField<>();
        gia = new ObservableField<>();
        datCoc = new ObservableField<>();
        gioiTinh = new ObservableField<>();
        diaChi = new ObservableField<>();
        tienDien = new ObservableField<>();
        tienNuoc = new ObservableField<>();
        tienIch = new ObservableField<>();
        moTa = new ObservableField<>();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void setPostRequest(PostRequest postRequest) {
        this.postRequest = postRequest;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai.set(theLoai);
    }

    public void setSoLuong(int soLuong) {
        this.soLuong.set(String.valueOf(soLuong));
    }

    public void setGia(int gia) {
        this.gia.set(String.valueOf(gia));
    }

    public void setDatCoc(int datCoc) {
        this.datCoc.set(String.valueOf(datCoc));
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh.set(gioiTinh);
    }

    public void setDiaChi(String diaChi) {
        this.diaChi.set(diaChi);
    }

    public void setTienDien(int tienDien) {
        this.tienDien.set(String.valueOf(tienDien));
    }

    public void setTienNuoc(int tienNuoc) {
        this.tienNuoc.set(String.valueOf(tienNuoc));
    }

    public void setTienIch(List<String> tienIch) {
        String tIch = "";
        for (String item : tienIch) {
            if (tIch.isEmpty()) {
                tIch = tIch + item;
            } else {
                tIch = tIch + ", " + item;
            }

        }
        this.tienIch.set(tIch);
    }

    public void setMoTa(String moTa) {
        this.moTa.set(moTa);
    }
}
