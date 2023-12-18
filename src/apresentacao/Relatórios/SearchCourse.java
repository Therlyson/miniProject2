package apresentacao.Relatórios;

import controller.Department;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SearchCourse extends JFrame {
    private Department department;
    JTextField field;
    public SearchCourse(Department department) {
        this.department = department;

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel title = new JLabel("RELATÓRIO POR CURSO");
        title.setBounds(280,3,3500,50);
        title.setFont(new Font("Arial", Font.BOLD, 19));
        title.setForeground(Color.DARK_GRAY);
        add(title);


        JLabel name = new JLabel("Nome do curso: ");
        name.setBounds(70, 90, 200, 50);
        name.setFont(new Font("Arial", Font.ITALIC, 18));
        add(name);

        field = new JTextField();
        field.setBounds(210,100,350,30);
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
        String nomeCurso = field.getText();
        if(!nomeCurso.isEmpty() && department.pesquisaPorCurso(nomeCurso) != null) {
            dispose();
            new CourseRequests(department, nomeCurso);
        } else {
            JOptionPane.showMessageDialog(null, "Ops, nenhuma solicitação encontrada com esse nome adicionadas a uma sala de aula!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnVoltar(ActionEvent actionEvent) {
        this.dispose();
    }
}