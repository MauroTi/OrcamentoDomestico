package com.example.orcamentodomestico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

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

    // Login loginTeste = new Login("mauro","oi");
    // loginController.cadastroLogin(loginTeste);

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
    if (!(etNome.getText().toString().equals("")) && !(etSenha.getText().toString().equals(""))) {

      List<Login> lista = loginController.reLogin();
      int contatoExisteLogin = 0;

      for (int i = 0; i < loginController.reLogin().size(); i++) {
        if (usuario.equals(lista.get(i).getUsuario()) && senha.equals(lista.get(i).getSenha())) {
          contatoExisteLogin = 1;
        }
      }
      if (contatoExisteLogin != 1) {
        loginController.cadastroLogin(login);
        Toast.makeText(MainActivity.this, "Cadastro efetuado com sucesso!!", Toast.LENGTH_SHORT)
            .show();
      } else {
        Toast.makeText(MainActivity.this, "Usuário já existente!!", Toast.LENGTH_SHORT).show();
      }
    }

    gravar(view);
  }

  public void logar(View view) {

    EditText etNome = findViewById(R.id.etNome);
    EditText etSenha = findViewById(R.id.etSenha);

    String nome = etNome.getText().toString();
    String codigo = etSenha.getText().toString();
    String teste = loginController.exibeLogin();
    String valida = (nome + codigo);

    if ((etNome.getText().toString().equals("")) && (etSenha.getText().toString().equals(""))) {
      Toast.makeText(MainActivity.this, "Os campos devem estar preenchidos.", Toast.LENGTH_LONG)
          .show();
    } else if (etNome.getText().toString().equals("")) {
      Toast.makeText(MainActivity.this, "O campo nome deve estar preenchido.", Toast.LENGTH_LONG)
          .show();
    } else if (etSenha.getText().toString().equals("")) {
      Toast.makeText(MainActivity.this, "O campo senha deve estar preenchido.", Toast.LENGTH_LONG)
          .show();
    } else if (!(etNome.getText().toString().equals(""))
        && !(etSenha.getText().toString().equals(""))) {

      List<Login> lista = loginController.reLogin();
      int contatoExiste = 0;

      for (int i = 0; i < loginController.reLogin().size(); i++) {
        if (nome.equals(lista.get(i).getUsuario()) && codigo.equals(lista.get(i).getSenha())) {
          contatoExiste = 1;
        }
      }
      if (contatoExiste == 1) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
      } else {
        Toast.makeText(
                MainActivity.this,
                "Nome ou senha não encontrados, tente novamente.",
                Toast.LENGTH_LONG)
            .show();
      }
    }
  }
}
