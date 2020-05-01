package com.example.orcamentodomestico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }


    public void cria_receita(View view) {
    }

    public void proxima(View view) {
        Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
        startActivity(intent);
    }


}
