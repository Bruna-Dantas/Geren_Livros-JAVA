package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import model.Usuario;
import repository.bancoLivros;
import repository.bancoUsuario;

public class CadastroFrame extends JFrame {

    private bancoUsuario bancoUsuarios;
    private bancoLivros bancoLivros; // para passar ao LoginFrame

    public CadastroFrame(bancoUsuario bancoUsuarios, bancoLivros bancoLivros) {
        this.bancoUsuarios = bancoUsuarios;
        this.bancoLivros = bancoLivros;

        setTitle("Criar Nova Conta");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Fundo igual ao LoginFrame
        JPanel background = new JPanel();
        background.setBackground(new Color(122, 140, 102));
        background.setLayout(null);
        add(background);

        // Card semelhante ao LoginFrame
        RoundedPanel card = new RoundedPanel(40);
        card.setBackground(Color.WHITE);
        card.setLayout(null);
        card.setBounds(50, 50, 800, 500);
        background.add(card);

        // Títulos
        JLabel titulo = new JLabel("Criar uma nova conta");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        titulo.setForeground(new Color(60, 60, 60));
        titulo.setBounds(80, 40, 500, 40);
        card.add(titulo);

        JLabel loginLink = new JLabel("Já possui conta? Faça login");
        loginLink.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginLink.setForeground(new Color(90, 120, 90));
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.setBounds(80, 80, 250, 20);
        card.add(loginLink);

        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Passa os dois bancos para o LoginFrame
                new LoginFrame(bancoUsuarios, bancoLivros).setVisible(true);
                dispose();
            }
        });

        // Labels e campos
        JLabel lUsuario = new JLabel("USUÁRIO");
        lUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lUsuario.setForeground(new Color(80, 80, 80));
        lUsuario.setBounds(80, 130, 200, 20);
        card.add(lUsuario);

        JTextField campoUsuario = new JTextField();
        campoUsuario.setBounds(80, 155, 260, 35);
        campoUsuario.setBorder(new LineBorder(new Color(180, 180, 180)));
        card.add(campoUsuario);

        JLabel lEmail = new JLabel("EMAIL");
        lEmail.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lEmail.setForeground(new Color(80, 80, 80));
        lEmail.setBounds(80, 205, 200, 20);
        card.add(lEmail);

        JTextField campoEmail = new JTextField();
        campoEmail.setBounds(80, 230, 260, 35);
        campoEmail.setBorder(new LineBorder(new Color(180, 180, 180)));
        card.add(campoEmail);

        JLabel lSenha = new JLabel("SENHA");
        lSenha.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lSenha.setForeground(new Color(80, 80, 80));
        lSenha.setBounds(80, 280, 200, 20);
        card.add(lSenha);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setBounds(80, 305, 260, 35);
        campoSenha.setBorder(new LineBorder(new Color(180, 180, 180)));
        card.add(campoSenha);

        // Botão de cadastro
        JButton btnCadastrar = new JButton("CADASTRAR");
        btnCadastrar.setBounds(80, 370, 260, 45);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnCadastrar.setBackground(new Color(118, 87, 67));
        btnCadastrar.setBorder(new RoundedBorder(20));
        card.add(btnCadastrar);

        // Imagem do lado direito
        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(420, 40, 340, 420);

        try {
            ImageIcon img = new ImageIcon(getClass().getResource("/imagens/girl.png"));
            Image scaled = img.getImage().getScaledInstance(340, 420, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            imgLabel.setText("Adicione sua imagem em /assets/cadastro.png");
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        card.add(imgLabel);

        // Ação do botão
        btnCadastrar.addActionListener(e -> {
            String nome = campoUsuario.getText().trim();
            String email = campoEmail.getText().trim();
            String senha = new String(campoSenha.getPassword());

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            if (bancoUsuarios.emailJaExiste(email)) {
                JOptionPane.showMessageDialog(this, "Esse email já está cadastrado.");
                return;
            }

            bancoUsuarios.adicionar(new Usuario(nome, email, senha));
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");

            // abre LoginFrame com os dois bancos
            new LoginFrame(bancoUsuarios, bancoLivros).setVisible(true);
            dispose();
        });

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
}