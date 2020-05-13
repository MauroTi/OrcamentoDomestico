package com.example.orcamentodomestico;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {



    //criação do adapter e da lista
    ListAdapterItem adapter;
    ArrayList<Item> listaItens;
    //fim criação adapter e lista

    //controles da tela
    EditText txtDespesa;
    EditText txtValor;
    Button btnAdicionar;
    ListView minhaLista;
    //fim dos controles da tela

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Context context = this;
        listaItens = new ArrayList<Item>();
        adapter = new ListAdapterItem(context, listaItens);

        minhaLista = findViewById(R.id.minhaLista);
        minhaLista.setAdapter(adapter);
        txtDespesa = findViewById(R.id.despesa);
        txtValor = findViewById(R.id.valor);
        btnAdicionar = findViewById(R.id.btnAdicionar);


        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String despesa = txtDespesa.getText().toString();
                String valor = txtValor.getText().toString();
                Item novoItem = new Item(despesa, valor);
                adapter.add(novoItem);
                adapter.notifyDataSetChanged();
                //Log.i("click", "Total de itens na lista" +  adapter.getCount());
                Toast.makeText(context, "Total de itens na lista  " + adapter.getCount(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "Item adicionado com sucesso!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateAdapter(){
        adapter.notifyDataSetChanged();
    }

    public void proxima(View view) {
        Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
        startActivity(intent);
    }


}


