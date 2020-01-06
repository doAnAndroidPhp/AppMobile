package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class DoiMatKhau extends AppCompatActivity {

    private String sharedPrefFile = "com.txtung.ailatrieuphu";
    private SharedPreferences mPreferences;
    public cls_TaiKhoan Taikhoan = new cls_TaiKhoan();
    private TextView txtUsername, txtCredit;
    private EditText edtNewpass,edtPassword, edtRepassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        txtCredit = findViewById(R.id.txt_Credit);
        txtUsername=findViewById(R.id.txt_Username);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        edtNewpass = findViewById(R.id.txt_newpass);
        edtPassword = findViewById(R.id.txt_password);
        edtRepassword = findViewById(R.id.txt_repass);
        LayThongTin();
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

    public void DoiMatKhau(View view) {
        final String pass = edtPassword.getText().toString().trim();
        final String newpass = edtNewpass.getText().toString().trim();
        final String repass = edtRepassword.getText().toString().trim();

        if (pass.equals("") || newpass.equals("") || repass.equals("")) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!repass.equals(newpass)) {
                Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không chính xác", Toast.LENGTH_SHORT).show();
            } else {
                String url = "http://10.0.2.2:8000/api/doi-mat-khau";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {JSONObject objLogin = new JSONObject(response);
                            boolean result = objLogin.getBoolean("status");
                            if (result) {
                                Toast.makeText(getApplicationContext(),  " Đổi Mật Khẩu Thành Công", Toast.LENGTH_LONG).show();
                                edtNewpass.setText("");
                                edtPassword.setText("");
                                edtRepassword.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Đổi Mật Khẩu Thất Bại", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server không phản hồi", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id",""+Taikhoan.getId());
                        params.put("mat_khau",edtNewpass.getText().toString());
                        return params;
                    }
                };
                RequestQueue mRequestQueue = Volley.newRequestQueue(this);
                mRequestQueue.add(stringRequest);
            }
    }
}
}
