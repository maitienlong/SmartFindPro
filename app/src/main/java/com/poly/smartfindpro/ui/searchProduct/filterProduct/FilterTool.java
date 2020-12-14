package com.poly.smartfindpro.ui.searchProduct.filterProduct;

public class FilterTool {
    private String theLoai;
    private int soLuongNguoi;
    private int gia;
    private String gioiTinh;
    private int tienDien;
    private int tienNuoc;

    public FilterTool() {
    }

    public FilterTool(String theLoai, int soLuongNguoi, int gia, String gioiTinh, int tienDien, int tienNuoc) {
        this.theLoai = theLoai;
        this.soLuongNguoi = soLuongNguoi;
        this.gia = gia;
        this.gioiTinh = gioiTinh;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getSoLuongNguoi() {
        return soLuongNguoi;
    }

    public void setSoLuongNguoi(int soLuongNguoi) {
        this.soLuongNguoi = soLuongNguoi;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getTienDien() {
        return tienDien;
    }

    public void setTienDien(int tienDien) {
        this.tienDien = tienDien;
    }

    public int getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(int tienNuoc) {
        this.tienNuoc = tienNuoc;
    }
}
