package com.example.orcamentodomestico;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

public class Main3Activity extends AppCompatActivity {

    private Button btnLogout;

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TextView tvReceitas = findViewById(R.id.receitaFinal);
        TextView tvDespesas = findViewById(R.id.despesaFinal);
        TextView tvSaldo = findViewById(R.id.saldoFinal);
    btnLogout = (Button) findViewById(R.id.btnLogout);

    Bundle extras = getIntent().getExtras();
    String despesas = extras.getString("TotalDespesas");
    String receitas = extras.getString("TotalReceitas");
    String resultado = extras.getString("Diferenca");

    Double despesaDouble = Double.parseDouble(despesas);
    Double receitaDouble = Double.parseDouble(receitas);
    Double resultadoDouble = Double.parseDouble(resultado);

    tvDespesas.setBackgroundResource(R.drawable.rounded_corner_main3_green);
    tvDespesas.setTextColor(Color.BLACK);

    tvReceitas.setBackgroundResource(R.drawable.rounded_corner_main3_green);
    tvReceitas.setTextColor(Color.BLACK);

    if (resultadoDouble < 0) {
      tvSaldo.setBackgroundResource(R.drawable.rounded_corner_main3_red);
      tvSaldo.setTextColor(Color.BLACK);
    } else {
      tvSaldo.setBackgroundResource(R.drawable.rounded_corner_main3_green);
      tvSaldo.setTextColor(Color.BLACK);
    }

    DecimalFormat dfDespesas = new DecimalFormat("R$ ,##0.00");
    String saidaDespesas = dfDespesas.format(despesaDouble);
    tvDespesas.setText(saidaDespesas);

    DecimalFormat dfReceitas = new DecimalFormat("R$ ,##0.00");
    String saidaReceitas = dfReceitas.format(receitaDouble);
    tvReceitas.setText(saidaReceitas);

    DecimalFormat dfSaldo = new DecimalFormat("R$ ,##0.00");
    String saidaSaldo = dfSaldo.format(resultadoDouble);
    tvSaldo.setText(saidaSaldo);

        btnLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        disconnect();
                    }
                });
  }

  public void voltar(View view) {
    Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
    startActivity(intent);
  }

  public void sair(View view) {
    finishAffinity();
  }

  private void disconnect() {
      FirebaseAuth.getInstance().signOut();
      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
      startActivity(intent);
      Toast.makeText(getApplicationContext(), "Logout efetuado com sucesso!", Toast.LENGTH_LONG)
              .show();
  }
}

  // Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
  // startActivity(intent);
