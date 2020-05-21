package com.example.orcamentodomestico;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

  // criação do adapter e da lista
  ListAdapterItem adapter;
  ArrayList<Item> listaItens;
  // fim criação adapter e lista
  Float despesas = Float.valueOf(0);

  // controles da tela
  EditText txtDespesa;
  EditText txtValor;
  Button btnAdicionar;
  ListView minhaLista;
  MonetaryMask monetaryMask;
  // fim dos controles da tela

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    final Context context = this;

    listaItens = new ArrayList<Item>();
    adapter = new ListAdapterItem(context, listaItens);

    minhaLista = findViewById(R.id.minhaLista);
    minhaLista.setAdapter(adapter);
    txtDespesa = findViewById(R.id.receita);
    txtValor = findViewById(R.id.valorReceita);
    monetaryMask = new MonetaryMask(this.txtValor);
    btnAdicionar = findViewById(R.id.btnAdicionarDespesa);

    Locale mLocale = new Locale("pt", "BR");
    txtValor.addTextChangedListener(new MonetaryMask(txtValor, mLocale));

    btnAdicionar.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            String despesa = txtDespesa.getText().toString();
            String valor = txtValor.getText().toString();
            Item novoItem = new Item(despesa, valor);
            adapter.add(novoItem);
            adapter.notifyDataSetChanged();

            txtDespesa.setText("");
            txtValor.setText("0");

            // Contador dos itens adicionados
            TextView itensAddDespesa = findViewById(R.id.itensAtuaisDespesa);
            int itensDespesa = adapter.getCount();
            itensAddDespesa.setText(String.valueOf(itensDespesa));
          }
        });
  }

  public void updateAdapter() {
    adapter.notifyDataSetChanged();

    TextView itensAddDespesa = findViewById(R.id.itensAtuaisDespesa);
    int itensDespesa = adapter.getCount();
    itensAddDespesa.setText(String.valueOf(itensDespesa));
  }

  public void proxima(View view) {

    Float pegaValor;

    for (Iterator<Item> iterator = listaItens.iterator(); iterator.hasNext(); ) {
      Item item = iterator.next(); // pega o item da lista

      pegaValor = Float.parseFloat(item.getValor().replaceAll("\\D", ""));
      despesas = (despesas) + (pegaValor / 100);
    }

    Intent i = new Intent(Main2Activity.this, Main4Activity.class);
    i.putExtra("TotalDespesas", despesas);
    startActivity(i);

    // Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
    // startActivity(intent);
  }
}
