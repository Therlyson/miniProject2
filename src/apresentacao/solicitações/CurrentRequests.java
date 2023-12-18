package apresentacao.solicitações;

import controller.Department;
import model.solicitações.EventualRequest;
import model.solicitações.FixedRequest;
import model.solicitações.Request;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CurrentRequests extends JFrame {
    Department department;
    public CurrentRequests(Department department){
        this.department = department;

        setTitle("Solicitações atuais");
        setSize(1130, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel text = new JLabel("Todas as solicitações cadastradas no atual momento");
        text.setBounds(3, 5, 1000, 50);
        text.setFont(new Font("Arial", Font.ITALIC, 18));
        add(text);

        JButton voltar = new JButton("Voltar");
        voltar.setBounds(1000, 510, 80, 30);
        voltar.setFont(new Font("Arial", Font.BOLD, 15));
        voltar.setForeground(new Color(255, 255, 255));
        voltar.setBackground(new Color(77, 81, 121));
        add(voltar);
        voltar.addActionListener(this::btnVoltar);

        exibirInformacoes();
        setLayout(null);
        setVisible(true);
    }


    private void btnVoltar(ActionEvent actionEvent) {
        this.dispose();
    }

    private void exibirInformacoes() {
        List<Request> solicitacoes = department.readRequestsCurrent();

        // Nomes das colunas
        String[] columnNames = {"Tipo solicitação", "Ano", "Semestre", "Curso", "Disciplina/Finalidade", "Vagas",
                "Horário", "Data Inicial", "Data Final"};
        ArrayList<Object[]> data = new ArrayList<>();

        for (Request solicitacao : solicitacoes) {
            if (solicitacao instanceof FixedRequest) {
                FixedRequest fixed = (FixedRequest) solicitacao;
                data.add(new Object[]{"FIXA", fixed.getAno(), fixed.getSemestre(), fixed.getCurso(),
                        fixed.getDisciplina(), fixed.getVagas(), fixed.getHorario().getHorarioSigaa(),
                        "", ""});
            } else if (solicitacao instanceof EventualRequest) {
                EventualRequest eventual = (EventualRequest) solicitacao;
                data.add(new Object[]{"EVENTUAL", eventual.getAno(), eventual.getSemestre(), eventual.getCurso(),
                        eventual.getPurpose(), eventual.getVagas(), eventual.getHorario().getHorarioSigaa(),
                        eventual.getInitial_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        eventual.getFinal_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))});
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data.toArray(new Object[0][0]), columnNames);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.ITALIC, 15));
        table.setBackground(Color.CYAN);
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1100, 450));
        scrollPane.setBounds(10, 45, 1100, 450);
        add(scrollPane);
    }
}
