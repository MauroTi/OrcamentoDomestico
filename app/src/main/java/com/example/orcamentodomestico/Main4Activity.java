package com.example.orcamentodomestico;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Main4Activity extends AppCompatActivity {

  DatabaseReference dbreference; // banco

  private Button btnLogout;
  TextView txtData;
  ArrayList<Item> listaDespesas;
  ArrayList<Item> listaReceitas;

  public boolean onCreateOptionsMenu(android.view.Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);

    return true;
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main4);

    dbreference = FirebaseDatabase.getInstance().getReference("totais"); // banco

    // Data do sistema
    txtData = findViewById(R.id.data);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    txtData.setText(sdf.format(new Date()));
    // Final data do sistema

    TextView tvReceitas = findViewById(R.id.receitaFinal);
    TextView tvDespesas = findViewById(R.id.despesaFinal);
    TextView tvSaldo = findViewById(R.id.saldoFinal);
    btnLogout = findViewById(R.id.btnLogout);

    Bundle extras = getIntent().getExtras();
    String despesas = extras.getString("TotalDespesas");
    String receitas = extras.getString("TotalReceitas");
    String resultado = extras.getString("Diferenca");

    // Pega lista serializada

    listaDespesas = (ArrayList<Item>) getIntent().getSerializableExtra("DespesasDiscriminadas");
    listaReceitas = (ArrayList<Item>) getIntent().getSerializableExtra("ReceitasDiscriminadas");

    // Popula listas despesas e receitas finais
    ListView listaRelatDespesas = findViewById(R.id.listaRelatDespesas);
    ArrayAdapter adapterDespesas = new ListAdapterItemRelatorio(this, listaDespesas);
    listaRelatDespesas.setAdapter(adapterDespesas);

    ListView listaRelatReceitas = findViewById(R.id.listaRelatReceitas);
    ArrayAdapter adapterReceitas = new ListAdapterItemRelatorio(this, listaReceitas);
    listaRelatReceitas.setAdapter(adapterReceitas);
    // Final popula listas despesas e receitas finais

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

    String id = dbreference.push().getKey(); // banco
    dbreference.child(id).setValue(saidaSaldo); // banco


  }

  // Preencher lista despesas
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private ArrayList<Item> preencherDespesas() {
    ArrayList<Item> dadosDespesas = new ArrayList<>();
    for (int i = 0; i < Objects.requireNonNull(listaDespesas).size(); i++) {
      Item itemDespesas = listaDespesas.get(i);
    }
    return dadosDespesas;
  }

  // Final preencher lista despesas

  // Preencher lista receitas

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private ArrayList<String> preencherReceitas() {
    ArrayList<String> dadosReceitas = new ArrayList<>();
    for (int i = 0; i < Objects.requireNonNull(listaReceitas).size(); i++) {
      Item itemReceitas = listaReceitas.get(i);
    }
    return dadosReceitas;
  }

  // Final preencher lista receitas

  public void voltar(View view) {
    Intent intent = new Intent(Main4Activity.this, Main2Activity.class);
    startActivity(intent);
  }

  public void sair(View view) {
    new AlertDialog.Builder(this)
            .setTitle("Sair do App?")
            .setMessage("Tem certeza que deseja sair do aplicativo?")
            .setPositiveButton(
                    "sim",
                    new DialogInterface.OnClickListener() {

                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {

                        finishAffinity();
                      }
                    })
            .setNegativeButton("não", null)
            .show();


  }

  // Botoes barra app
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.logout) {
      new AlertDialog.Builder(this)
              .setTitle("Efetuar logout?")
              .setMessage("Tem certeza que deseja efetuar logout?")
              .setPositiveButton(
                      "sim",
                      new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                          disconnect();
                        }
                      })
              .setNegativeButton("não", null)
              .show();
      return true;
    } else if (id == R.id.sair) {
      new AlertDialog.Builder(this)
              .setTitle("Sair do App?")
              .setMessage("Tem certeza que deseja sair do aplicativo?")
              .setPositiveButton(
                      "sim",
                      new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                          finish();
                        }
                      })
              .setNegativeButton("não", null)
              .show();
    }
    return super.onOptionsItemSelected(item);
  }

  private void disconnect() {
    FirebaseAuth.getInstance().signOut();
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    startActivity(intent);
    Toast.makeText(getApplicationContext(), "Logout efetuado com sucesso!", Toast.LENGTH_LONG)
            .show();
  }

  public void logout(View view) {

    new AlertDialog.Builder(this)
            .setTitle("Efetuar logout?")
            .setMessage("Tem certeza que deseja efetuar logout?")
            .setPositiveButton(
                    "sim",
                    new DialogInterface.OnClickListener() {

                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                        disconnect();

                      }
                    })
            .setNegativeButton("não", null)
            .show();
  }
}

