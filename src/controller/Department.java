package controller;

import model.Espaco;
import model.Horario;
import model.exceptions.ProjectExceptions;
import model.solicitações.EventualRequest;
import model.solicitações.FixedRequest;
import model.solicitações.Request;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Department {

    //Método para ler apenas as solicitações atuais, ou seja, enquanto o programa estiver em execução
    public List<Request> readRequestsCurrent(){
        String arquivo = "SolicitaçõesAtuais.txt";
        List<Request> requestList = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader(arquivo))){
            String line;
            while((line = bf.readLine()) != null){
                String[] separator = line.split(";");
                if(separator[0].equalsIgnoreCase("FIXA")){
                    Horario hr = new Horario(separator[5]);
                    Request rq = new FixedRequest(Integer.parseInt(separator[1]), separator[2], separator[3],
                            Integer.parseInt(separator[4]), hr, separator[6]);
                    requestList.add(rq);
                }
                else if(separator[0].equalsIgnoreCase("EVENTUAL")){
                    Horario hr = new Horario(separator[5]);
                    LocalDate initial_date = LocalDate.parse(separator[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate final_date = LocalDate.parse(separator[8], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Request rq = new EventualRequest(Integer.parseInt(separator[1]), separator[2], separator[3],
                            Integer.parseInt(separator[4]), hr, separator[6], initial_date, final_date);
                    requestList.add(rq);
                }
            }
            if(!requestList.isEmpty()){
                return requestList;
            }
            else{
                return null;
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    //Método que ler todas as solicitações feitas
    public Hashtable<String, Request> readRequestsGeneral(){
        String arquivo = "TodasSolicitações.txt";
        Hashtable<String, Request> hash = new Hashtable<>();

        try(BufferedReader bf = new BufferedReader(new FileReader(arquivo))){
            bf.readLine();
            String line;
            while((line = bf.readLine()) != null){
                String[] separator = line.split(";");
                if(separator[0].equalsIgnoreCase("FIXA")){
                    Horario hr = new Horario(separator[5]);
                    /*método que busca um local pelo nome dentro do arquivo "espacos.txt" e retorna um espaço com todas as
                    caracteristicas*/
                    Espaco espaco = buscarEspaco(separator[6]);
                    LocalDate data = LocalDate.parse(separator[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Request rq = new FixedRequest(Integer.parseInt(separator[1]), separator[2], separator[3],
                            Integer.parseInt(separator[4]), hr, espaco,data, separator[8]);
                    String key = separator[5].concat(separator[6]);
                    hash.put(key, rq);
                }
                else if(separator[0].equalsIgnoreCase("EVENTUAL")){
                    Horario hr = new Horario(separator[5]);
                    Espaco espaco = buscarEspaco(separator[6]);
                    LocalDate data = LocalDate.parse(separator[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate initial_date = LocalDate.parse(separator[9], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate final_date = LocalDate.parse(separator[10], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Request rq = new EventualRequest(Integer.parseInt(separator[1]), separator[2], separator[3],
                            Integer.parseInt(separator[4]), hr, espaco,data, separator[8], initial_date, final_date);
                    String key = separator[5].concat(separator[6]);
                    hash.put(key, rq);
                }
            }
            if(!hash.isEmpty()){
                return hash;
            }
            else{
                return null;
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public boolean allocateRequest(Request request){
        String arquivo = "TodasSolicitações.txt";
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(arquivo, true))){
            if(request instanceof FixedRequest){
                FixedRequest fixed = (FixedRequest) request;
                fixed.testEspace();  //testa se o espaço tem capacidade suficiente para a solicitação, se não retorna uma exceção
                alocarEspacoFisico(fixed.getEspaco()); //aloca um novo espaço se ele ainda não existe dentro do arquivo "espacos.txt"
                compararConflitos(fixed); //testa se não tem conflito de horários, se tiver dispara uma exceção
                bf.write("FIXA" + ";" + fixed.getAno() + ";" + fixed.getSemestre() + ";" + fixed.getCurso() + ";" +
                        fixed.getVagas() + ";" + fixed.getHorario().getHorarioSigaa() + ";" + fixed.getEspaco().getName() +
                        ";" + fixed.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ";" + fixed.getDisciplina());
            }
            else if(request instanceof EventualRequest){
                EventualRequest eventual = (EventualRequest) request;
                eventual.testEspace();
                alocarEspacoFisico(eventual.getEspaco());
                compararConflitos(eventual);
                bf.write("EVENTUAL" + ";" + eventual.getAno() + ";" + eventual.getSemestre() + ";" + eventual.getCurso() + ";" +
                        eventual.getVagas() + ";" + eventual.getHorario().getHorarioSigaa() + ";" +
                        eventual.getEspaco().getName() + ";" + eventual.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + ";" + eventual.getPurpose() + ";" + eventual.getInitial_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                        ";" + eventual.getFinal_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            bf.newLine();

            try(BufferedWriter bw = new BufferedWriter(new FileWriter("SolicitaçõesAtuais.txt", true))){
                if(request instanceof FixedRequest) {
                    FixedRequest fixed = (FixedRequest) request;
                    bw.write("FIXA" + ";" + fixed.getAno() + ";" + fixed.getSemestre() + ";" + fixed.getCurso() + ";" +
                            fixed.getVagas() + ";" + fixed.getHorario().getHorarioSigaa() +
                            ";" + fixed.getDisciplina());
                }
                else if(request instanceof EventualRequest) {
                    EventualRequest eventual = (EventualRequest) request;
                    bw.write("EVENTUAL" + ";" + eventual.getAno() + ";" + eventual.getSemestre() + ";" + eventual.getCurso() + ";" +
                            eventual.getVagas() + ";" + eventual.getHorario().getHorarioSigaa()
                            + ";" + eventual.getPurpose() + ";" + eventual.getInitial_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                            ";" + eventual.getFinal_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                bw.newLine();
                return true;
            }catch (IOException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }catch (ProjectExceptions e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    /* método necessário por limpar o contéudo do arquivo "SolicitaçõesAtuais.txt" com o intuito de
    sempre quando o programa for aberto esse arquivo ter apenas as solicitções alocadas no momento
    */
    public void clearFile(){
        try {
            FileWriter fileWriter = new FileWriter("SolicitaçõesAtuais.txt", false);
            fileWriter.close();  // Isso limpa o conteúdo do arquivo e o fecha
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao limpar o arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean verificarPorPartes(String one, String two){
        String[] separator = one.split("");
        /*Basicamente vai separar a primeira string, e verificar se alguma palavra da primeira string contém
        na segunda string passada como parãmetro, se contiver indica que tem um conflito*/
        for(String str: separator){
            if(two.contains(str)){
                return true;
            }
        }
        return false;
    }

    public void conflitoHorario(Request h1, Request h2) throws ProjectExceptions {
        /*se o método verificarLetrasEmComum for true nos dias, turnos e horas, e o espaço também for o mesmo
        isso indica que teve um conflito de horário na solicitação*/
        if(verificarPorPartes(h1.getHorario().getDias(), h2.getHorario().getDias()) &&
                verificarPorPartes(h1.getHorario().getTurnos(), h2.getHorario().getTurnos())
                && verificarPorPartes(h1.getHorario().getHoras(), h2.getHorario().getHoras()) &&
                h1.getEspaco().getName().equalsIgnoreCase(h2.getEspaco().getName())){
            throw new ProjectExceptions("ERRO! Ocorreu algum conflito de horário");
        }
    }

    public void compararConflitos(Request r1) throws ProjectExceptions {
        Hashtable<String, Request> hash = readRequestsGeneral();
        if(hash != null){
            for(String key: hash.keySet()){
                Request r2 = hash.get(key);
                conflitoHorario(r1, r2);
                /*compara a solicitação passada como parâmetro com todas as outras do arquivo pra
                saber se tem conflito de horário em alguma*/
            }
        }
    }

    public void alocarEspacoFisico(Espaco ef){
        if(buscarEspaco(ef.getName()) == null){ //ou seja, se não achou nem um local com esse nome cria um novo.
            try(BufferedWriter alocar = new BufferedWriter(new FileWriter("espacos.txt", true))){
                alocar.write(ef.getType() + "," + ef.getCapacity() + "," + ef.getName()
                        + "," + ef.getLocation());
                alocar.newLine();
            }catch (IOException e){
                JOptionPane.showMessageDialog(null, "Erro ao tentar escrever no arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Espaco buscarEspaco(String nome) {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("espacos.txt"));

            // Verifica se o arquivo não está vazio
            if (leitor.readLine() == null) { //já comeu uma linha e tem que reiniciar
                leitor.close();
                return null;
            }
            leitor.close();
            leitor = new BufferedReader(new FileReader("espacos.txt"));  //abri o arquivo novamente

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] separator = linha.split(",");
                if(separator[2].equalsIgnoreCase(nome)) {
                    Espaco ef = new Espaco(separator[0], Integer.parseInt(separator[1]),
                            separator[2], separator[3]);
                    leitor.close();
                    return ef;
                }
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ARQUIVO INEXISTENTE!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LER ARQUIVO", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public ArrayList<Request> pesquisaPorCurso(String nomeCurso) {
        Hashtable<String, Request> conteudo = readRequestsGeneral();
        ArrayList<Request> relatorioCurso = new ArrayList<>();

        if(conteudo == null){
            return null;
        }
        for (String key : conteudo.keySet()) {
            Request e = conteudo.get(key);
            if (e.getCurso().equalsIgnoreCase(nomeCurso)) {
                if (e.getEspaco().getType().equalsIgnoreCase("sala de aula")) {
                    relatorioCurso.add(e);
                }
            }
        }
        if (!relatorioCurso.isEmpty()) {
            return relatorioCurso;
        } else {
            return null;
        }
    }

    public ArrayList<Request> pesquisaPorEspaco(String espaco) {
        Hashtable<String, Request> conteudo = readRequestsGeneral();
        ArrayList<Request> relatorioEspaco = new ArrayList<>();

        if(conteudo==null){
            return null;
        }
        for (String key : conteudo.keySet()) {
            Request e = conteudo.get(key);
            if (e.getEspaco().getType().equalsIgnoreCase(espaco)) {
                int dias = calcularDiferencaDias(e.getDataCadastro());
                if(dias <= 7){ //apenas solicitações alocadas ao longo da semana
                    relatorioEspaco.add(e);
                }
            }
        }
        if (!relatorioEspaco.isEmpty()) {
            return relatorioEspaco;
        } else {
            return null;
        }
    }

    public int calcularDiferencaDias(LocalDate outraData) {
        LocalDate dataAtual = LocalDate.now();
        long diferenca = ChronoUnit.DAYS.between(outraData, dataAtual);
        return (int) diferenca;
    }
}
