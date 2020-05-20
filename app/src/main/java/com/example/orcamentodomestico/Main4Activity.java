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

public class Main4Activity extends AppCompatActivity {



    //criação do adapter e da lista
    ListAdapterItem adapter;
    ArrayList<Item> listaItens;
    //fim criação adapter e lista

    //controles da tela

    EditText txtReceita;
    EditText txtValorReceita;
    Button btnAdicionar;
    ListView minhaLista;
    MonetaryMask monetaryMask;
    //fim dos controles da tela

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        final Context context = this;
        listaItens = new ArrayList<Item>();
        adapter = new ListAdapterItem(context, listaItens);

        minhaLista = findViewById(R.id.minhaLista);
        minhaLista.setAdapter(adapter);
        txtReceita = findViewById(R.id.receita);
        txtValorReceita = findViewById(R.id.valorReceita);
        monetaryMask = new MonetaryMask(this.txtValorReceita);
        btnAdicionar = findViewById(R.id.btnAdicionarReceita);

        Locale mLocale = new Locale("pt", "BR");
        txtValorReceita.addTextChangedListener(new MonetaryMask(txtValorReceita, mLocale));


        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String despesa = txtReceita.getText().toString();
                String valor = txtValorReceita.getText().toString();
                Item novoItem = new Item(despesa, valor);
                adapter.add(novoItem);
                adapter.notifyDataSetChanged();
                txtReceita.setText("");
                txtValorReceita.setText("0");
                updateAdapter();


                //Contador dos itens adicionados
                TextView itensAddReceita;
                itensAddReceita = findViewById(R.id.itensAtuaisReceita);
                int itensReceita = adapter.getCount();
                itensAddReceita.setText(String.valueOf(itensReceita));



            }
        });
    }

    public void updateAdapter(){
        adapter.notifyDataSetChanged();
    }

    public void proxima(View view) {

        Float receitas = Float.valueOf(0);
        Float diferenca = Float.valueOf(0);
            Float receita = Float.valueOf(0);
            Float pegaValor;

            for (Iterator<Item> iterator = listaItens.iterator(); iterator.hasNext(); ) {
                Item item = iterator.next(); // pega o item da lista

                pegaValor = Float.parseFloat(item.getValor().replaceAll("\\D", ""));
                receita = (receita) + (pegaValor/100) ;
            }

        Bundle extras = getIntent().getExtras();
        Float despesas = extras.getFloat("TotalDespesas");
        diferenca = receitas - despesas;

        //Toast.makeText(Main4Activity.this, "Receitas " + receitas, Toast.LENGTH_SHORT).show();
        //Toast.makeText(Main4Activity.this, "Despesas " + despesas, Toast.LENGTH_SHORT).show();
        //Toast.makeText(Main4Activity.this, "Total " + diferenca, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(Main4Activity.this,Main3Activity.class);
        i.putExtra("TotalDespesas", ""+ diferenca);
        i.putExtra("TotalReceitas", ""+ receitas);
        i.putExtra("Diferenca", ""+ diferenca);

        startActivity(i);

        }
}



