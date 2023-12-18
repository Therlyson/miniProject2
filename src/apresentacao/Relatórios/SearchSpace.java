package apresentacao.Relatórios;

import controller.Department;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SearchSpace extends JFrame {
    private Department department;
    JTextField field;
    public SearchSpace(Department department) {
        this.department = department;

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel title = new JLabel("RELATÓRIO POR ESPAÇO FÍSICO");
        title.setBounds(250, 3, 3500, 50);
        title.setFont(new Font("Arial", Font.BOLD, 19));
        title.setForeground(Color.DARK_GRAY);
        add(title);

        JLabel name = new JLabel("Sala de aula ou auditório: ");
        name.setBounds(70, 90, 300, 50);
        name.setFont(new Font("Arial", Font.ITALIC, 18));
        add(name);

        field = new JTextField();
        field.setBounds(290, 100, 350, 30);
        field.setFont(new Font("Arial", Font.ITALIC, 17));
        field.setSelectionColor(Color.black);
        add(field);

        JButton pesq = new JButton("pesquisar");
        pesq.setBounds(320, 170, 150, 30);
        pesq.setFont(new Font("Arial", Font.BOLD, 15));
        pesq.setForeground(new Color(255, 255, 255));
        pesq.setBackground(new Color(4, 101, 182));
        add(pesq);
        pesq.addActionListener(this::btnPesquisar);

        JButton voltar = new JButton("Voltar");
        voltar.setBounds(660, 400, 80, 30);
        voltar.setFont(new Font("Arial", Font.BOLD, 15));
        voltar.setForeground(new Color(255, 255, 255));
        voltar.setBackground(new Color(77, 81, 121));
        add(voltar);
        voltar.addActionListener(this::btnVoltar);


        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void btnPesquisar(ActionEvent actionEvent) {
        String nomeLocal = field.getText();
        if (!nomeLocal.isEmpty() && department.pesquisaPorEspaco(nomeLocal) != null) {
            dispose();
            new SpaceRequests(department, nomeLocal);
        } else {
            JOptionPane.showMessageDialog(null, "Ops, nenhuma solicitação presente nesse espaço há uma semana!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnVoltar(ActionEvent actionEvent) {
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SearchSpace(new Department()));
    }
}
