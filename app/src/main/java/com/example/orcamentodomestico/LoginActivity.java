package com.example.orcamentodomestico;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

  // CHAVE DO NOME DE USUÁRIO EM SHARED PREFERENCES
  private static final String NOME_USUARIO = "nome-usuario";
  private FirebaseAuth mAuth;
  private EditText email;
  private EditText senha;
  private Button btnLogar;
  private Button btnLogout;
  private Button btnCancelar;
  private Button btnCadastrar;
  // Variáveis SharedPreferences
  private Button btnRecuperaSenha;
  private EditText ed1;

  public boolean onCreateOptionsMenu(android.view.Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);

    return true;
  }

  ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_main);

    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();
    // Verifica se usuario esta logado
    FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {
      Toast.makeText(
              getApplicationContext(),
              "Bem vindo de volta " + user.getEmail() + "!",
              Toast.LENGTH_LONG)
              .show();
    } else {
    }

    // SharedPreferences

    ed1 = findViewById(R.id.etEmail);
    String msgNome = Utils.carregarDadosComponente(NOME_USUARIO, this);
    ed1.setText(msgNome);

    mAuth = FirebaseAuth.getInstance();

    btnLogar = (Button) findViewById(R.id.btnLogar);
    btnLogout = (Button) findViewById(R.id.btnLogout);
    btnCancelar = (Button) findViewById(R.id.btnCancelar);
    btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
    btnRecuperaSenha = (Button) findViewById(R.id.btnRecuperaSenha);
    email = (EditText) findViewById(R.id.etEmail);
    senha = (EditText) findViewById(R.id.etSenha);

    btnLogar.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                if ((email.getText().toString().equals(""))
                        && (senha.getText().toString().equals(""))) {
                  Toast.makeText(
                          LoginActivity.this, "Os campos devem estar preenchidos.", Toast.LENGTH_LONG)
                          .show();
                } else if (email.getText().toString().equals("")) {
                  Toast.makeText(
                          LoginActivity.this, "O campo email deve estar preenchido.", Toast.LENGTH_LONG)
                          .show();
                } else if (senha.getText().toString().equals("")) {
                  Toast.makeText(
                          LoginActivity.this, "O campo senha deve estar preenchido.", Toast.LENGTH_LONG)
                          .show();
                } else if (!(email.getText().toString().equals(""))
                        && !(senha.getText().toString().equals(""))) {
                  loginUser(email.getText().toString(), senha.getText().toString());
                  gravar(email);
                }
              }
            });

    btnCadastrar.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                if ((email.getText().toString().equals(""))
                        && (senha.getText().toString().equals(""))) {
                  Toast.makeText(
                          LoginActivity.this, "Os campos devem estar preenchidos.", Toast.LENGTH_LONG)
                          .show();
                } else if (email.getText().toString().equals("")) {
                  Toast.makeText(
                          LoginActivity.this, "O campo email deve estar preenchido.", Toast.LENGTH_LONG)
                          .show();
                } else if (senha.getText().toString().equals("")) {
                  Toast.makeText(
                          LoginActivity.this, "O campo senha deve estar preenchido.", Toast.LENGTH_LONG)
                          .show();
                } else if (!(email.getText().toString().equals(""))
                        && !(senha.getText().toString().equals(""))) {
                  loginNewUser(email.getText().toString(), senha.getText().toString());
                  gravar(email);
                }
              }
            });

    btnRecuperaSenha.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
              }
            });

    btnCancelar.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                email.setText("");
                senha.setText("");
              }
            });

    /* btnLogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            disconnect();
        }
    });*/
  }

  //Botões barra app
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.logout) {
      new AlertDialog.Builder(this)
              .setTitle("Nenhum usuário logado!")
              .setMessage("Não há usuários logados no momento!")
              .setNegativeButton("Ok", null)
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

  private void loginUser(String email, String password) {
    mAuth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                    this,
                    new OnCompleteListener<AuthResult>() {

                      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          // Sign in success, update UI with the signed-in user's information
                          Log.d("TAG", "signInWithEmail:success");
                          FirebaseUser user = mAuth.getCurrentUser();
                          Toast.makeText(
                                  getApplicationContext(),
                                  "Login realizado com sucesso!",
                                  Toast.LENGTH_LONG)
                                  .show();

                          openPrincipalActivity();

                          // updateUI(user);
                        } else {
                          // If sign in fails, display a message to the user.
                          Log.w("TAG", "signInWithEmail:failure", task.getException());
                  /* Toast.makeText(
                      getApplicationContext(),
                      "Cadastro não encontrado! Tente novamente ou cadastre-se!",
                      Toast.LENGTH_LONG)
                  .show();*/

                          String errorCode =
                                  ((FirebaseAuthException) Objects.requireNonNull(task.getException()))
                                          .getErrorCode();
                          EditText etEmail = findViewById(R.id.etEmail);
                          EditText etPassword = findViewById(R.id.etSenha);
                          switch (errorCode) {
                            case "ERROR_INVALID_CUSTOM_TOKEN":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "O formato do token personalizado está incorreto. Por favor, verifique a documentação.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "O token personalizado corresponde a um público diferente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_INVALID_CREDENTIAL":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "A credencial de autenticação fornecida está incorreta ou expirou.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_INVALID_EMAIL":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "\n" + "O endereço de email está mal formatado.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              etEmail.setError("Email mal formatado.");
                              etEmail.requestFocus();
                              break;

                            case "ERROR_WRONG_PASSWORD":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "\n" + "A senha é inválida ou o usuário não tem uma senha.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              etPassword.setError("Senha inválida");
                              etPassword.requestFocus();
                              etPassword.setText("");
                              break;

                            case "ERROR_USER_MISMATCH":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "As credenciais fornecidas não correspondem ao usuário conectado anteriormente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_REQUIRES_RECENT_LOGIN":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Esta operação é sensível e requer autenticação recente. Efetue login novamente antes de tentar novamente esta solicitação.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Já existe uma conta com o mesmo endereço de email, mas com credenciais de login diferentes. Faça login usando um provedor associado a este endereço de e-mail.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_EMAIL_ALREADY_IN_USE":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "\n" + "O endereço de email já está sendo usado por outra conta.   ",
                                      Toast.LENGTH_LONG)
                                      .show();
                              etEmail.setError("Email já usado em outra conta.");
                              etEmail.requestFocus();
                              break;

                            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Essa credencial já está associada a uma conta de usuário diferente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_USER_DISABLED":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "A conta do usuário foi desativada por um administrador.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_USER_TOKEN_EXPIRED":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "A credencial do usuário não é mais válida. O usuário deve entrar novamente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_USER_NOT_FOUND":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Cadastro não encontrado! Tente novamente ou cadastre-se!",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_INVALID_USER_TOKEN":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "A credencial do usuário não é mais válida. Entre novamente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_OPERATION_NOT_ALLOWED":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Esta operação não é permitida. Você deve ativar este serviço no console.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_WEAK_PASSWORD":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "\n" + "A senha fornecida é inválida.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              etPassword.setError("A senha é inválida, deve conter no mínimo 6 caracteres");
                              etPassword.requestFocus();
                              break;

                            default:
                              Toast.makeText(
                                      LoginActivity.this, "Ops, ocorreu algo imprevisto.", Toast.LENGTH_LONG)
                                      .show();
                              break;
                          }
                        }
                      }
                    });
  }

  private void loginNewUser(final String email, final String password) {
    mAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                    this,
                    new OnCompleteListener<AuthResult>() {

                      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          // Sign in success, update UI with the signed-in user's information
                          Log.d("TAG", "createUserWithEmail:success");
                          FirebaseUser user = mAuth.getCurrentUser();
                          Toast.makeText(
                                  getApplicationContext(),
                                  "Cadastro realizado com sucesso!",
                                  Toast.LENGTH_LONG)
                                  .show();
                          // updateUI(user);
                        } else {
                          // If sign in fails, display a message to the user.
                          Log.w("TAG", "createUserWithEmail:failure", task.getException());

                          String errorCode =
                                  ((FirebaseAuthException) Objects.requireNonNull(task.getException()))
                                          .getErrorCode();
                          EditText etEmail = findViewById(R.id.etEmail);
                          EditText etPassword = findViewById(R.id.etSenha);
                          switch (errorCode) {
                            case "ERROR_INVALID_CUSTOM_TOKEN":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "O formato do token personalizado está incorreto. Por favor, verifique a documentação.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "O token personalizado corresponde a um público diferente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_INVALID_CREDENTIAL":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "A credencial de autenticação fornecida está incorreta ou expirou.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_INVALID_EMAIL":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "\n" + "O endereço de email está mal formatado.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              etEmail.setError("Email mal formatado.");
                              etEmail.requestFocus();
                              break;

                            case "ERROR_WRONG_PASSWORD":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "\n" + "A senha é inválida ou o usuário não tem uma senha.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              etPassword.setError("Senha inválida");
                              etPassword.requestFocus();
                              etPassword.setText("");
                              break;

                            case "ERROR_USER_MISMATCH":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "As credenciais fornecidas não correspondem ao usuário conectado anteriormente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_REQUIRES_RECENT_LOGIN":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Esta operação é sensível e requer autenticação recente. Efetue login novamente antes de tentar novamente esta solicitação.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Já existe uma conta com o mesmo endereço de email, mas com credenciais de login diferentes. Faça login usando um provedor associado a este endereço de e-mail.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_EMAIL_ALREADY_IN_USE":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "\n" + "O endereço de email já está sendo usado por outra conta.   ",
                                      Toast.LENGTH_LONG)
                                      .show();
                              etEmail.setError("Email já usado em outra conta.");
                              etEmail.requestFocus();
                              break;

                            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Essa credencial já está associada a uma conta de usuário diferente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_USER_DISABLED":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "A conta do usuário foi desativada por um administrador.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_USER_TOKEN_EXPIRED":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "A credencial do usuário não é mais válida. O usuário deve entrar novamente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_USER_NOT_FOUND":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Cadastro não encontrado! Tente novamente ou cadastre-se!",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_INVALID_USER_TOKEN":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "A credencial do usuário não é mais válida. Entre novamente.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_OPERATION_NOT_ALLOWED":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "Esta operação não é permitida. Você deve ativar este serviço no console.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              break;

                            case "ERROR_WEAK_PASSWORD":
                              Toast.makeText(
                                      LoginActivity.this,
                                      "\n" + "A senha fornecida é inválida.",
                                      Toast.LENGTH_LONG)
                                      .show();
                              etPassword.setError("A senha é inválida, deve conter no mínimo 6 caracteres");
                              etPassword.requestFocus();
                              break;

                            default:
                              Toast.makeText(
                                      LoginActivity.this, "Ops, ocorreu algo imprevisto.", Toast.LENGTH_LONG)
                                      .show();
                              break;
                          }
                        }
                      }
                    });

    /* String excecao;

    try {
        throw Objects.requireNonNull(task.getException());
    } catch (FirebaseNetworkException ex) {
        excecao = "Verifique sua conexão com a internet.";
    } catch (FirebaseAuthWeakPasswordException ex) {
        excecao =
                "Senha fraca. Utilize ao menos seis caracteres contendo letras e números.";
    } catch (FirebaseAuthUserCollisionException ex) {
        excecao = "E-mail já cadastrado.";
    } catch (FirebaseAuthInvalidCredentialsException ex) {
        excecao = "O e-mail fornecido é inválido.";
    } catch (Exception ex) {
        excecao = "Erro ao tentar cadastrar.";
        ex.printStackTrace();
    }

    Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_SHORT).show();
            Toast.makeText(
                    getApplicationContext(),
                    "Cadastro não efetuado! Insira Email e senha de no mínimo 6 caracteres!",
                    Toast.LENGTH_LONG)
                    .show();*/
    // updateUI(null);
  }

  private boolean userConnected() {
    FirebaseUser currentUser = mAuth.getCurrentUser();

    if (currentUser == null) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (userConnected()) {
      openPrincipalActivity();
    }
  }

  private void openPrincipalActivity() {
    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
    startActivity(intent);
    finish();
  }

  public void gravar(View view) {
    Utils.salvarDadosComponente(NOME_USUARIO, ed1.getText().toString(), view.getContext());
  }

  private void disconnect() {
    FirebaseAuth.getInstance().signOut();
    closePrincipal();
  }

  private void closePrincipal() {
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    startActivity(intent);
    finish();
  }
}

