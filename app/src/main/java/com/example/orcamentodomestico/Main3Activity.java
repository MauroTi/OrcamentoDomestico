package com.example.orcamentodomestico;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity {

  DatabaseReference dbreference; // banco

  // criação do adapter e da lista
  ListAdapterItem adapter;
  ArrayList<Item> listaItens;
  // fim criação adapter e lista

  int testeAdd;
  String valor = String.valueOf(0);
  Float despesas;
  Float receitas = Float.valueOf(0);
  Float diferenca = Float.valueOf(0);
  Float pegaValor;
  private FirebaseAuth mAuth;

  // controles da tela
  EditText txtReceita;
  EditText txtValorReceita;
  Button btnAdicionar;
  ListView minhaLista;
  MonetaryMask monetaryMask;
  TextView tvDespesaTotal;
  // fim dos controles da tela

  public boolean onCreateOptionsMenu(android.view.Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);

    return true;
  }

  @SuppressLint("WrongViewCast")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main3);

    dbreference = FirebaseDatabase.getInstance().getReference("receitas"); // banco

    // Inicializa Firebase Auth
    mAuth = FirebaseAuth.getInstance();
    // Verifica se usuario esta logado
    FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {

    } else {
      Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
      startActivity(intent);
      finish();
    }

    final Context context = this;
    listaItens = new ArrayList<Item>();
    adapter = new ListAdapterItem(context, listaItens);

    minhaLista = findViewById(R.id.minhaLista);
    minhaLista.setAdapter(adapter);
    txtReceita = findViewById(R.id.receita);
    txtValorReceita = findViewById(R.id.valorReceita);

    tvDespesaTotal = findViewById(R.id.tvDespesaTotal);
    monetaryMask = new MonetaryMask(this.txtValorReceita);
    btnAdicionar = findViewById(R.id.btnAdicionarReceita);

    Locale mLocale = new Locale("pt", "BR");
    txtValorReceita.addTextChangedListener(new MonetaryMask(txtValorReceita, mLocale));

    btnAdicionar.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                // txtValorReceita.setText("0");
                String despesa = txtReceita.getText().toString();
                valor = txtValorReceita.getText().toString();
                Item novoItem = new Item(despesa, valor);
                adapter.add(novoItem);
                adapter.notifyDataSetChanged();
                txtReceita.setText("");
                txtValorReceita.setText("0");

                // Contador dos itens adicionados

                TextView itensAddReceita = findViewById(R.id.itensAtuaisReceita);
                int itensReceita = adapter.getCount();
                testeAdd = itensReceita;
                itensAddReceita.setText(String.valueOf(itensReceita));
              }
            });

    // Campo soma das despesas
    Bundle extras = getIntent().getExtras();
    despesas = extras.getFloat("TotalDespesas");
    String desp;
    desp = despesas.toString();

    Double despesaDouble = Double.parseDouble(desp);
    DecimalFormat dfDespesas = new DecimalFormat("R$ ,##0.00");
    String saidaDespesas = dfDespesas.format(despesaDouble);
    tvDespesaTotal.setText(saidaDespesas);
    // Campo soma das despesas final

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

  public void updateAdapter() {
    adapter.notifyDataSetChanged();
    TextView itensAddReceita = findViewById(R.id.itensAtuaisReceita);
    int itensReceita = adapter.getCount();
    testeAdd = itensReceita;
    itensAddReceita.setText(String.valueOf(itensReceita));
  }

  public void proxima(View view) {

    if ((testeAdd > 0) && (!valor.equals(""))) {
      try {

        for (Iterator<Item> iterator = listaItens.iterator(); iterator.hasNext(); ) {
          Item item = iterator.next(); // pega o item da lista

          String id = dbreference.push().getKey(); // banco
          dbreference.child(id).setValue(item); // banco

          pegaValor = Float.parseFloat(item.getValor().replaceAll("\\D", ""));
          receitas = receitas + (pegaValor / 100);
        }
        Bundle extras = getIntent().getExtras();
        despesas = extras.getFloat("TotalDespesas");
        diferenca = receitas - despesas;

        // Passa lista serializada
        ArrayList<Item> listaDespesas;
        listaDespesas = (ArrayList<Item>) getIntent().getSerializableExtra("DespesasDiscriminadas");

        Intent i = new Intent(Main3Activity.this, Main4Activity.class);
        i.putExtra("TotalDespesas", "" + despesas);
        i.putExtra("TotalReceitas", "" + receitas);
        i.putExtra("Diferenca", "" + diferenca);

        // Passa lista serializada
        i.putExtra("DespesasDiscriminadas", listaDespesas);
        i.putExtra("ReceitasDiscriminadas", listaItens);
        startActivity(i);

      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    } else {
      new AlertDialog.Builder(this)
              .setTitle("Nenhum valor!!!")
              .setMessage("Nenhum valor válido adicionado!!!")
              .setPositiveButton(
                      "OK",
                      new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                      })
              .show();
    }
  }
}
