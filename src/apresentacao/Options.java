package apresentacao;
import apresentacao.Relatórios.SearchCourse;
import apresentacao.Relatórios.SearchSpace;
import apresentacao.alocação.RegisterEventual;
import apresentacao.alocação.RegisterFixed;
import apresentacao.solicitações.CurrentRequests;
import apresentacao.solicitações.GeneralRequests;
import controller.Department;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Options extends JFrame {
    Department department = new Department();
    public Options(String nomeUsuario) {
        setTitle("Funcionalidades - Bem-vindo, " + nomeUsuario + "!");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("OPÇÕES DISPONIVEIS: ");
        title.setBounds(20,20,3000,50);
        title.setFont(new Font("Arial", Font.ITALIC, 19));
        title.setForeground(Color.DARK_GRAY);
        add(title);

        JButton btnAtuais = new JButton("Ver solicitações atuais");
        btnAtuais.setBounds(85, 90, 250, 35);
        btnAtuais.setFont(new Font("Arial", Font.BOLD, 15));
        btnAtuais.setForeground(new Color(5, 4, 4));
        add(btnAtuais);
        btnAtuais.addActionListener(this::btnAtuaisActionPerformed);

        JButton btnTotais = new JButton("Ver solicitações totais");
        btnTotais.setBounds(85, 170, 250, 35);
        btnTotais.setFont(new Font("Arial", Font.BOLD, 15));
        btnTotais.setForeground(new Color(5, 4, 4));
        add(btnTotais);
        btnTotais.addActionListener(this::btnTotaisActionPerfomed);

        JButton btnAlocaFixa = new JButton("Cadastrar solicitação fixa");
        btnAlocaFixa.setBounds(85, 250, 250, 35);
        btnAlocaFixa.setFont(new Font("Arial", Font.BOLD, 15));
        btnAlocaFixa.setForeground(new Color(5, 4, 4));
        add(btnAlocaFixa);
        btnAlocaFixa.addActionListener(this::btnAlocarFixaActionPerformed);

        JButton btnAlocaEventual = new JButton("Cadastrar solicitação eventual");
        btnAlocaEventual.setBounds(85, 330, 250, 35);
        btnAlocaEventual.setFont(new Font("Arial", Font.BOLD, 15));
        btnAlocaEventual.setForeground(new Color(5, 4, 4));
        add(btnAlocaEventual);
        btnAlocaEventual.addActionListener(this::btnAlocarEventualActionPerformed);

        JButton btnPesqCurso = new JButton("Pesquisar solicitação por curso");
        btnPesqCurso.setBounds(420, 90, 260, 35);
        btnPesqCurso.setFont(new Font("Arial", Font.BOLD, 15));
        btnPesqCurso.setForeground(new Color(5, 4, 4));
        add(btnPesqCurso);
        btnPesqCurso.addActionListener(this::btnCourseActionPerfomed);

        JButton btnPesqSpace = new JButton("Pesquisar solicitação por local");
        btnPesqSpace.setBounds(420, 170, 260, 35);
        btnPesqSpace.setFont(new Font("Arial", Font.BOLD, 15));
        btnPesqSpace.setForeground(new Color(5, 4, 4));
        add(btnPesqSpace);
        btnPesqSpace.addActionListener(this::btnSpaceActionPerfomed);

        JButton sair = new JButton("sair");
        sair.setBounds(660, 400, 80, 30);
        sair.setFont(new Font("Arial", Font.BOLD, 15));
        sair.setForeground(new Color(255, 255, 255));
        sair.setBackground(new Color(226, 13, 13));
        add(sair);
        sair.addActionListener(this::btnSaindo);

        setVisible(true);
    }

    private void btnAlocarEventualActionPerformed(ActionEvent actionEvent) {
        new RegisterEventual(department, "eventual");
    }

    private void btnAlocarFixaActionPerformed(ActionEvent actionEvent) {
        new RegisterFixed(department, "fixa");
    }

    private void btnSpaceActionPerfomed(ActionEvent actionEvent) {
        new SearchSpace(department);
    }

    private void btnCourseActionPerfomed(ActionEvent actionEvent) {
        new SearchCourse(department);
    }

    private void btnTotaisActionPerfomed(ActionEvent actionEvent) {
        if(department.readRequestsGeneral() != null){
            new GeneralRequests(department);
        }else{
            JOptionPane.showMessageDialog(null, "Ops, nenhuma solicitação cadastrada!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnAtuaisActionPerformed(ActionEvent actionEvent) {
        if(department.readRequestsCurrent() != null){
            new CurrentRequests(department);
        }else{
            JOptionPane.showMessageDialog(null, "Ops, nenhuma solicitação cadastrada no atual momento!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnSaindo(ActionEvent actionEvent) {
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Options("RYAN"));
    }
}