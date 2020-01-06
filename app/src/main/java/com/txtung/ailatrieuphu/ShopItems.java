package com.txtung.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopItems extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private String sharedPrefFile = "com.txtung.ailatrieuphu";
    private SharedPreferences mPreferences;
    public cls_TaiKhoan Taikhoan = new cls_TaiKhoan();
    private Button btn_goi1, btn_goi3, btn_goi4, btn_goi2;
    private  int credit=0;
    private final ArrayList<cls_Credit> mListCredit = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_credit);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        LayThongTin();
        btn_goi1 = findViewById(R.id.btn_goi1);
        btn_goi2 = findViewById(R.id.btn_goi2);
        btn_goi3 = findViewById(R.id.btn_goi3);
        btn_goi4 = findViewById(R.id.btn_goi4);
    }

    public void goi1(View view) {
        credit = mListCredit.get(0).getCredit();
        muacredit();
    }

    public void goi2(View view) {
        credit = mListCredit.get(1).getCredit();
        muacredit();
    }

    public void goi3(View view) {
        credit = mListCredit.get(2).getCredit();
        muacredit();
    }

    public void goi4(View view) {
        credit = mListCredit.get(3).getCredit();
        muacredit();
    }
    public void muacredit(){
        String url = "http://10.0.2.2:8000/api/doi-mat-khau";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {JSONObject objLogin = new JSONObject(response);
                    boolean result = objLogin.getBoolean("status");
                    if (result) {
                        Toast.makeText(getApplicationContext(),  " Giao Dịch Thành Công", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Giao Dịch Thất Bại", Toast.LENGTH_LONG).show();
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
                params.put("credit",""+credit);
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(stringRequest);
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
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new CreditLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //Xử lý dữ liệu
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("linh_vuc");
            for (int i = 0; i < itemsArray.length(); i++) {
                int id = itemsArray.getJSONObject(i).getInt("id");
                String tenGoi = itemsArray.getJSONObject(i).getString("ten_goi");
                int credit = itemsArray.getJSONObject(i).getInt("credit");
                int sotien = itemsArray.getJSONObject(i).getInt("so_tien");
                this.mListCredit.add(new cls_Credit(id, tenGoi, credit, sotien));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //Hiển thị tên lĩnh vực lên các button

        btn_goi1.setText(mListCredit.get(0).getCredit());
        btn_goi2.setText(mListCredit.get(1).getCredit());
        btn_goi3.setText(mListCredit.get(2).getCredit());
        btn_goi4.setText(mListCredit.get(3).getCredit());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
