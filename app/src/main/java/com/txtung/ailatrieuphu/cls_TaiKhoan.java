package com.txtung.ailatrieuphu;

public class cls_TaiKhoan {
    private int id;
    private String ten_dang_nhap;
    private String mat_khau;
    private String hinh_dai_dien;
    private String email;
    private int credit;
    private int diem_cao_nhat;

    public cls_TaiKhoan(){}
    public cls_TaiKhoan(int id, String ten_dang_nhap, String mat_khau, String hinh_dai_dien, String email, int credit, int diem_cao_nhat) {
        this.id = id;
        this.ten_dang_nhap = ten_dang_nhap;
        this.mat_khau = mat_khau;
        this.hinh_dai_dien = hinh_dai_dien;
        this.credit = credit;
        this.diem_cao_nhat = diem_cao_nhat;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_dang_nhap() {
        return ten_dang_nhap;
    }

    public void setTen_dang_nhap(String ten_dang_nhap) {
        this.ten_dang_nhap = ten_dang_nhap;
    }

    public String getMat_khau() {
        return mat_khau;
    }

    public void setMat_khau(String mat_khau) {
        this.mat_khau = mat_khau;
    }

    public String getHinh_dai_dien() {
        return hinh_dai_dien;
    }

    public void setHinh_dai_dien(String hinh_dai_dien) {
        this.hinh_dai_dien = hinh_dai_dien;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDiem_cao_nhat() {
        return diem_cao_nhat;
    }

    public void setDiem_cao_nhat(int diem_cao_nhat) {
        this.diem_cao_nhat = diem_cao_nhat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
