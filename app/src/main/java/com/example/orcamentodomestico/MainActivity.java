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
  LoginController loginController = new LoginController();

  String usuario;
  String senha;

  public void cadastrar(View view) {

    EditText etNome = findViewById(R.id.etNome);
    usuario = etNome.getText().toString();
    EditText etSenha = findViewById(R.id.etSenha);
    senha = etSenha.getText().toString();

    login = new Login(usuario, senha);
    login.setUsuario(usuario);
    login.setSenha(senha);
    loginController.cadastroLogin(login);

    // Toast.makeText(getBaseContext(), login.toString(), Toast.LENGTH_LONG).show();
    Toast.makeText(getBaseContext(), loginController.exibeLogin(), Toast.LENGTH_LONG).show();
  }

  public void logar(View view) {


      if((usuario == null || senha == null) || (usuario == " " || senha== " "))
      {
      Toast.makeText(MainActivity.this, "Os campos devem estar preenchidos.", Toast.LENGTH_LONG).show();
      }
    else if (loginController.exibeLogin().contains(usuario)
        && loginController.exibeLogin().contains(senha)) {
     Intent intent = new Intent(MainActivity.this, Main2Activity.class);
      startActivity(intent);

       //Toast.makeText(MainActivity.this, usuario + " " + senha + " " + loginController.exibeLogin(), Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(
              MainActivity.this,
              "Nome ou senha não encontrados, tente novamente.",
              Toast.LENGTH_LONG)
          .show();
    }
  }

  /*public void proxima(View view) {
      Intent intent = new Intent(MainActivity.this, Main2Activity.class);
      startActivity(intent);
  }*/
}
