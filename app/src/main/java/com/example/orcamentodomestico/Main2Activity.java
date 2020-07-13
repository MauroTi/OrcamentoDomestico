package com.example.orcamentodomestico;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {


    DatabaseReference dbreference; //banco
    // criação do adapter e da lista
    ListAdapterItem adapter;
    ArrayList<Item> listaItens;
    int testeAdd;

    // fim criação adapter e lista

    // controles da tela
    EditText txtDespesa;
    EditText txtValor;
    Button btnAdicionar;
    ListView minhaLista;
    MonetaryMask monetaryMask;

    // fim dos controles da tela
    private FirebaseAuth mAuth;
    String valor = String.valueOf(0);

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbreference = FirebaseDatabase.getInstance().getReference("despesas"); //banco

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Verifica se usuario esta logado
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(
                    getApplicationContext(), "Bem vindo " + user.getEmail() + "!", Toast.LENGTH_LONG)
                    .show();
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
        txtDespesa = findViewById(R.id.despesa);
        txtValor = findViewById(R.id.valorDespesa);
        monetaryMask = new MonetaryMask(this.txtValor);
        btnAdicionar = findViewById(R.id.btnAdicionarDespesa);


        Locale mLocale = new Locale("pt", "BR");
        txtValor.addTextChangedListener(new MonetaryMask(txtValor, mLocale));

        btnAdicionar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String despesa = txtDespesa.getText().toString();
                        valor = txtValor.getText().toString();
                        Item novoItem = new Item(despesa, valor);
                        adapter.add(novoItem);
                        adapter.notifyDataSetChanged();
                        txtDespesa.setText("");
                        txtValor.setText("0");

                        // Contador dos itens adicionados
                        TextView itensAddDespesa = findViewById(R.id.itensAtuaisDespesa);
                        int itensDespesa = adapter.getCount();
                        testeAdd = itensDespesa;
                        itensAddDespesa.setText(String.valueOf(itensDespesa));
                    }
                });
    }

//Botoes barra app
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

    /*private void disconnect() {
        FirebaseAuth.getInstance().signOut();
        closePrincipal();
    }*/

    private void closePrincipal() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();
        TextView itensAddDespesa = findViewById(R.id.itensAtuaisDespesa);
        int itensDespesa = adapter.getCount();
        testeAdd = itensDespesa;
        itensAddDespesa.setText(String.valueOf(itensDespesa));
    }

    public void proxima(View view) {

        Float pegaValor;
        Float despesas = Float.valueOf(0);

        if ((testeAdd > 0) && (!valor.equals(""))) {
            try {

                for (Iterator<Item> iterator = listaItens.iterator(); iterator.hasNext(); ) {
                    Item item = iterator.next(); // pega o item da lista

                    String id = dbreference.push().getKey(); //banco
                    dbreference.child(id).setValue(item); //banco

                    pegaValor = Float.parseFloat(item.getValor().replaceAll("\\D", ""));
                    despesas = (despesas) + (pegaValor / 100);
                }
                Intent i = new Intent(Main2Activity.this, Main3Activity.class);
                i.putExtra("TotalDespesas", despesas);
                //i.putExtra("DespesasDiscriminadas",listaItens);

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

    public void onBackPressed() {

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
}
