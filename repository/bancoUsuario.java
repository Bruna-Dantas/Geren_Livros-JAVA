package repository;

import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class bancoUsuario {

    private List<Usuario> usuarios;

    public bancoUsuario() {
        this.usuarios = new ArrayList<>();
    }

    // Cadastrar usuário
    public void adicionar(Usuario usuario) {
        usuarios.add(usuario);
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