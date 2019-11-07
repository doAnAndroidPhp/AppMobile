package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChoiGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choi_game);
    }

    public void Shop_items(View view) {
        Intent intent = new Intent(this, ShopItems.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void tro_giup_call(View view) {

    }

    public void tro_giup_khan_gia(View view) {
    }

    public void tro_giup_them_30s(View view) {
    }

    public void tro_giup_5050(View view) {
        Button button = findViewById(R.id.btn_PhuongAnB);
        button.setVisibility(View.INVISIBLE);
        Button button2 = findViewById(R.id.btn_PhuongAnD);
        button2.setVisibility(View.INVISIBLE);
        ImageButton button3 = findViewById(R.id.ibtn_5050);
        button3.setVisibility(View.INVISIBLE);
    }
}
