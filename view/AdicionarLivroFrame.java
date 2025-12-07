package view;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Livro; // Importação explícita para o ActionListener
import repository.bancoLivros;

public class AdicionarLivroFrame extends JFrame {

    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JComboBox<String> campoGenero;

    private JLabel previewCapa;
    private String caminhoCapa = null;

    private bancoLivros banco;
    private TelaPrincipalFrame tela; 

    public AdicionarLivroFrame(TelaPrincipalFrame tela, bancoLivros banco) {
        this.tela = tela;
        this.banco = banco;

        setTitle("Adicionar Livro");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#e8e8e8"));

        JPanel painel = new JPanel();
        painel.setBackground(Color.WHITE);
        painel.setPreferredSize(new Dimension(650, 450));
        painel.setLayout(null);
        painel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        add(painel, BorderLayout.CENTER);
        
        // Título
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblTitulo.setBounds(50, 40, 200, 30);
        painel.add(lblTitulo);

        campoTitulo = new JTextField();
        campoTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        campoTitulo.setBounds(50, 75, 300, 35);
        painel.add(campoTitulo);

        // Autor
        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblAutor.setBounds(50, 120, 200, 30);
        painel.add(lblAutor);

        campoAutor = new JTextField();
        campoAutor.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        campoAutor.setBounds(50, 155, 300, 35);
        painel.add(campoAutor);

        // Gênero
        JLabel lblGenero = new JLabel("Gênero:");
        lblGenero.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblGenero.setBounds(50, 200, 200, 30);
        painel.add(lblGenero);

        campoGenero = new JComboBox<>(new String[]{
            "Fantasia", "Romance", "Suspense", "Ficção", "Terror", "Aventura", "Outro"
        });
        campoGenero.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        campoGenero.setBounds(50, 235, 300, 35);
        painel.add(campoGenero);

        // Preview da capa
        JLabel lblCapa = new JLabel("Capa:");
        lblCapa.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblCapa.setBounds(420, 40, 200, 30);
        painel.add(lblCapa);

        previewCapa = new JLabel("Sem capa", SwingConstants.CENTER);
        previewCapa.setOpaque(true);
        previewCapa.setBackground(Color.LIGHT_GRAY);
        previewCapa.setBounds(420, 75, 180, 250);
        painel.add(previewCapa);

        // Botão selecionar imagem
        JButton btnSelecionar = new JButton("Selecionar Capa");
        btnSelecionar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnSelecionar.setBounds(390, 340, 240, 45);
        btnSelecionar.setBackground(Color.decode("#d1d1d1"));
        btnSelecionar.setFocusPainted(false);
        painel.add(btnSelecionar);

        // Conecta o método escolherImagem() ao botão
        btnSelecionar.addActionListener(e -> escolherImagem());

        // Botão adicionar livro
        JButton btnAdicionar = new JButton("Adicionar Livro");
        btnAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnAdicionar.setBackground(new Color(70, 130, 180));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setBounds(280, 410, 300, 50);
        btnAdicionar.setFocusPainted(false);
        painel.add(btnAdicionar);

        // Conecta o método adicionarLivro() ao botão
        btnAdicionar.addActionListener(e -> adicionarLivro());
    }

    private void escolherImagem() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filtro);

        int escolha = fileChooser.showOpenDialog(this);
        if (escolha == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            caminhoCapa = arquivo.getAbsolutePath();

            // Lógica para redimensionar e exibir a capa
            ImageIcon img = new ImageIcon(
                new ImageIcon(caminhoCapa).getImage().getScaledInstance(180, 250, Image.SCALE_SMOOTH)
            );
            previewCapa.setIcon(img);
            previewCapa.setText("");
        }
    }

    private void adicionarLivro() {
        String titulo = campoTitulo.getText();
        String autor = campoAutor.getText();
        String genero = campoGenero.getSelectedItem().toString();

        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Livro livro = new Livro(titulo, autor, genero);
            
            // Atribui o caminho da capa ao livro antes de adicionar ao banco.
            livro.setCaminhoArquivo(caminhoCapa); 
            banco.adicionar(livro);

            JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            tela.atualizarAposNovoLivro();
            this.dispose();


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}