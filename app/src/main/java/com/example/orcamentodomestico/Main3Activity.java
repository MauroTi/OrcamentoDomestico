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
        Float despesas = extras.getFloat("TotalDespesas");
        Float receitas = extras.getFloat("TotalReceitas");
        Float resultado = extras.getFloat("Diferenca");


        Toast.makeText(Main3Activity.this, "Receitas " + receitas, Toast.LENGTH_SHORT).show();
        Toast.makeText(Main3Activity.this, "Despesas " + despesas, Toast.LENGTH_SHORT).show();
        Toast.makeText(Main3Activity.this, "Total " + resultado, Toast.LENGTH_SHORT).show();
    }
}