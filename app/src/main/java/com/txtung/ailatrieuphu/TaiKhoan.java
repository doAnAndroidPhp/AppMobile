package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class TaiKhoan extends AppCompatActivity {

    private String sharedPrefFile = "com.txtung.ailatrieuphu";
    private SharedPreferences mPreferences;
    public cls_TaiKhoan Taikhoan = new cls_TaiKhoan();
    private TextView txtUsername, txtCredit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);
        txtCredit = findViewById(R.id.txt_Credit);
        txtUsername=findViewById(R.id.txt_Usrname);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        LayThongTin();
    }

    public void Doi_Mat_Khau(View view) {
        Intent intent = new Intent(this, DoiMatKhau.class);
        startActivity(intent);
    }

    public void LichSu(View view) {
        Intent intent = new Intent(this, LichSuChoiGame.class);
        intent.putExtra("id", Taikhoan.getId());
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
                    Taikhoan.setId(jsonObject.getInt("id"));
                    Taikhoan.setId(jsonObject.getInt("id"));
                    Taikhoan.setTen_dang_nhap(jsonObject.getString("ten_dang_nhap"));
                    Taikhoan.setEmail(jsonObject.getString("email"));
                    Taikhoan.setCredit(jsonObject.getInt("credit"));
                    Taikhoan.setDiem_cao_nhat(jsonObject.getInt("diem_cao_nhat"));
                    txtUsername.setText(Taikhoan.getTen_dang_nhap());
                    txtCredit.setText(""+Taikhoan.getCredit());
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
