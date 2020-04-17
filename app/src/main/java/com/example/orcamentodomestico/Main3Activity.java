package com.example.orcamentodomestico;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Main3Activity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    public Main3Activity() {

        ListView etNomeSenha = findViewById(R.id.etNomeSenha);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }


}
