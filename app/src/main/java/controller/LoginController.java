package controller;

import java.util.ArrayList;
import java.util.List;

import model.Login;

public class LoginController {

    static List<Login> listaLogins = new ArrayList<>();

    public void cadastroLogin(Login login) {

        listaLogins.add(login);

    }

    public static String exibeLogin() {
        //System.out.println(listaLogins.toString());
        return listaLogins.toString();
    }
}
