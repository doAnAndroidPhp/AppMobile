package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity {

    //Khai báo biến
   // private ImageView imgAvatar;
    //private ImageButton iBtnCapnhattaikhoan;
    private TextView txtCredit, txtUsername;
    private String sharedPrefFile = "com.txtung.ailatrieuphu";
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Khởi tạo giá trị
        txtCredit = findViewById(R.id.txt_Credit);
        txtUsername = findViewById(R.id.txt_username);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        TextView mToken = findViewById(R.id.txt_token);
        //Lấy token trong sharedPreferences
        String token = mPreferences.getString("TOKEN", "");
        String user;
        mToken.setText(token);
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
            // Xóa token trong SharedPreferences
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }
    public void goiAPI(View view) {

        String token = mPreferences.getString("TOKEN", null);

        new FectAPIToken() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    txtUsername.setText(jsonObject.getString("ten_dang_nhap"));
                    txtCredit.setText(jsonObject.getString("credit"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute("lay-thong-tin", "GET", token);
    }
    public void Tai_Khoan(View view) {
        Intent intent = new Intent(this, TaiKhoan.class);
        startActivity(intent);
    }
}
