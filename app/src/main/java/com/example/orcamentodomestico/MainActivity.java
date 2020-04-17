package com.example.orcamentodomestico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import controller.LoginController;
import model.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    Login login;
    LoginController loginController;

    public void cadastrar(View view) {

        EditText etNome = findViewById(R.id.etNome);
        String usuario = etNome.getText().toString();
        EditText etSenha = findViewById(R.id.etSenha);
        String senha = etSenha.getText().toString();

        loginController = new LoginController();
        login = new Login();
        login.setUsuario(usuario);
        login.setSenha(senha);
        login.toString();
        loginController.cadastroLogin(login);

        //Toast.makeText(getBaseContext(), login.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(getBaseContext(), loginController.exibeLogin(), Toast.LENGTH_LONG).show();

    }

    public void proxima(View view) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);

    }



}
