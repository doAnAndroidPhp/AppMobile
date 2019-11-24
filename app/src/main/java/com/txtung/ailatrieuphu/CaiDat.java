package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
public class CaiDat extends AppCompatActivity {

    //khai báo biến
    private ImageButton iBtnMusic, iBtnSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);
        iBtnSound = findViewById(R.id.iBtn_Sound);
        iBtnMusic= findViewById(R.id.iBtn_Music);
    }

    public void mute_music(View view) {
        Drawable.ConstantState cs1 = iBtnMusic.getDrawable().getConstantState();
        Drawable.ConstantState cs2 = getResources().getDrawable(R.drawable.music_icon).getConstantState();
        if(cs1 == cs2){
            iBtnMusic.setImageResource(R.drawable.music_mute);
        }
        else {
            iBtnMusic.setImageResource(R.drawable.music_icon);
        }
    }

    public void mute_sound(View view) {

        Drawable.ConstantState cs1 = iBtnSound.getDrawable().getConstantState();
        Drawable.ConstantState cs2 = getResources().getDrawable(R.drawable.sound).getConstantState();
        if(cs1 == cs2){
            iBtnSound.setImageResource(R.drawable.sound_mute);
        }
        else {
            iBtnSound.setImageResource(R.drawable.sound);
        }
    }
}
