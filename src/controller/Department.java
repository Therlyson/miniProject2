package controller;

import model.Espaco;
import model.Horario;
import model.exceptions.ProjectExceptions;
import model.solicitações.EventualRequest;
import model.solicitações.FixedRequest;
import model.solicitações.Request;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Hashtable;

public class Department {

    public Hashtable<String, Request> readRequestsCurrent(){
        String arquivo = "SolicitaçõesAtuais.txt";
        Hashtable<String, Request> hash = new Hashtable<>();

        try(BufferedReader bf = new BufferedReader(new FileReader(arquivo))){
            String line;
            while((line = bf.readLine()) != null){
                String[] separator = line.split(",");
                if(separator[0].equalsIgnoreCase("FIXA")){
                    Horario hr = new Horario(separator[5]);
                    Request rq = new FixedRequest(Integer.parseInt(separator[1]), separator[2], separator[3],
                            Integer.parseInt(separator[4]), hr, separator[6]);
                    hash.put(separator[5], rq);
                }
                else if(separator[0].equalsIgnoreCase("EVENTUAL")){
                    Horario hr = new Horario(separator[5]);
                    Request rq = new EventualRequest(Integer.parseInt(separator[1]), separator[2], separator[3],
                            Integer.parseInt(separator[4]), hr, separator[6]);
                    hash.put(separator[5], rq);
                }
            }
            if(!hash.isEmpty()){
                return hash;
            }
            else{
                return null;
            }
        }catch (IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null;
    }

    public Hashtable<String, Request> readRequestsGeneral(){
        String arquivo = "TodasSolicitações.txt";
        Hashtable<String, Request> hash = new Hashtable<>();

        try(BufferedReader bf = new BufferedReader(new FileReader(arquivo))){
            bf.readLine();
            String line;
            while((line = bf.readLine()) != null){
                String[] separator = line.split(",");
                if(separator[0].equalsIgnoreCase("FIXA")){
                    Horario hr = new Horario(separator[5]);
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
                    Request rq = new EventualRequest(Integer.parseInt(separator[1]), separator[2], separator[3],
                            Integer.parseInt(separator[4]), hr, espaco,data, separator[8]);
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
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null;
    }

    public boolean allocateRequest(Request request){
        String arquivo = "TodasSolicitações.txt";
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(arquivo, true))){
            if(request instanceof FixedRequest){
                FixedRequest fixed = (FixedRequest) request;
                fixed.testEspace();
                alocarEspacoFisico(fixed.getEspaco());
                compararConflitos(fixed);
                bf.write("FIXA" + "," + fixed.getAno() + "," + fixed.getSemestre() + "," + fixed.getCurso() + "," +
                        fixed.getVagas() + "," + fixed.getHorario().getHorarioSigaa() + "," + fixed.getEspaco().getName() +
                        "," + fixed.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "," + fixed.getDisciplina());
                return true;
            }
            else if(request instanceof EventualRequest){
                EventualRequest eventual = (EventualRequest) request;
                eventual.testEspace();
                alocarEspacoFisico(eventual.getEspaco());
                compararConflitos(eventual);
                bf.write("EVENTUAL" + "," + eventual.getAno() + "," + eventual.getSemestre() + "," + eventual.getCurso() + "," +
                        eventual.getVagas() + "," + eventual.getHorario().getHorarioSigaa() + "," +
                        eventual.getEspaco().getName() + "," + eventual.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + "," + eventual.getFinalidade());
                return true;
            }
            bf.newLine();

            try(BufferedWriter bw = new BufferedWriter(new FileWriter("SolicitaçõesAtuais.txt", true))){
                if(request instanceof FixedRequest) {
                    FixedRequest fixed = (FixedRequest) request;
                    bw.write("FIXA" + "," + fixed.getAno() + "," + fixed.getSemestre() + "," + fixed.getCurso() + "," +
                            fixed.getVagas() + "," + fixed.getHorario().getHorarioSigaa() +
                            "," + fixed.getDisciplina());
                }
                else if(request instanceof EventualRequest) {
                    EventualRequest eventual = (EventualRequest) request;
                    bw.write("EVENTUAL" + "," + eventual.getAno() + "," + eventual.getSemestre() + "," + eventual.getCurso() + "," +
                            eventual.getVagas() + "," + eventual.getHorario().getHorarioSigaa()
                            + "," + eventual.getFinalidade());
                }
                bw.newLine();
            }catch (IOException e){
                System.out.println("Error writeing file: " + e.getMessage());
            }
        }catch (IOException e){
            System.out.println("Error writeing file: " + e.getMessage());
        }catch (ProjectExceptions e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void clearFile(){
        try {
            FileWriter fileWriter = new FileWriter("SolicitaçõesAtuais.txt", false);
            fileWriter.close();  // Isso limpa o conteúdo do arquivo e o fecha
        } catch (IOException e) {
            System.out.println("Erro ao limpar o arquivo: " + e.getMessage());
        }
    }

    public boolean verificarLetrasEmComum(String one, String two){
        String[] separator = one.split("");
        for(String str: separator){
            if(two.contains(str)){
                return true;
            }
        }
        return false;
    }

    public void conflitoHorario(Request h1, Request h2) throws ProjectExceptions {
        if(verificarLetrasEmComum(h1.getHorario().getDias(), h2.getHorario().getDias()) &&
                verificarLetrasEmComum(h1.getHorario().getTurnos(), h2.getHorario().getTurnos())
                && verificarLetrasEmComum(h1.getHorario().getHoras(), h2.getHorario().getHoras()) &&
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
            }
        }
    }

    public void alocarEspacoFisico(Espaco ef){
        if(buscarEspaco(ef.getName()) == null){
            try(BufferedWriter alocar = new BufferedWriter(new FileWriter("espacos.txt", true))){
                alocar.write(ef.getType() + "," + ef.getCapacity() + "," + ef.getName()
                        + "," + ef.getLocation());
                alocar.newLine();
            }catch (IOException e){
                System.out.println("ERRO AO TENTAR ESCREVER NO ARQUIVO.");
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
            leitor = new BufferedReader(new FileReader("espacos.txt"));

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
            System.out.println("ARQUIVO INEXISTENTE!");
        } catch (IOException e) {
            System.out.println("ERRO AO LER NO ARQUIVO");
        }
        return null;
    }

    public ArrayList<Request> pesquisaPorCurso(String nomeCurso) {
        Hashtable<String, Request> conteudo = readRequestsGeneral();
        ArrayList<Request> eventosCurso = new ArrayList<>();

        if(conteudo == null){
            return null;
        }
        for (String key : conteudo.keySet()) {
            Request e = conteudo.get(key);
            if (e.getCurso().equalsIgnoreCase(nomeCurso)) {
                if (e.getEspaco().getType().equalsIgnoreCase("sala de aula")) {
                    eventosCurso.add(e);
                }
            }
        }
        if (!eventosCurso.isEmpty()) {
            return eventosCurso;
        } else {
            return null;
        }
    }

    public ArrayList<Request> pesquisaPorEspaco(String espaco) {
        Hashtable<String, Request> conteudo = readRequestsGeneral();
        ArrayList<Request> eventos = new ArrayList<>();

        if(conteudo==null){
            return null;
        }
        for (String key : conteudo.keySet()) {
            Request e = conteudo.get(key);
            if (e.getEspaco().getType().equalsIgnoreCase(espaco)) {
                int semana = calcularDiferencaDias(e.getDataCadastro());
                if(semana <= 7){
                    eventos.add(e);
                }
            }
        }
        if (!eventos.isEmpty()) {
            return eventos;
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
