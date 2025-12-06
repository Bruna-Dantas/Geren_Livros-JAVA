package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Livro;

public class bancoLivros {
    
    private static final String NOME_ARQUIVO = "livros.dat";
    private List<Livro> livros;

    public bancoLivros() {
        this.livros = carregar();
    }

    // Salvar a lista de livros no arquivo
    @SuppressWarnings("unchecked")
    private List<Livro> carregar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Livro>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //Salvar a lista de livros no arquivo 
    public void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(this.livros);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar os livros!", "Erro de Persistência", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Adicionar livro
    public void adicionar(Livro livro) {
        livros.add(livro);
        salvar();
    }

    // Remover livro
    public void remover(Livro livro) {
        livros.remove(livro);
        salvar();
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