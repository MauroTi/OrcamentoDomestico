package controller;

import java.util.ArrayList;
import java.util.List;

import model.Login;

public class LoginController {

  static List<Login> listaLogins = new ArrayList<>();

  public static String exibeLogin() {

    return listaLogins.toString();
  }

  public void cadastroLogin(Login login) {

    listaLogins.add(login);
  }

  public List<Login> reLogin() {
    return this.listaLogins;
  }
}
