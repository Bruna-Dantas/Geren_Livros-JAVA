package view;

import repository.UsuarioRepository;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroFrame extends JFrame {

    public CadastroFrame(UsuarioRepository usuarioRepository) {

        // Configuração básica da janela
        setTitle("Criar Nova Conta");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // vamos posicionar manualmente para ficar igual ao design

        // ===== PAINEL DE FUNDO =====
        JPanel fundo = new JPanel();
        fundo.setBackground(new Color(108, 119, 84)); // verde oliva
        fundo.setBounds(0, 0, 900, 550);
        fundo.setLayout(null);
        add(fundo);

        // ===== CARTÃO BRANCO COM CANTOS ARREDONDADOS =====
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 244, 240)); // branco gelo
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        card.setOpaque(false);
        card.setLayout(null);
        card.setBounds(50, 30, 800, 480);
        fundo.add(card);

        // ===== TÍTULO =====
        JLabel titulo = new JLabel("Criar uma conta nova");
        titulo.setBounds(60, 40, 400, 40);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(new Color(92, 74, 50)); // marrom
        card.add(titulo);

        JLabel loginLink = new JLabel("Já cadastrado? Faça Login");
        loginLink.setBounds(60, 80, 300, 25);
        loginLink.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginLink.setForeground(new Color(92, 74, 50));
        card.add(loginLink);

        // ===== CAMPOS =====
        JLabel lblUsuario = new JLabel("USUÁRIO");
        lblUsuario.setBounds(60, 130, 200, 20);
        lblUsuario.setForeground(new Color(92, 74, 50));
        lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(lblUsuario);

        JTextField campoUsuario = new JTextField();
        campoUsuario.setBounds(60, 150, 300, 35);
        card.add(campoUsuario);

        JLabel lblEmail = new JLabel("EMAIL");
        lblEmail.setBounds(60, 200, 200, 20);
        lblEmail.setForeground(new Color(92, 74, 50));
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(lblEmail);

        JTextField campoEmail = new JTextField();
        campoEmail.setBounds(60, 220, 300, 35);
        card.add(campoEmail);

        JLabel lblSenha = new JLabel("SENHA");
        lblSenha.setBounds(60, 270, 200, 20);
        lblSenha.setForeground(new Color(92, 74, 50));
        lblSenha.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(lblSenha);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setBounds(60, 290, 300, 35);
        card.add(campoSenha);

        JLabel lblData = new JLabel("DATA DE NASCIMENTO");
        lblData.setBounds(60, 340, 300, 20);
        lblData.setForeground(new Color(92, 74, 50));
        lblData.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(lblData);

        String[] datas = {"Select", "2000", "2001", "2002", "2003", "2004"};
        JComboBox<String> comboData = new JComboBox<>(datas);
        comboData.setBounds(60, 360, 300, 35);
        card.add(comboData);

        // ===== BOTÃO CADASTRAR =====
        JButton btnCadastrar = new JButton("CADASTRAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Cor do botão
                g2.setColor(new Color(164, 125, 98)); // marrom claro
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                super.paintComponent(g);
            }
        };
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCadastrar.setContentAreaFilled(false);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBounds(60, 415, 180, 40);
        card.add(btnCadastrar);

        // ===== PAINEL DA IMAGEM =====
        JPanel painelImagem = new JPanel();
        painelImagem.setBounds(400, 0, 400, 480);
        painelImagem.setLayout(null);

        // A imagem você adiciona aqui depois:
        // JLabel img = new JLabel(new ImageIcon("caminho/para/imagem.png"));
        // img.setBounds(0, 0, 400, 480);
        // painelImagem.add(img);

        card.add(painelImagem);

        // ===== FUNÇÃO DO BOTÃO =====
        btnCadastrar.addActionListener((ActionEvent e) -> {

            String nome = campoUsuario.getText().trim();
            String email = campoEmail.getText().trim();
            String senha = new String(campoSenha.getPassword());

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            if (usuarioRepository.emailJaExiste(email)) {
                JOptionPane.showMessageDialog(this, "Esse email já está cadastrado.");
                return;
            }

            usuarioRepository.adicionar(new Usuario(nome, email, senha));
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");

            // voltar para a tela de login
            new LoginFrame(usuarioRepository).setVisible(true);
            dispose();
        });
    }
}