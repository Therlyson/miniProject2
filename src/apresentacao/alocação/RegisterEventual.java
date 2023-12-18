package apresentacao.alocação;

import controller.Department;
import model.Espaco;
import model.Horario;
import model.solicitações.EventualRequest;
import model.solicitações.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegisterEventual extends JFrame{
    Department department;
    JTextField fieldTipo, fieldCapacidade, fieldNome, fieldLocalizacao;
    JTextField fieldHorario, fieldAno, fieldSemestre, fieldCurso, fieldVagas, fieldData, fieldFinalidade, fieldDataInicial, fieldDataFinal;
    public RegisterEventual(Department department, String nome) {
        this.department = department;

        setTitle("Área de cadastro");
        setSize(1050, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("Cadastro de solicitação " + nome);
        title.setBounds(390,10,500,50);
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(Color.DARK_GRAY);
        add(title);

        JLabel space = new JLabel("Dados do espaço fisíco");
        space.setBounds(60,55,500,50);
        space.setFont(new Font("Arial", Font.BOLD, 19));
        space.setForeground(Color.blue);
        add(space);

        JLabel tipo = new JLabel("Tipo(auditório ou sala de aula): ");
        tipo.setBounds(60,100,500,50);
        tipo.setFont(new Font("Arial", Font.ITALIC, 17));
        add(tipo);
        fieldTipo = new JTextField();
        fieldTipo.setBounds(300,106,300,30);
        fieldTipo.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldTipo.setSelectionColor(Color.black);
        add(fieldTipo);

        JLabel capacidade = new JLabel("Capacidade: ");
        capacidade.setBounds(60,140,500,50);
        capacidade.setFont(new Font("Arial", Font.ITALIC, 17));
        add(capacidade);
        fieldCapacidade = new JTextField();
        fieldCapacidade.setBounds(160,146,300,30);
        fieldCapacidade.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldCapacidade.setSelectionColor(Color.black);
        add(fieldCapacidade);

        JLabel nomeS = new JLabel("Nome: ");
        nomeS.setBounds(60,180,500,50);
        nomeS.setFont(new Font("Arial", Font.ITALIC, 17));
        add(nomeS);
        fieldNome = new JTextField();
        fieldNome.setBounds(120,186,300,30);
        fieldNome.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldNome.setSelectionColor(Color.black);
        add(fieldNome);

        JLabel localizacao = new JLabel("Localização: ");
        localizacao.setBounds(60,220,500,50);
        localizacao.setFont(new Font("Arial", Font.ITALIC, 17));
        add(localizacao);
        fieldLocalizacao = new JTextField();
        fieldLocalizacao.setBounds(165,226,300,30);
        fieldLocalizacao.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldLocalizacao.setSelectionColor(Color.black);
        add(fieldLocalizacao);

        JLabel especificao = new JLabel("Outros dados");
        especificao.setBounds(60,300,500,50);
        especificao.setFont(new Font("Arial", Font.BOLD, 19));
        especificao.setForeground(Color.blue);
        add(especificao);

        JLabel horario = new JLabel("Horário: ");
        horario.setBounds(60,360,500,50);
        horario.setFont(new Font("Arial", Font.ITALIC, 17));
        add(horario);
        JLabel exemplo = new JLabel("Exemplo de escrita: 35T12");
        exemplo.setBounds(130,330,500,50);
        exemplo.setFont(new Font("Arial", Font.ITALIC, 12));
        add(exemplo);
        fieldHorario = new JTextField();
        fieldHorario.setBounds(130,366,300,30);
        fieldHorario.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldHorario.setSelectionColor(Color.black);
        add(fieldHorario);

        JLabel ano = new JLabel("Ano: ");
        ano.setBounds(60,400,500,50);
        ano.setFont(new Font("Arial", Font.ITALIC, 17));
        add(ano);
        fieldAno = new JTextField();
        fieldAno.setBounds(105,406,300,30);
        fieldAno.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldAno.setSelectionColor(Color.black);
        add(fieldAno);

        JLabel semestre = new JLabel("Semestre: ");
        semestre.setBounds(60,440,500,50);
        semestre.setFont(new Font("Arial", Font.ITALIC, 17));
        add(semestre);
        fieldSemestre = new JTextField();
        fieldSemestre.setBounds(150,446,300,30);
        fieldSemestre.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldSemestre.setSelectionColor(Color.black);
        add(fieldSemestre);

        JLabel curso = new JLabel("Curso: ");
        curso.setBounds(60,480,500,50);
        curso.setFont(new Font("Arial", Font.ITALIC, 17));
        add(curso);
        fieldCurso = new JTextField();
        fieldCurso.setBounds(120,486,300,30);
        fieldCurso.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldCurso.setSelectionColor(Color.black);
        add(fieldCurso);

        JLabel vagas = new JLabel("Quantidade de vagas: ");
        vagas.setBounds(60,520,500,50);
        vagas.setFont(new Font("Arial", Font.ITALIC, 17));
        add(vagas);
        fieldVagas = new JTextField();
        fieldVagas.setBounds(230,526,300,30);
        fieldVagas.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldVagas.setSelectionColor(Color.black);
        add(fieldVagas);

        JLabel data = new JLabel("Data atual: ");
        data.setBounds(560,360,500,50);
        data.setFont(new Font("Arial", Font.ITALIC, 17));
        add(data);
        JLabel exData = new JLabel("Ex: 17/12/2023");
        exData.setBounds(650,330,500,50);
        exData.setFont(new Font("Arial", Font.ITALIC, 12));
        add(exData);
        fieldData = new JTextField();
        fieldData.setBounds(650,366,300,30);
        fieldData.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldData.setSelectionColor(Color.black);
        add(fieldData);


        JLabel exemploFinali = new JLabel("Ex: seminários, defesas TCC, palestras, provas");
        exemploFinali.setBounds(650,379,500,50);
        exemploFinali.setFont(new Font("Arial", Font.ITALIC, 12));
        add(exemploFinali);
        JLabel finalidade = new JLabel("Finalidade: ");
        finalidade.setBounds(560,405,500,50);
        finalidade.setFont(new Font("Arial", Font.ITALIC, 17));
        add(finalidade);
        fieldFinalidade = new JTextField();
        fieldFinalidade.setBounds(650,411,300,30);
        fieldFinalidade.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldFinalidade.setSelectionColor(Color.black);
        add(fieldFinalidade);

        JLabel dataInicial = new JLabel("Data inicial do evento: ");
        dataInicial.setBounds(560,445,500,50);
        dataInicial.setFont(new Font("Arial", Font.ITALIC, 17));
        add(dataInicial);
        fieldDataInicial = new JTextField();
        fieldDataInicial.setBounds(730,451,250,30);
        fieldDataInicial.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldDataInicial.setSelectionColor(Color.black);
        add(fieldDataInicial);

        JLabel dataFinal = new JLabel("Data final do evento: ");
        dataFinal.setBounds(560,485,500,50);
        dataFinal.setFont(new Font("Arial", Font.ITALIC, 17));
        add(dataFinal);
        fieldDataFinal = new JTextField();
        fieldDataFinal.setBounds(720,491,250,30);
        fieldDataFinal.setFont(new Font("Arial", Font.ITALIC, 17));
        fieldDataFinal.setSelectionColor(Color.black);
        add(fieldDataFinal);


        JButton btnSalvar = new JButton("salvar");
        btnSalvar.setBounds(750, 530, 100, 30);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 15));
        btnSalvar.setForeground(new Color(5, 4, 4));
        btnSalvar.setBackground(new Color(104, 157, 240));
        add(btnSalvar);
        btnSalvar.addActionListener(this::btnSalvarActionPerformed);

        JButton voltar = new JButton("voltar");
        voltar.setBounds(880, 530, 100, 30);
        voltar.setFont(new Font("Arial", Font.BOLD, 15));
        voltar.setForeground(new Color(255, 255, 255));
        voltar.setBackground(new Color(226, 13, 13));
        add(voltar);
        voltar.addActionListener(this::btnVoltarActionPerfomed);

        setVisible(true);
    }

    private void btnVoltarActionPerfomed(ActionEvent actionEvent) {
        this.dispose();
    }

    private void btnSalvarActionPerformed(ActionEvent actionEvent){
        String tipo = fieldTipo.getText();
        String capacidade = fieldCapacidade.getText();
        String nome = fieldNome.getText();
        String Localizacao = fieldLocalizacao.getText();
        String horario = fieldHorario.getText();
        String Ano = fieldAno.getText();
        String semestre = fieldSemestre.getText();
        String curso = fieldCurso.getText();
        String qtd_vagas = fieldVagas.getText();
        String dataAtual = fieldData.getText();
        String finalidade = fieldFinalidade.getText();
        String dataInicio = fieldDataInicial.getText();
        String dataFim = fieldDataFinal.getText();

        if(!tipo.isEmpty() && !capacidade.isEmpty() && !nome.isEmpty() && !Localizacao.isEmpty() && !horario.isEmpty()
                && !Ano.isEmpty() && !semestre.isEmpty() && !curso.isEmpty() && !qtd_vagas.isEmpty() && !dataAtual.isEmpty()
                && !finalidade.isEmpty() && !dataInicio.isEmpty() && !dataFim.isEmpty()){
            try{
                int capacity = Integer.parseInt(capacidade);
                Espaco espacoFisico = new Espaco(tipo, capacity, nome, Localizacao);
                Horario h = new Horario(horario);
                int ano1 = Integer.parseInt(Ano);
                int vagas1 = Integer.parseInt(qtd_vagas);
                LocalDate data = LocalDate.parse(dataAtual, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dataI = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dataF = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Request eventualRequest = new EventualRequest(ano1, semestre, curso, vagas1, h, espacoFisico, data, finalidade, dataI, dataF);
                boolean alocar = department.allocateRequest(eventualRequest);
                if(alocar){
                    JOptionPane.showMessageDialog(null, "Solicitação cadastrada com sucesso.", "cadastro", JOptionPane.PLAIN_MESSAGE);
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "ERRO! Não foi possivel cadastrar essa solicitação.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "ERRO! Escreva um número inteiro válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }catch (DateTimeParseException e){
                JOptionPane.showMessageDialog(null, "Por favor, insira uma data no formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Ops, faltou inserir alguma informação.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new RegisterEventual(new Department(), "eventual"));
//    }
}
