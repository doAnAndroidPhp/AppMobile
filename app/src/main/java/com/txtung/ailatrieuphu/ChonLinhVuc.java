package com.txtung.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChonLinhVuc extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private Button Linh_vuc_1, Linh_vuc_2, Linh_vuc_3, Linh_vuc_4;
    private final ArrayList<cls_LinhVuc> mListLinhVuc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_linh_vuc);

        //Khai báo biến
        Linh_vuc_1 = findViewById(R.id.btn_Linhvuc1);
        Linh_vuc_2 = findViewById(R.id.btn_Linhvuc2);
        Linh_vuc_3 = findViewById(R.id.btn_Linhvuc3);
        Linh_vuc_4 = findViewById(R.id.btn_Linhvuc4);

        //Khởi tạo lại loader nếu loader đã tồn tại
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0, null, this);
        }
        getSupportLoaderManager().restartLoader(0, null, this);

    }

    //Chuyển sang giao diện chơi game
    public void Choi_game(View view) {
        Intent intent = new Intent(this, ChoiGame.class);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new LinhVucLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //Xử lý dữ liệu
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("linh_vuc");
            for (int i = 0; i < itemsArray.length(); i++) {
                int id = itemsArray.getJSONObject(i).getInt("id");
                String tenLinhVuc = itemsArray.getJSONObject(i).getString("ten_linh_vuc");
                this.mListLinhVuc.add(new cls_LinhVuc(id, tenLinhVuc));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //Hiển thị tên lĩnh vực lên các button
        Linh_vuc_1.setText(mListLinhVuc.get(1).getTen_linh_vuc());
        Linh_vuc_2.setText(mListLinhVuc.get(3).getTen_linh_vuc());
        Linh_vuc_3.setText(mListLinhVuc.get(3).getTen_linh_vuc());
        Linh_vuc_4.setText(mListLinhVuc.get(4).getTen_linh_vuc());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
