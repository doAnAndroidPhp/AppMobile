package com.txtung.ailatrieuphu;

import java.util.Date;

public class LuotChoi {

    public int getSo_cau() {
        return so_cau;
    }

    public int getSo_diem() {
        return so_diem;
    }

    public String getNgay_gio() {
        return ngay_gio;
    }

    public void setSo_cau(int so_cau) {
        this.so_cau = so_cau;
    }

    public void setSo_diem(int so_diem) {
        this.so_diem = so_diem;
    }

    public void setNgay_gio(String ngay_gio) {
        this.ngay_gio = ngay_gio;
    }

    private String nguoi_choi_id;
    private int so_cau;
    private int so_diem;
    private String ngay_gio;

    public LuotChoi( int so_cau, int so_diem, String ngay_gio) {
        this.so_cau = so_cau;
        this.so_diem = so_diem;
        this.ngay_gio = ngay_gio;
    }
}
