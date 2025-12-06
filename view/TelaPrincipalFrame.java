package view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import model.Livro;
import repository.bancoLivros;

public class TelaPrincipalFrame extends JFrame {

    private bancoLivros banco;
    private JTextField campoBusca;
    private JPanel painelLivros;

    // Paleta de cores igual ao LoginFrame
    private final Color VERDE = new Color(122, 140, 102);
    private final Color MARROM = new Color(118, 87, 67);
    private final Color BEGE = new Color(245, 244, 240);
    private final Color TEXTO = new Color(60, 60, 60);

    public TelaPrincipalFrame(bancoLivros banco) {
        this.banco = banco;

        setTitle("Minha Biblioteca");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        add(criarSidebar(), BorderLayout.WEST);
        add(criarAreaPrincipal(), BorderLayout.CENTER);

        atualizarGrid(banco.listarTodos());
    }

    // 🔹 SIDEBAR
    private JPanel criarSidebar() {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(200, 700));
        side.setBackground(VERDE);
        side.setLayout(null);

        JLabel foto = new JLabel("FOTO", SwingConstants.CENTER);
        foto.setBounds(50, 40, 100, 100);
        foto.setOpaque(true);
        foto.setBackground(new Color(180, 190, 170));
        foto.setForeground(Color.white);
        foto.setFont(new Font("SansSerif", Font.BOLD, 16));
        foto.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        side.add(foto);

        side.add(botaoSidebar("Meu Perfil", 180));
        side.add(botaoSidebar("Minha Biblioteca", 230));

        // 🎯 BOTÃO: ADICIONAR LIVROS
        JButton addBtn = botaoSidebar("Adicionar Livros", 280);
        addBtn.addActionListener(e -> new AdicionarLivroFrame(TelaPrincipalFrame.this, banco).setVisible(true));
        side.add(addBtn);

        side.add(botaoSidebar("Remover Livros", 330));
        side.add(botaoSidebar("Configurações", 380));

        JButton logout = new JButton("Logout");
        logout.setBounds(30, 600, 140, 40);
        logout.setFocusPainted(false);
        logout.setBackground(MARROM);
        logout.setForeground(Color.white);
        logout.setFont(new Font("SansSerif", Font.BOLD, 14));
        side.add(logout);

        return side;
    }

    private JButton botaoSidebar(String texto, int y) {
        JButton b = new JButton(texto);
        b.setBounds(20, y, 160, 35);
        b.setFocusPainted(false);
        b.setBackground(new Color(145, 160, 140));
        b.setForeground(Color.white);
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return b;
    }

    // 🔹 ÁREA PRINCIPAL
    private JPanel criarAreaPrincipal() {
        JPanel area = new JPanel(new BorderLayout());
        area.setBackground(BEGE);

        JPanel topo = new JPanel();
        topo.setLayout(null);
        topo.setPreferredSize(new Dimension(900, 80));
        topo.setBackground(BEGE);

        campoBusca = new JTextField();
        campoBusca.setBounds(30, 20, 300, 35);
        campoBusca.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoBusca.setBorder(BorderFactory.createLineBorder(VERDE));
        topo.add(campoBusca);

        JButton ordenar = new JButton("Ordenar por");
        ordenar.setBounds(350, 20, 140, 35);
        ordenar.setFocusPainted(false);
        ordenar.setBackground(MARROM);
        ordenar.setForeground(Color.WHITE);
        ordenar.setFont(new Font("SansSerif", Font.BOLD, 14));
        topo.add(ordenar);

        area.add(topo, BorderLayout.NORTH);

        JLabel titulo = new JLabel("Minhas Leituras");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(TEXTO);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 10));

        painelLivros = new JPanel();
        painelLivros.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelLivros.setBackground(BEGE);

        JScrollPane scroll = new JScrollPane(painelLivros);
        scroll.setBorder(null);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BEGE);
        wrapper.add(titulo, BorderLayout.NORTH);
        wrapper.add(scroll, BorderLayout.CENTER);

        return wrapper;
    }

    // 🔹 Atualiza o grid
    public void atualizarGrid(List<Livro> livros) {
        painelLivros.removeAll();
        painelLivros.add(criarCardAdicionar());

        for (Livro livro : livros) {
            painelLivros.add(criarCardLivro(livro));
        }

        painelLivros.revalidate();
        painelLivros.repaint();
    }

    // 🔹 Atualizar após adicionar livro
    public void atualizarAposNovoLivro() {
        atualizarGrid(banco.listarTodos());
    }

    // 🔹 CARD de adicionar
    private JPanel criarCardAdicionar() {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(120, 180));
        card.setBackground(new Color(220, 225, 210));
        card.setBorder(BorderFactory.createLineBorder(VERDE));
        card.setLayout(new BorderLayout());

        JLabel mais = new JLabel("+", SwingConstants.CENTER);
        mais.setFont(new Font("SansSerif", Font.BOLD, 40));
        mais.setForeground(MARROM);
        card.add(mais);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new AdicionarLivroFrame(TelaPrincipalFrame.this, banco).setVisible(true);
            }
        });

        return card;
    }

    // 🔹 Card de livro individual
    private JPanel criarCardLivro(Livro livro) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(120, 180));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(VERDE));
        card.setBackground(Color.WHITE);

        JPanel capa = new JPanel();
        capa.setBackground(new Color(200, 200, 200));

        if ( livro .getCaminhoArquivo() != null) {
            try {
                ImageIcon icon = new ImageIcon( livro .getCaminhoArquivo());
                Image img = icon.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);
                capa = new JPanel(new BorderLayout());
                capa.add(new JLabel(new ImageIcon(img)), BorderLayout.CENTER);
            } catch (Exception ignored) {}
        }

        JLabel titulo = new JLabel("<html><center>" + livro.getTitulo() + "</center></html>", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        titulo.setForeground(TEXTO);

        card.add(capa, BorderLayout.CENTER);
        card.add(titulo, BorderLayout.SOUTH);

        return card;
    }
}