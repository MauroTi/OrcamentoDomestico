package model;

public class Login {

    private static String Usuario;
    private static String Senha;

    public Login(Login login) {
    }

    public Login() {

    }


    public String getUsuario()
    {
        return Usuario;
    }
    public void setUsuario(String usuario)
    {
        Usuario = usuario;
    }
    public String getSenha()
    {
        return Senha;
    }
    public void setSenha(String senha)
    {
        Senha = senha;
    }

    @Override
    public String toString() {
        return "Login [Usuário: " + getUsuario() + ", Senha: " + getSenha() + "]";
    }

}