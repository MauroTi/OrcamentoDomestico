package com.example.orcamentodomestico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    String nomeDespesa;
    Float valorDespesa;

    public void cria_despesa(View view) {
        EditText etNomeDespesa = findViewById(R.id.edNomeDespesa);
        nomeDespesa = etNomeDespesa.getText().toString();
        EditText etValorDespesa = findViewById(R.id.edValorDespesa);
        valorDespesa = etValorDespesa.getAlpha();


    }

    public void proxima(View view) {
        Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
        startActivity(intent);
    }


}


