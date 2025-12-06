package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import model.Livro;
import repository.bancoLivros;

public class DetalhesLivroFrame extends JFrame {

    private Livro livro;
    private bancoLivros banco;

    public DetalhesLivroFrame(Livro livro, bancoLivros banco) {
        this.livro = livro;
        this.banco = banco;

        setTitle("Detalhes do Livro");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.decode("#e8e8e8"));

        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBounds(120, 40, 650, 450);
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        add(painel);

        // CAPA
        JLabel capaLabel = new JLabel();
        capaLabel.setOpaque(true);
        capaLabel.setBackground(Color.LIGHT_GRAY);
        capaLabel.setBounds(40, 40, 200, 300);
        capaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        capaLabel.setVerticalAlignment(SwingConstants.CENTER);

        if (livro.getCaminhoCapa() != null) {
            ImageIcon img = new ImageIcon(
                new ImageIcon(livro.getCaminhoCapa()).getImage()
                .getScaledInstance(200, 300, Image.SCALE_SMOOTH)
            );
            capaLabel.setIcon(img);
            capaLabel.setText("");
        } else {
            capaLabel.setText("Sem capa");
        }
        painel.add(capaLabel);

        // TÍTULO
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(270, 40, 200, 30);
        painel.add(lblTitulo);

        JLabel tituloValor = new JLabel(livro.getTitulo());
        tituloValor.setFont(new Font("Segoe UI", Font.PLAIN, 19));
        tituloValor.setBounds(270, 75, 350, 30);
        painel.add(tituloValor);

        // AUTOR
        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblAutor.setBounds(270, 130, 200, 30);
        painel.add(lblAutor);

        JLabel autorValor = new JLabel(livro.getAutor());
        autorValor.setFont(new Font("Segoe UI", Font.PLAIN, 19));
        autorValor.setBounds(270, 165, 350, 30);
        painel.add(autorValor);

        // GÊNERO
        JLabel lblGenero = new JLabel("Gênero:");
        lblGenero.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblGenero.setBounds(270, 220, 200, 30);
        painel.add(lblGenero);

        JLabel generoValor = new JLabel(livro.getGenero());
        generoValor.setFont(new Font("Segoe UI", Font.PLAIN, 19));
        generoValor.setBounds(270, 255, 350, 30);
        painel.add(generoValor);

        // BOTÃO REMOVER
        JButton btnRemover = new JButton("Remover Livro");
        btnRemover.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnRemover.setBackground(new Color(200, 50, 50));
        btnRemover.setForeground(Color.WHITE);
        btnRemover.setFocusPainted(false);
        btnRemover.setBounds(330, 350, 220, 45);
        painel.add(btnRemover);

        btnRemover.addActionListener(e -> removerLivro());

        // BOTÃO VOLTAR
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnVoltar.setBackground(Color.decode("#d1d1d1"));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBounds(200, 350, 120, 45);
        painel.add(btnVoltar);

        btnVoltar.addActionListener(e -> dispose());
    }

    private void removerLivro() {
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente remover este livro?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            banco.remover(livro);
            JOptionPane.showMessageDialog(this, "Livro removido com sucesso!");
            dispose();
        }
    }
} 