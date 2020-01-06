package com.txtung.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChoiGame extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private Button btn_Caua, btn_Caub, btn_Cauc, btn_Caud;
    private TextView txt_Noidung, txt_Time, txt_Mang, txt_Cau, txt_Credit, txt_Ten;
    private final ArrayList<cls_CauHoi> cauHoiArrayList = new ArrayList<>();
    private int iDemsocau=0, iMang = 5, id_linh_vuc;
    private CountDownTimer timer;
    private String sharedPrefFile = "com.txtung.ailatrieuphu";
    private SharedPreferences mPreferences;
    public cls_TaiKhoan Taikhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choi_game);

        btn_Caua = findViewById(R.id.btn_CauA);
        btn_Caub = findViewById(R.id.btn_CauB);
        btn_Cauc = findViewById(R.id.btn_CauC);
        btn_Caud = findViewById(R.id.btn_CauD);
        txt_Noidung = findViewById(R.id.txt_Cauhoi);
        txt_Time = findViewById(R.id.txt_Time);
        txt_Ten = findViewById(R.id.txt_Name);
        txt_Credit = findViewById(R.id.txt_Credit);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        LayThongTin();


        txt_Mang = findViewById(R.id.txt_Mang);
        txt_Mang.setText("X "+ iMang);
        txt_Cau = findViewById(R.id.txt_Cau);
        txt_Cau.setText(""+1);
        Intent intent = getIntent();
        id_linh_vuc = intent.getIntExtra("id_linh_vuc",0);

        //Đếm thời gian
        timer=new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long l) {
                txt_Time.setText("" + l/1000);
            }

            @Override
            public void onFinish() {
                txt_Time.setText("Hết giờ!");
                GameOver();
            }
        }.start();

        //Khởi tạo lại loader nếu loader đã tồn tại
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0, null, this);
        }
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    public void Shop_items(View view) {
        Intent intent = new Intent(this, ShopItems.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        id = id_linh_vuc;
        return new CauHoiLoader(this, id);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //Xử lý dữ liệu
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("cau_hoi");
            for (int i = 0; i < itemsArray.length(); i++) {
                int id = itemsArray.getJSONObject(i).getInt("id");
                int linhvucid = itemsArray.getJSONObject(i).getInt("linh_vuc_id");
                String noiDung = itemsArray.getJSONObject(i).getString("noi_dung");
                String phuong_an_a = itemsArray.getJSONObject(i).getString("phuong_an_a");
                String phuong_an_b = itemsArray.getJSONObject(i).getString("phuong_an_b");
                String phuong_an_c = itemsArray.getJSONObject(i).getString("phuong_an_c");
                String phuong_an_d = itemsArray.getJSONObject(i).getString("phuong_an_d");
                String dap_an = itemsArray.getJSONObject(i).getString("dap_an");
                this.cauHoiArrayList.add(new cls_CauHoi(id, linhvucid, noiDung, phuong_an_a, phuong_an_b, phuong_an_c, phuong_an_d, dap_an));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        //Hiển thị câu hỏi lên các button
        iDemsocau = 0;
        txt_Noidung.setText(cauHoiArrayList.get(0).getNoi_dung());
        btn_Caua.setText(cauHoiArrayList.get(0).getPhuong_an_a());
        btn_Caub.setText(cauHoiArrayList.get(0).getPhuong_an_b());
        btn_Cauc.setText(cauHoiArrayList.get(0).getPhuong_an_c());
        btn_Caud.setText(cauHoiArrayList.get(0).getPhuong_an_d());
        }
    //CÁC QUYỀN TRỢ GIÚP
    public void tro_giup_call(View view) {

    }

    public void tro_giup_khan_gia(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_bieu_do_tro_giup_khan_gia);
        BarChart barChart = dialog.findViewById(R.id.barchart);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(1200,1000);

        int a, b, c,d;
        Random random = new Random();
        a= random.nextInt(100);
        b = random.nextInt(100-a);
        c = random.nextInt(100-a-b);
        d = 100-a-b-c;

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(a, 0));
        entries.add(new BarEntry(b, 1));
        entries.add(new BarEntry(c, 2));
        entries.add(new BarEntry(d, 3));

        BarDataSet bardataset = new BarDataSet(entries, "");

        ArrayList<String> labels = new ArrayList();
        labels.add("A");
        labels.add("B");
        labels.add("C");
        labels.add("D");

        BarData data = new BarData(labels, bardataset);
        data.setValueTextSize(30);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(30);

        //Khong ve luoi tren bieu do
        xAxis.setDrawGridLines(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        //Zoom bieu do
        barChart.setDoubleTapToZoomEnabled(true);

        // set the data and list of labels into chart
        barChart.setData(data);
        // set the description
        barChart.setDescription("");
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(3000);

        dialog.show();

        ImageButton button = findViewById(R.id.ibtn_khangia);
        button.setVisibility(View.INVISIBLE);
    }

    public void tro_giup_them_30s(View view) {
        int giay = Integer.parseInt(txt_Time.getText().toString());
        giay +=30000;
        timer.cancel();
        timer=new CountDownTimer(giay, 1000) {

            @Override
            public void onTick(long l) {
                txt_Time.setText("" + l/1000);
            }

            @Override
            public void onFinish() {
                txt_Time.setText("Hết giờ!");
                GameOver();
            }
        }.start();
        ImageButton button3 = findViewById(R.id.ibtn_30s);
        button3.setVisibility(View.INVISIBLE);
    }

    public void tro_giup_5050(View view) {
        String a = btn_Caua.getText().toString();
        String b = btn_Caub.getText().toString();
        String c = btn_Cauc.getText().toString();
        String d = btn_Caud.getText().toString();
        String e = cauHoiArrayList.get(iDemsocau).getDapan();
            if(!a.equals(e)&&!b.equals(e)){
                btn_Caua.setVisibility(View.INVISIBLE);
                btn_Caub.setVisibility(View.INVISIBLE);
            }
        else if(!a.equals(e)&&!c.equals(e)){
            btn_Caua.setVisibility(View.INVISIBLE);
            btn_Cauc.setVisibility(View.INVISIBLE);
        }
        else if(!a.equals(e)&&!d.equals(e)){
            btn_Caua.setVisibility(View.INVISIBLE);
            btn_Caud.setVisibility(View.INVISIBLE);
        }
        else if(!b.equals(e)&&!c.equals(e)){
            btn_Caub.setVisibility(View.INVISIBLE);
            btn_Cauc.setVisibility(View.INVISIBLE);
        }
            else if(!b.equals(e)&&!d.equals(e)){
                btn_Caub.setVisibility(View.INVISIBLE);
                btn_Caud.setVisibility(View.INVISIBLE);
            }
            else if(!c.equals(e)&&!d.equals(e)){
                btn_Cauc.setVisibility(View.INVISIBLE);
                btn_Caud.setVisibility(View.INVISIBLE);
            }
        ImageButton button3 = findViewById(R.id.ibtn_5050);
        button3.setVisibility(View.INVISIBLE);
    }

    public void tro_giup_DoiCauHoi(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.tro_giup_doi_cau_hoi);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(1200,1000);
        //dialog.show();
        Button btn_ok = dialog.findViewById(R.id.btn_credit);
        Button btn_huy = dialog.findViewById(R.id.btn_Huy);
        //btn_ok.setOnClickListener();
        iDemsocau +=1;

        if(iDemsocau< cauHoiArrayList.size()){
            txt_Noidung.setText(cauHoiArrayList.get(iDemsocau).getNoi_dung());
            btn_Caua.setText(cauHoiArrayList.get(iDemsocau).getPhuong_an_a());
            btn_Caub.setText(cauHoiArrayList.get(iDemsocau).getPhuong_an_b());
            btn_Cauc.setText(cauHoiArrayList.get(iDemsocau).getPhuong_an_c());
            btn_Caud.setText(cauHoiArrayList.get(iDemsocau).getPhuong_an_d());
        }
    }
    public void DoiCauHoi(){
        btn_Caua.setVisibility(View.VISIBLE);
        btn_Caub.setVisibility(View.VISIBLE);
        btn_Cauc.setVisibility(View.VISIBLE);
        btn_Caud.setVisibility(View.VISIBLE);
        iDemsocau +=1;
        int i = iDemsocau;
        i+=1;
        txt_Cau.setText(""+i);
        timer.cancel(); // cancel
        timer.start();  // then restart
        if(iDemsocau<= cauHoiArrayList.size()){
            txt_Noidung.setText(cauHoiArrayList.get(iDemsocau).getNoi_dung());
            btn_Caua.setText(cauHoiArrayList.get(iDemsocau).getPhuong_an_a());
            btn_Caub.setText(cauHoiArrayList.get(iDemsocau).getPhuong_an_b());
            btn_Cauc.setText(cauHoiArrayList.get(iDemsocau).getPhuong_an_c());
            btn_Caud.setText(cauHoiArrayList.get(iDemsocau).getPhuong_an_d());
    }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void LayThongTin(){
        final String token = mPreferences.getString("TOKEN", "");
        String url = "http://10.0.2.2:8000/api/lay-thong-tin";
        StringRequest request = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Taikhoan = new cls_TaiKhoan(jsonObject.getInt("id"),
                            jsonObject.getString("ten_dang_nhap"),
                            "",
                            jsonObject.getString("email"),
                            jsonObject.getString("hinh_dai_dien"),
                            jsonObject.getInt("credit"),
                            jsonObject.getInt("diem_cao_nhat"));
                    txt_Ten.setText(jsonObject.getString("ten_dang_nhap"));
                    txt_Credit.setText(""+jsonObject.getInt("credit"));
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
    public void TraLoiSai(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_tra_loi_sai);
        dialog.getWindow().setLayout(900,1200);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }
    public void GameOver(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_game_over);
        TextView txt_Score = dialog.findViewById(R.id.txt_Score);
        TextView txt_Best = dialog.findViewById(R.id.txt_Bestscore);
        Button btn_restar = dialog.findViewById(R.id.btn_Restar);
        Button btn_menu = dialog.findViewById(R.id.btn_Mainmenu);
        //btn_restar.setOnClickListener();
        txt_Score.setText(""+iDemsocau+"0");
        int diem = iDemsocau;
        diem = diem*10;
        if(diem > Taikhoan.getDiem_cao_nhat()){
            txt_Best.setText(""+iDemsocau+"0");
        }
        else{
            txt_Best.setText(""+Taikhoan.getDiem_cao_nhat());
        }
        LuuLuotChoi();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void LuuLuotChoi(){
        final java.util.Date date=new java.util.Date();
        String url = "http://10.0.2.2:8000/api/luu-luot-choi";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {JSONObject objLogin = new JSONObject(response);
                    boolean result = objLogin.getBoolean("status");
                    if (result) {
                        Toast.makeText(getApplicationContext(),  " Lưu thành công", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Lưu thất bại", Toast.LENGTH_LONG).show();
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
                params.put("nguoi_choi_id",""+Taikhoan.getId());
                params.put("so_cau",""+iDemsocau);
                params.put("diem",""+iDemsocau+"0");
                params.put("ngay_gio",""+date);
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(stringRequest);
    }
    public void btn_CauA(View view) {
        String a = btn_Caua.getText().toString();
        String b = cauHoiArrayList.get(iDemsocau).getDapan();
        if(a.equals(b)&& iDemsocau<14){
            DoiCauHoi();
        }
        else if(!a.equals(b)){
            if(iMang>0){
                iMang--;
                txt_Mang.setText("x" + iMang);
                TraLoiSai();
            }
            else if(iMang==0){
                GameOver();
            }
        }
        else if(a.equals(b)&& iDemsocau==14){
            GameOver();
        }
    }
    public void btn_CauB(View view) {
        String a = btn_Caub.getText().toString();
        String b = cauHoiArrayList.get(iDemsocau).getDapan();
        if(a.equals(b)&& iDemsocau<14){
            DoiCauHoi();
        }
        else if(!a.equals(b)){
            if(iMang>0){
                iMang--;
                txt_Mang.setText("x" + iMang);
                TraLoiSai();
            }
            else if(iMang==0){
                GameOver();
            }
        }
        else if(a.equals(b)&& iDemsocau==14){
            GameOver();
        }
    }
    public void btn_CauC(View view) {
        String a = btn_Cauc.getText().toString();
        String b = cauHoiArrayList.get(iDemsocau).getDapan();
        if(a.equals(b)&& iDemsocau<14){
            DoiCauHoi();
        }
        else if(!a.equals(b)){
            if(iMang>0){
                iMang--;
                txt_Mang.setText("x" + iMang);
                TraLoiSai();
            }
            else if(iMang==0){
                GameOver();
            }
        }
        else if(a.equals(b)&& iDemsocau==14){
            GameOver();
        }
    }
    public void btn_CauD(View view) {
        String a = btn_Caud.getText().toString();
        String b = cauHoiArrayList.get(iDemsocau).getDapan();
        if(a.equals(b)&& iDemsocau<14){
            DoiCauHoi();
        }
        else if(!a.equals(b)){
            if(iMang>0){
                iMang--;
                txt_Mang.setText("x" + iMang);
                TraLoiSai();
            }
            else if(iMang==0){
                GameOver();
            }
        }
        else if(a.equals(b)&& iDemsocau==14){
            GameOver();
        }
    }

}
