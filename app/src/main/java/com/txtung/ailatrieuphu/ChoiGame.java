package com.txtung.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
import java.util.Random;

public class ChoiGame extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private Button btn_Caua, btn_Caub, btn_Cauc, btn_Caud;
    private TextView txt_Noidung, txt_Time;
    private final ArrayList<cls_CauHoi> cauHoiArrayList = new ArrayList<>();
    private int iDemsocau;
    private CountDownTimer timer;
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

        timer=new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long l) {
                txt_Time.setText("" + l/1000);
            }

            @Override
            public void onFinish() {
                txt_Time.setText("0");
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
        return new CauHoiLoader(this);
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
        iDemsocau = 0;
        //Hiển thị câu hỏi lên các button
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

    }

    public void tro_giup_them_30s(View view) {
    }

    public void tro_giup_5050(View view) {
        Button button = findViewById(R.id.btn_CauA);
        button.setVisibility(View.INVISIBLE);
        Button button2 = findViewById(R.id.btn_CauB);
        button2.setVisibility(View.INVISIBLE);
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
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
