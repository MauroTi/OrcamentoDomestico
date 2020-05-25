package com.example.orcamentodomestico;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Main3Activity extends AppCompatActivity {

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main3);

    TextView tvReceitas = findViewById(R.id.receitaFinal);
    TextView tvDespesas = findViewById(R.id.despesaFinal);
    TextView tvSaldo = findViewById(R.id.saldoFinal);

    Bundle extras = getIntent().getExtras();
    String despesas = extras.getString("TotalDespesas");
    String receitas = extras.getString("TotalReceitas");
    String resultado = extras.getString("Diferenca");

    Double despesaDouble = Double.parseDouble(despesas);
    Double receitaDouble = Double.parseDouble(receitas);
    Double resultadoDouble = Double.parseDouble(resultado);

    DecimalFormat dfDespesas = new DecimalFormat("R$ ,##0.00");
    String saidaDespesas = dfDespesas.format(despesaDouble);
    tvDespesas.setText(saidaDespesas);
    if (despesaDouble < 0) {
      tvDespesas.setBackgroundColor(Color.parseColor("#FF4500"));
    } else {
      tvDespesas.setBackgroundColor(Color.parseColor("#3CB371"));
    }

    if (receitaDouble < 0) {
      tvReceitas.setBackgroundColor(Color.parseColor("#FF4500"));
    } else {
      tvReceitas.setBackgroundColor(Color.parseColor("#3CB371"));
    }

    if (resultadoDouble < 0) {
      tvSaldo.setBackgroundColor(Color.parseColor("#FF4500"));
    } else {
      tvSaldo.setBackgroundColor(Color.parseColor("#3CB371"));
    }

    DecimalFormat dfReceitas = new DecimalFormat("R$ ,##0.00");
    String saidaReceitas = dfReceitas.format(receitaDouble);
    tvReceitas.setText(saidaReceitas);

    DecimalFormat dfSaldo = new DecimalFormat("R$ ,##0.00");
    String saidaSaldo = dfSaldo.format(resultadoDouble);
    tvSaldo.setText(saidaSaldo);
  }

  public void voltar(View view) {
    Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
    startActivity(intent);
  }


  // Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
  // startActivity(intent);
}
