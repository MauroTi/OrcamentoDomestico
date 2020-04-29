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

  // Variáveis SharedPreferences

  private EditText ed1;
  private static final String KEY_SAUDACAO = "msgSaudacao";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // SharedPreferences

    ed1 = findViewById(R.id.etNome);
    String msgNome = Utils.carregarDadosComponente(KEY_SAUDACAO, this);
    ed1.setText(msgNome);
  }

  public void gravar(View view) {
    Utils.salvarDadosComponente(KEY_SAUDACAO, ed1.getText().toString(), view.getContext());
  }

  // Fim SharedPreferences

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
      if(!loginController.exibeLogin().contains(usuario))
      {
          loginController.cadastroLogin(login);
      Toast.makeText(MainActivity.this, "Cadastro efetuado com sucesso!!", Toast.LENGTH_SHORT).show();
      }
      else
          {
      Toast.makeText(MainActivity.this, "Usuário já existente!!", Toast.LENGTH_SHORT).show();
          }

    gravar(view);

    Toast.makeText(getBaseContext(), loginController.exibeLogin(), Toast.LENGTH_LONG).show();
  }

    public void logar(View view) {

    EditText etNome = findViewById(R.id.etNome);
    EditText etSenha = findViewById(R.id.etSenha);

    String nome = etNome.getText().toString();
    String codigo = etSenha.getText().toString();
    String teste = loginController.exibeLogin();
    String valida = (nome + codigo);


        Toast.makeText(MainActivity.this, teste, Toast.LENGTH_LONG).show();

    if ((etNome.getText().toString().equals("")) && (etSenha.getText().toString().equals(""))) {
      Toast.makeText(MainActivity.this, "Os campos devem estar preenchidos.", Toast.LENGTH_LONG)
          .show();
    } else if (etNome.getText().toString().equals("")) {
      Toast.makeText(MainActivity.this, "O campo nome deve estar preenchido.", Toast.LENGTH_LONG)
          .show();
    } else if (etSenha.getText().toString().equals("")) {
      Toast.makeText(MainActivity.this, "O campo senha deve estar preenchido.", Toast.LENGTH_LONG)
          .show();
    }


//Aqui está o problema
    else if (login.equals(loginController.exibeLogin())) {

      Toast.makeText(MainActivity.this, nome + codigo + " VARIAVEIS ", Toast.LENGTH_LONG).show();
      Intent intent = new Intent(MainActivity.this, Main2Activity.class);
      startActivity(intent);

    } else {
      Toast.makeText(MainActivity.this, "Nome ou senha não encontrados, tente novamente.",
              Toast.LENGTH_LONG).show();
      Toast.makeText(MainActivity.this, nome + codigo + "VARIAVEIS" + teste, Toast.LENGTH_LONG).show();
    }
  }

  /*public void proxima(View view) {
      Intent intent = new Intent(MainActivity.this, Main2Activity.class);
      startActivity(intent);
  }*/

}