// Fim SharedPreferences

/*  Login login;
LoginController loginController = new LoginController();

String usuario;
String senha;

public void cadastrar(View view) {

  EditText etNome = findViewById(R.id.etEmail);
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
      Toast.makeText(LoginActivity.this, "Cadastro efetuado com sucesso!!", Toast.LENGTH_SHORT)
          .show();
    } else {
      Toast.makeText(LoginActivity.this, "Usuário já existente!!", Toast.LENGTH_SHORT).show();
    }
  }

  gravar(view);
}

public void logar(View view) {

  Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
  startActivity(intent);
  finish();

  EditText etNome = findViewById(R.id.etEmail);
  EditText etSenha = findViewById(R.id.etSenha);

  String nome = etNome.getText().toString();
  String codigo = etSenha.getText().toString();
  // String teste = loginController.exibeLogin();
  // String valida = (nome + codigo);

  if ((etNome.getText().toString().equals("")) && (etSenha.getText().toString().equals(""))) {
    Toast.makeText(LoginActivity.this, "Os campos devem estar preenchidos.", Toast.LENGTH_LONG)
        .show();
  } else if (etNome.getText().toString().equals("")) {
    Toast.makeText(LoginActivity.this, "O campo nome deve estar preenchido.", Toast.LENGTH_LONG)
        .show();
  } else if (etSenha.getText().toString().equals("")) {
    Toast.makeText(LoginActivity.this, "O campo senha deve estar preenchido.", Toast.LENGTH_LONG)
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
     /*Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
      startActivity(intent);
      finish();
    } else {

      new AlertDialog.Builder(this)
              .setTitle("Dados não conferem.")
              .setMessage("Nome ou senha não encontrados, tente novamente.")
              .setPositiveButton(
                      "OK",
                      new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                      })
              .show();
     /* Toast.makeText(
              LoginActivity.this,
              "Nome ou senha não encontrados, tente novamente.",
              Toast.LENGTH_LONG)
          .show();
    }*/
