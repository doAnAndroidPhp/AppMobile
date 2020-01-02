package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

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

    public void Dangky(View view) {
        final String username = edtUsername.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        final String repass = edtRepassword.getText().toString().trim();
        //Kiểm tra dữ liệu nhập
        if (username.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!password.equals(repass)) {
                Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không chính xác", Toast.LENGTH_SHORT).show();
            } else {
                String url = "http://10.0.2.2:8000/api/dang-ky";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {JSONObject objLogin = new JSONObject(response);
                            boolean result = objLogin.getBoolean("status");
                            if (result) {
                                Toast.makeText(getApplicationContext(),  " Tạo Thành Công", Toast.LENGTH_LONG).show();
                                edtUsername.setText("");
                                edtEmail.setText("");
                                edtPassword.setText("");
                                edtRepassword.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Tài khoản đã được đăng ký", Toast.LENGTH_LONG).show();
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
                        params.put("ten_dang_nhap",edtUsername.getText().toString());
                        params.put("mat_khau",edtPassword.getText().toString());
                        params.put("email",edtEmail.getText().toString());
                        return params;
                    }
                };
                RequestQueue mRequestQueue = Volley.newRequestQueue(this);
                mRequestQueue.add(stringRequest);
    }
    }
}
}
