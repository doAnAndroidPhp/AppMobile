package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChonLinhVuc extends AppCompatActivity {

    //private Button btnLinhvuc1, btnLinhvuc2, btnLinhvuc3, btnLinhvuc4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_linh_vuc);

       /* btnLinhvuc1.findViewById(R.id.btn_Linhvuc1);
        btnLinhvuc2.findViewById(R.id.btn_Linhvuc2);
        btnLinhvuc3.findViewById(R.id.btn_Linhvuc3);
        btnLinhvuc4.findViewById(R.id.btn_LichSu);*/
    }

    public void Choi_game(View view) {
        Intent intent = new Intent(this, ChoiGame.class);
        startActivity(intent);
    }
}
