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

    private Button Linh_vuc_a, Linh_vuc_b, Linh_vuc_c, Linh_vuc_d;
    private final ArrayList<cls_LinhVuc> mListLinhVuc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_linh_vuc);

        //Khai báo biến
        Linh_vuc_a = findViewById(R.id.btn_Linhvuca);
        Linh_vuc_b = findViewById(R.id.btn_Linhvucb);
        Linh_vuc_c = findViewById(R.id.btn_Linhvucc);
        Linh_vuc_d = findViewById(R.id.btn_Linhvucd);

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

        Linh_vuc_a.setText(mListLinhVuc.get(1).getTen_linh_vuc());
        Linh_vuc_b.setText(mListLinhVuc.get(2).getTen_linh_vuc());
        Linh_vuc_c.setText(mListLinhVuc.get(3).getTen_linh_vuc());
        Linh_vuc_d.setText(mListLinhVuc.get(0).getTen_linh_vuc());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
