package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
