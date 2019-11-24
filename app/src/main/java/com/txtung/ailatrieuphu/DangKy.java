package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class DangKy extends AppCompatActivity {

    private Button btnDangky, btnHuy;
    EditText edtUsername, edtPassword, edtRepassword, edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        edtUsername = findViewById(R.id.edt_Username);
        edtPassword = findViewById(R.id.edt_Password);
        edtRepassword = findViewById(R.id.edt_Repassword);
        edtEmail = findViewById(R.id.edt_Email);
        btnDangky = findViewById(R.id.btn_Dangky);
        btnHuy = findViewById(R.id.btn_Huy);
    }
}
