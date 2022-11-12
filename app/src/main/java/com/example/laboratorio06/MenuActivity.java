package com.example.laboratorio06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void goToGraficoBarrasActivity(View view) {
        Intent graf1Intent = new Intent(this, GraficoBarras.class);
        startActivity(graf1Intent);
    }

    public void goToGraficoCircularActivity(View view) {
        Intent graf2Intent = new Intent(this, GraficoCircular.class);
        startActivity(graf2Intent);
    }
}
