package model;

public class Login {

  private String Usuario;
  private String Senha;

  public Login(String Usuario, String Senha) {
    this.Usuario = Usuario;
    this.Senha = Senha;
  }

  public String getUsuario() {
    return Usuario;
  }

  public void setUsuario(String usuario) {
    Usuario = usuario;
  }

  public String getSenha() {
    return Senha;
  }

  public void setSenha(String senha) {
    Senha = senha;
  }

  @Override
  public String toString() {
    return getUsuario() + getSenha();

  }

}
