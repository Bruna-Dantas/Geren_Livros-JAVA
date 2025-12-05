package repository;

import java.util.ArrayList;
import java.util.List;

import model.Livro;

public class bancoLivros {
    
    private List<Livro> livros;

    public bancoLivros() {
        this.livros = new ArrayList<>();
    }

    // Adicionar livro
    public void adicionar(Livro livro) {
        livros.add(livro);
    }

    // Remover livro
    public void remover(Livro livro) {
        livros.remove(livro);
    }

    // Listar todos os livros
    public List<Livro> listarTodos() {
        return livros;
    }

    // Buscar por título
    public List<Livro> buscarPorTitulo(String busca) {
        List<Livro> resultado = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getTitulo().toLowerCase().contains(busca.toLowerCase())) {
                resultado.add(livro);
            }
        }

        return resultado;
    }

    // Buscar por gênero
    public List<Livro> buscarPorGenero(String genero) {
        List<Livro> resultado = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getGenero().equalsIgnoreCase(genero)) {
                resultado.add(livro);
            }
        }

        return resultado;
    }
}