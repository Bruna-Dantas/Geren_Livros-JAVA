import repository.bancoLivros;
import repository.bancoUsuario;
import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        bancoUsuario usuarios = new bancoUsuario();
        bancoLivros livros = new bancoLivros();
        new LoginFrame(usuarios, livros).setVisible(true);
    }

}