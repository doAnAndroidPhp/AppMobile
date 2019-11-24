package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

   /* // Khai báo biến
    private Button btnDangnhap, btnDângky;
    private EditText EdtUsername;
    private EditText EdtPassword;
    private ImageButton iBtnGoogle, iBtnFace, iBtnTwiter;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*//Khởi tạo giá trị
        btnDangnhap = findViewById(R.id.btn_DangNhap);
        btnDângky = findViewById(R.id.btn_Dangky);
        EdtUsername = findViewById(R.id.Edt_UserName);
        iBtnFace = findViewById(R.id.iBtn_face);
        iBtnGoogle = findViewById(R.id.iBtn_google);
        iBtnTwiter = findViewById(R.id.iBtn_twiter);*/
    }

    public void Login(View view) {
        Intent intent = new Intent(this, Menu.class);
        //intent.putExtra("ID", Integer.parseInt(itemTag));
        startActivity(intent);
    }

    public void DagKy(View view) {
        Intent intent = new Intent(this, DangKy.class);
        startActivity(intent);
    }

   /* public EditText getEdtPassword() {
        return EdtPassword;
    }

    public void setEdtPassword(EditText edtPassword) {
        EdtPassword = edtPassword;
    }*/
}
