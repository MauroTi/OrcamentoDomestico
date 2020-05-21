package com.example.orcamentodomestico;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle extras = getIntent().getExtras();
        String despesas = extras.getString("TotalDespesas");
        String receitas = extras.getString("TotalReceitas");
        String resultado = extras.getString("Diferenca");


        Toast.makeText(Main3Activity.this, "Receitas " + receitas, Toast.LENGTH_SHORT).show();
        Toast.makeText(Main3Activity.this, "Despesas " + despesas, Toast.LENGTH_SHORT).show();
        Toast.makeText(Main3Activity.this, "Saldo " + resultado, Toast.LENGTH_SHORT).show();
    }
}