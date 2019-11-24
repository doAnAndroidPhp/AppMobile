package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    //Khai báo biến
   // private Button BtnChoingay, BtnCaidat, BtnHuongdan, BtnDangxuat;
   // private ImageView imgAvatar;
    //private ImageButton iBtnCapnhattaikhoan;
   // private TextView txtCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Khởi tạo giá trị
        /*BtnChoingay = findViewById(R.id.btn_Choingay);
        BtnDangxuat = findViewById(R.id.btn_DangXuat);
       imgAvatar = findViewById(R.id.img_avata);
        iBtnCapnhattaikhoan = findViewById(R.id.iBtn_Capnhattaikhoan);
        txtCredit = findViewById(R.id.txt_Credit);*/
    }

    public void Choi_Ngay(View view) {
        Intent intent = new Intent(this, ChonLinhVuc.class);
        startActivity(intent);
    }

    public void Huong_Dan(View view) {
        Intent intent = new Intent(this, HuongDan.class);
        startActivity(intent);
    }

    public void CaiDat(View view) {
        Intent intent = new Intent(this, CaiDat.class);
        startActivity(intent);
    }

    public void DangXuat(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Tai_Khoan(View view) {
        Intent intent = new Intent(this, TaiKhoan.class);
        startActivity(intent);
    }
}
