package model;

import java.io.Serializable;

public class Livro implements Serializable{

    private static final long serialVersionUID = 1L;

    public enum StatusLeitura {
        NAO_INICIADO,
        EM_ANDAMENTO,
        FINALIZADO
    }

    private String titulo;
    private String autor;
    private String genero;
    private String opiniao;    
    private int avaliacao;     
    private StatusLeitura status;
    private String caminhoArquivo;  

    public Livro(String titulo, String autor, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.status = StatusLeitura.NAO_INICIADO;
        this.avaliacao = 0;
        this.opiniao = "";
        this.caminhoArquivo = null;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public String getOpiniao() {
        return opiniao;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public StatusLeitura getStatus() {
        return status;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    // Setters
    public void setOpiniao(String opiniao) {
        this.opiniao = opiniao;
    }

    public void setAvaliacao(int avaliacao) {
        if (avaliacao < 0) avaliacao = 0;
        if (avaliacao > 5) avaliacao = 5;
        this.avaliacao = avaliacao;
    }

    public void setStatus(StatusLeitura status) {
        this.status = status;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
    
    //Remover livro
    public void remover(Livro livro) {
        livros.remove(livro);
        salvar();
    }
}