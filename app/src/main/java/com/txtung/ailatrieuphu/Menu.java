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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.BASE;

public class Menu extends AppCompatActivity {

    //Khai báo biến
   // private ImageView imgAvatar;
    //private ImageButton iBtnCapnhattaikhoan;
    private TextView txtCredit, txtUsername, txtthongbao;
    private String sharedPrefFile = "com.txtung.ailatrieuphu";
    private SharedPreferences mPreferences;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Khởi tạo giá trị
        txtCredit = findViewById(R.id.txt_Credit);
        txtUsername = findViewById(R.id.txt_username);
        txtthongbao = findViewById(R.id.txt_thongbao);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        TextView mToken = findViewById(R.id.txt_token);
        //Lấy token trong sharedPreferences
        String token = mPreferences.getString("TOKEN", "");
        mToken.setText(token);
        cls_TaiKhoan Taikhoan1 = new cls_TaiKhoan();
        LayThongTin();
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
    public void goiAPI(String token) {

        //String token = mPreferences.getString("TOKEN", "");

        new FectAPIToken(){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    txtthongbao.setText(jsonObject.getString("message"));
                    //mThongBao.setText(jsonObject.getString("message"));
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
    public void LayThongTin(){
        final String token = mPreferences.getString("TOKEN", "");
        String url = "http://10.0.2.2:8000/api/lay-thong-tin";
        StringRequest request = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONObject jsonObject = new JSONObject(response);

                        cls_TaiKhoan Taikhoan = new cls_TaiKhoan(jsonObject.getInt("id"),
                                jsonObject.getString("ten_dang_nhap"),
                                "",
                                jsonObject.getString("email"),
                                jsonObject.getString("hinh_dai_dien"),
                                jsonObject.getInt("credit"),
                                jsonObject.getInt("diem_cao_nhat"));
                        txtUsername.setText(jsonObject.getString("ten_dang_nhap"));
                        txtCredit.setText(""+jsonObject.getInt("credit"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(request);
    }
}
