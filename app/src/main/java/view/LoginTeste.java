package view;

import controller.LoginController;
import model.Login;

public class LoginTeste {

    public static void main(String[] args) {
        Login log1 = new Login();
        log1.setUsuario("usuario 1");
        log1.setSenha("senha 1");

        Login log2 = new Login();
        log2.setUsuario("usuario 2");
        log2.setSenha("senha 2");

        LoginController lc = new LoginController();

        lc.cadastroLogin(log1);
        lc.cadastroLogin(log2);

        LoginController.exibeLogin();

    }


}
