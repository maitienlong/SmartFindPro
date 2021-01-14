package com.poly.smartfindpro.ui.searchProduct.filterProduct;

public class FilterTool {
    private String theLoai;
    private int soLuongNguoi;
    private int gia;
    private String gioiTinh;
    public FilterTool() {
    }

    public FilterTool(String theLoai, int soLuongNguoi, int gia, String gioiTinh) {
        this.theLoai = theLoai;
        this.soLuongNguoi = soLuongNguoi;
        this.gia = gia;
        this.gioiTinh = gioiTinh;
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

}
