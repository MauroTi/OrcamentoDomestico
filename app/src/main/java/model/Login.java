package model;

public class Login {

    private static String Usuario;
    private static String Senha;

    public Login(String Usuario, String Senha) {
        this.Usuario = Usuario;
        this.Senha = Senha;
    }


    public static String getUsuario() {
        return Usuario;
    }

    public static void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public static String getSenha() {
        return Senha;
    }

    public static void setSenha(String senha) {
        Senha = senha;
    }

    @Override
    public String toString() {
        return "Login [Usuário: " + getUsuario() + ", Senha: " + getSenha() + "]";
    }

}