package com.txtung.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Login(View view) {
        Intent intent = new Intent(this, Menu.class);
        //intent.putExtra("ID", Integer.parseInt(itemTag));
        startActivity(intent);
    }

    public void DagKy(View view) {
        Intent intent = new Intent(this, DangKy.class);
        startActivity(intent);
    }
}
