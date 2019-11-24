package com.txtung.ailatrieuphu;

import android.os.Parcel;
import android.os.Parcelable;

public class cls_LinhVuc implements Parcelable {
    private int id;
    private String Ten_linh_vuc;


    protected cls_LinhVuc(Parcel in) {
        id = in.readInt();
        Ten_linh_vuc = in.readString();
    }

    public static final Creator<cls_LinhVuc> CREATOR = new Creator<cls_LinhVuc>() {
        @Override
        public cls_LinhVuc createFromParcel(Parcel in) {
            return new cls_LinhVuc(in);
        }

        @Override
        public cls_LinhVuc[] newArray(int size) {
            return new cls_LinhVuc[size];
        }
    };

    //Khởi tạo không tham số
    public  cls_LinhVuc(){

    }
    //Khởi tạo có tham số
    public cls_LinhVuc(int id, String tenLinhVuc) {
        this.id = id;
        this.Ten_linh_vuc = tenLinhVuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_linh_vuc() {
        return Ten_linh_vuc;
    }

    public void setTen_linh_vuc(String ten_linh_vuc) {
        Ten_linh_vuc = ten_linh_vuc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Ten_linh_vuc);
    }
}
