package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }

    public void Restar(View view) {
        Intent intent = new Intent(this, ChonLinhVuc.class);
        startActivity(intent);
    }

    public void MainMenu(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
