package apresentacao.Relatórios;

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

public class SpaceRequests extends JFrame {
    String nomeSpace;
    Department department;
    public SpaceRequests(Department department, String nomeCurso){
        this.department = department;
        this.nomeSpace = nomeCurso;

        setTitle("Relatório por espaço físico");
        setSize(1080, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel name = new JLabel("Todas as solicitações presentes em "+ nomeSpace);
        name.setBounds(5, 5, 1000, 50);
        name.setFont(new Font("Arial", Font.ITALIC, 18));
        add(name);

        JButton voltar = new JButton("Voltar");
        voltar.setBounds(960, 500, 80, 30);
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

    private void exibirInformacoes(){
        ArrayList<Request> space = department.pesquisaPorEspaco(nomeSpace);

        // Nomes das colunas
        String[] columnNames = {"Tipo solicitação", "Ano", "Semestre", "Curso", "Vagas", "Horário", "Local",
                "Data de cadastro", "Disciplina/Finalidade", "Data Inicial", "Data Final"};
        ArrayList<Object[]> data = new ArrayList<>();

        for (Request r: space) {
            if (r instanceof FixedRequest) {
                FixedRequest fixed = (FixedRequest) r;
                data.add(new Object[]{"FIXA", fixed.getAno(), fixed.getSemestre(), fixed.getCurso(),
                        fixed.getVagas(), fixed.getHorario().getHorarioSigaa(), fixed.getEspaco().getName(),
                        fixed.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        fixed.getDisciplina(), "", ""});
            } else if (r instanceof EventualRequest) {
                EventualRequest eventual = (EventualRequest) r;
                data.add(new Object[]{"EVENTUAL", eventual.getAno(), eventual.getSemestre(), eventual.getCurso(),
                        eventual.getVagas(), eventual.getHorario().getHorarioSigaa(), eventual.getEspaco().getName(),
                        eventual.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        eventual.getPurpose(), eventual.getInitial_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        eventual.getFinal_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))});
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data.toArray(new Object[0][0]), columnNames);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.ITALIC, 15));
        table.setBackground(Color.CYAN);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(8, 45, 1050, 2000);
        add(scrollPane);
    }
}
