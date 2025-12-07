package view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent; // Para o ActionListener
import model.Livro;
import repository.bancoLivros;

public class DetalhesLivroFrame extends JFrame {

    private Livro livro;
    private bancoLivros banco;
    private TelaPrincipalFrame telaPrincipal;

    // Campos de edição
    private JTextArea campoOpiniao;
    private JComboBox<String> comboStatus;
    private JComboBox<Integer> comboAvaliacao;

    public DetalhesLivroFrame(Livro livro, bancoLivros banco, TelaPrincipalFrame telaPrincipal) {
        this.livro = livro;
        this.banco = banco;
        this.telaPrincipal = telaPrincipal;

        setTitle("Detalhes: " + livro.getTitulo());
        setSize(750, 600); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.decode("#f5f4f0")); 

        setLayout(new BorderLayout());
        
        JPanel painelDetalhes = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelDetalhes.setBackground(Color.decode("#f5f4f0"));
        
        JLabel capaLabel = criarPainelCapa();
        painelDetalhes.add(capaLabel);
        
        JPanel infoPanel = criarPainelInfo();
        painelDetalhes.add(infoPanel);

        add(painelDetalhes, BorderLayout.CENTER);
        
        // Adiciona o painel de botões no rodapé
        add(criarPainelBotoes(), BorderLayout.SOUTH); 
    }

    private JLabel criarPainelCapa() {
        JLabel capaLabel = new JLabel();
        capaLabel.setPreferredSize(new Dimension(180, 270));
        capaLabel.setOpaque(true);
        capaLabel.setBackground(Color.LIGHT_GRAY);
        capaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        if (livro.getCaminhoArquivo() != null) {
            try {
                ImageIcon icon = new ImageIcon(livro.getCaminhoArquivo());
                Image img = icon.getImage().getScaledInstance(180, 270, Image.SCALE_SMOOTH);
                capaLabel.setIcon(new ImageIcon(img));
                capaLabel.setText("");
            } catch (Exception ignored) {
                 capaLabel.setText("Capa não encontrada");
            }
        } else {
            capaLabel.setText("Sem Capa");
        }
        return capaLabel;
    }

    private JPanel criarPainelInfo() {
        JPanel info = new JPanel();
        info.setPreferredSize(new Dimension(480, 500)); 
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.decode("#f5f4f0"));
        
        // Título e Autor
        JLabel titulo = new JLabel("<html><p style='width:350px'><b>" + livro.getTitulo() + "</b></p></html>");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(titulo);
        info.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel autor = new JLabel("Por: " + livro.getAutor());
        autor.setFont(new Font("SansSerif", Font.ITALIC, 16));
        autor.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(autor);
        info.add(Box.createRigidArea(new Dimension(0, 20)));

        // Gênero
        adicionarLabelCampo(info, "Gênero:", livro.getGenero());

        
        // 1. Status de Leitura
        adicionarLabel(info, "Status:");
        comboStatus = new JComboBox<>(new String[]{
            "NAO INICIADO", "EM ANDAMENTO", "FINALIZADO"
        });
        comboStatus.setSelectedItem(livro.getStatus().toString());
        configurarCampo(comboStatus);
        info.add(comboStatus);

        // 2. Avaliação (Estrelas)
        adicionarLabel(info, "Avaliação (0-5):");
        comboAvaliacao = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5});
        comboAvaliacao.setSelectedItem(livro.getAvaliacao());
        configurarCampo(comboAvaliacao);
        info.add(comboAvaliacao);

        // 3. Opinião
        adicionarLabel(info, "Opinião:");
        campoOpiniao = new JTextArea(livro.getOpiniao());
        campoOpiniao.setLineWrap(true);
        campoOpiniao.setWrapStyleWord(true);
        campoOpiniao.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        campoOpiniao.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JScrollPane scrollOpiniao = new JScrollPane(campoOpiniao);
        scrollOpiniao.setPreferredSize(new Dimension(450, 100));
        scrollOpiniao.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(scrollOpiniao);
        
        return info;
    }

    // Método para configurar o painel de botões Salvar e Remover
    private JPanel criarPainelBotoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        painelBotoes.setBackground(Color.decode("#f5f4f0"));
        
        // Botão Salvar Alterações
        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnSalvar.setBackground(new Color(70, 130, 180)); // Azul
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setPreferredSize(new Dimension(200, 45));
        btnSalvar.addActionListener(this::acaoSalvarAlteracoes);
        painelBotoes.add(btnSalvar);
        
        // Botão Remover Livro
        JButton btnRemover = new JButton("Remover Livro");
        btnRemover.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnRemover.setBackground(new Color(200, 70, 70)); // Vermelho
        btnRemover.setForeground(Color.WHITE);
        btnRemover.setPreferredSize(new Dimension(200, 45));
        btnRemover.addActionListener(this::acaoRemoverLivro);
        painelBotoes.add(btnRemover);
        
        return painelBotoes;
    }

    // Lógica para salvar as alterações
    private void acaoSalvarAlteracoes(ActionEvent e) {
        try {
            // Atualiza o objeto Livro com os novos valores
            livro.setOpiniao(campoOpiniao.getText());
            livro.setAvaliacao((Integer) comboAvaliacao.getSelectedItem());
            
            String statusSelecionado = (String) comboStatus.getSelectedItem();
            livro.setStatus(Livro.StatusLeitura.valueOf(statusSelecionado));

            // Salva as mudanças no banco de dados (persiste no arquivo)
            banco.salvar(); 
            
            // Atualiza a tela principal (caso a avaliação ou status mudem, o card pode ser re-renderizado no futuro)
            telaPrincipal.atualizarAposNovoLivro();
            
            JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!");
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    // Lógica de remoção e confirmação
    private void acaoRemoverLivro(ActionEvent e) {
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja remover '" + livro.getTitulo() + "' da sua biblioteca?",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            banco.remover(livro); 
            telaPrincipal.atualizarAposNovoLivro(); 
            JOptionPane.showMessageDialog(this, "Livro removido com sucesso!");
            this.dispose(); 
        }
    }
    
    
    private void adicionarLabel(JPanel parent, String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(Box.createRigidArea(new Dimension(0, 10)));
        parent.add(label);
    }
    
    private <T extends JComponent> void configurarCampo(T componente) {
        componente.setFont(new Font("SansSerif", Font.PLAIN, 14));
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        componente.setMaximumSize(new Dimension(450, 30));
    }
    
    private void adicionarLabelCampo(JPanel parent, String labelText, String valueText) {
        JLabel label = new JLabel("<html><b>" + labelText + "</b> " + valueText + "</html>");
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(label);
        parent.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}