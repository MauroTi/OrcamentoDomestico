package com.example.orcamentodomestico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    public void cria_despesa(View view) {


    }

    public void proxima(View view) {
        Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
        startActivity(intent);

    }
}

