package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
public class CaiDat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);
    }

    public void mute_music(View view) {
        ImageButton imageButton = findViewById(R.id.imgb_music);
    }

    public void mute_sound(View view) {

    }
}
