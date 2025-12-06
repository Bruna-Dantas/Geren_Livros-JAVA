package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

public class bancoUsuario {

    private static final String NOME_ARQUIVO = "usuarios.dat";
    private List< Usuario> usuarios;

    //Carregar usuários
    @SuppressWarnings("unchecked")
    private List<Usuario> carregar(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Salvar lista de usuários no arquivo
    public void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(this.usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public bancoUsuario() {
        this.usuarios = carregar();
    }

    // Cadastrar usuário
    public void adicionar(Usuario usuario) {
        usuarios.add(usuario);
        salvar();
    }

    // Verificar se o email já está cadastrado
    public boolean emailJaExiste(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    // Autenticar usuário (Login)
    public Usuario autenticar(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email) &&
                usuario.getSenha().equals(senha)) {
                return usuario; // login correto
            }
        }
        return null; // login errado
    }

    // Listar todos 
    public List<Usuario> listarTodos() {
        return usuarios;
    }
    
}