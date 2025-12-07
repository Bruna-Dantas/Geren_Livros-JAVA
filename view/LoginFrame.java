package view;

import repository.bancoUsuario;
import repository.bancoLivros;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private bancoUsuario bancoUsuarios;
    private bancoLivros bancoLivros;

    public LoginFrame(bancoUsuario bancoUsuarios, bancoLivros bancoLivros) {
        this.bancoUsuarios = bancoUsuarios;
        this.bancoLivros = bancoLivros;
        initComponents();
    }

    private void initComponents() {
        setTitle("Login");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel background = new JPanel();
        background.setBackground(new Color(122, 140, 102));
        background.setLayout(null);
        add(background);

        RoundedPanel card = new RoundedPanel(40);
        card.setBackground(Color.WHITE);
        card.setLayout(null);
        card.setBounds(50, 50, 800, 500);
        background.add(card);

        JLabel titulo = new JLabel("Bem-vindo de volta");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        titulo.setForeground(new Color(60, 60, 60));
        titulo.setBounds(80, 40, 400, 40);
        card.add(titulo);

        JLabel subtitulo = new JLabel("Faça login para continuar");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(100, 100, 100));
        subtitulo.setBounds(80, 80, 300, 20);
        card.add(subtitulo);

        JLabel lUsuario = new JLabel("EMAIL");
        lUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lUsuario.setForeground(new Color(80, 80, 80));
        lUsuario.setBounds(80, 140, 200, 20);
        card.add(lUsuario);

        JTextField tUsuario = new JTextField();
        tUsuario.setBounds(80, 165, 260, 35);
        tUsuario.setBorder(new LineBorder(new Color(180, 180, 180)));
        card.add(tUsuario);

        JLabel lSenha = new JLabel("SENHA");
        lSenha.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lSenha.setForeground(new Color(80, 80, 80));
        lSenha.setBounds(80, 215, 200, 20);
        card.add(lSenha);

        JPasswordField tSenha = new JPasswordField();
        tSenha.setBounds(80, 240, 260, 35);
        tSenha.setBorder(new LineBorder(new Color(180, 180, 180)));
        card.add(tSenha);

        JButton btnLogin = new JButton("ENTRAR");
        btnLogin.setBounds(80, 305, 260, 45);
        btnLogin.setFocusPainted(false);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnLogin.setBackground(new Color(118, 87, 67));
        btnLogin.setBorder(new RoundedBorder(20));
        card.add(btnLogin);

        JLabel criarConta = new JLabel("Não tem conta? Cadastre-se");
        criarConta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        criarConta.setForeground(new Color(90, 120, 90));
        criarConta.setBounds(115, 390, 200, 20);
        criarConta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.add(criarConta);

        // Link para CadastroFrame
        criarConta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new CadastroFrame(bancoUsuarios, bancoLivros).setVisible(true);
                dispose();
            }
        });

        // Ação do botão LOGIN
        btnLogin.addActionListener(e -> {
            String email = tUsuario.getText();
            String senha = new String(tSenha.getPassword());

            if (email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (bancoUsuarios.autenticar(email, senha) != null) {
                JOptionPane.showMessageDialog(null,
                        "Login realizado com sucesso!");
                new TelaPrincipalFrame(bancoLivros).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(420, 40, 340, 420);

        try {
            ImageIcon img = new ImageIcon(getClass().getResource("/imagens/girl.png"));
            Image scaled = img.getImage().getScaledInstance(340, 420, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            imgLabel.setText("Adicione sua imagem em /assets/login.png");
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        card.add(imgLabel);

        setVisible(true);
    }

    // Painel arredondado
    class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int r) { this.radius = r; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }
    }

    // Borda arredondada
    class RoundedBorder extends LineBorder {
        private int arc;

        public RoundedBorder(int arc) {
            super(new Color(118, 87, 67), 1, true);
            this.arc = arc;
        }
    }

    public static void main(String[] args) {
        bancoUsuario usuarios = new bancoUsuario();
        bancoLivros livros = new bancoLivros();
        new LoginFrame(usuarios, livros);
    }
}