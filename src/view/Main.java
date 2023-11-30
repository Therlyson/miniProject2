package view;

import controller.Department;
import model.Espaco;
import model.Horario;
import model.solicitações.EventualRequest;
import model.solicitações.FixedRequest;
import model.solicitações.Request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Department department = new Department();
        department.clearFile();

        int opcao;
        while(true){
            System.out.println();
            System.out.print("\t\t-=-=-MENU-=-=-\n1 - LER SOLICITAÇÕES ATUAL\n2 - LER TODAS AS SOLICITAÇÕES\n3 - ALOCAR NOVA SOLICITAÇÃO\n" +
                    "4 - RELATÓRIO DE ALOCAÇÕES POR CURSO\n" + "5 - RELATÓRIO DE ALOCAÇÕES POR ESPAÇO FISICO\n0 - SAIR\nDigite a sua opção: ");
            opcao = teclado.nextInt();
            teclado.nextLine();

            if(opcao == 1){
                System.out.println();
                Hashtable<String, Request> solicitacoes = department.readRequestsCurrent();
                if(solicitacoes==null){
                    System.out.println("Sem solicitações atuais no momento!");
                    continue;
                }
                System.out.println("SOLICITAÇÕES ATUAIS: ");
                for(String key: solicitacoes.keySet()){
                    Request evento = solicitacoes.get(key);
                    System.out.println(evento);
                }
            }

            else if(opcao==2){
                System.out.println();
                Hashtable<String, Request> solicitacoes = department.readRequestsGeneral();
                if(solicitacoes==null){
                    System.out.println("Sem solicitações no momento!");
                    continue;
                }
                System.out.println("TODAS AS SOLICITAÇÕES: ");
                for(String key: solicitacoes.keySet()){
                    Request evento = solicitacoes.get(key);
                    System.out.println(evento);
                }
            }

            else if (opcao==3) {
                System.out.print("\nDeseja alocar no auditório ou sala de aula: ");
                String espaco = teclado.nextLine();
                System.out.printf("Qual a capacidade do/a %s? ", espaco);
                int capacidade = teclado.nextInt();
                teclado.nextLine();
                System.out.printf("Digite o nome do/a %s: ", espaco);
                String nome = teclado.nextLine();
                System.out.print("Digite a localização: ");
                String localization = teclado.nextLine();
                Espaco espacoFisico = new Espaco(espaco, capacidade, nome, localization);

                System.out.print("Digite o horário do evento(formato: 35T12): ");
                String h = teclado.nextLine();
                Horario horario = new Horario(h);

                System.out.print("Digite o ano do evento: ");
                Integer ano = teclado.nextInt();
                teclado.nextLine();
                System.out.print("Digite o semestre: ");
                String semestre = teclado.nextLine();
                System.out.print("Digite o curso: ");
                String curso = teclado.nextLine();
                System.out.print("Digite a quantidade de vagas: ");
                Integer vagas = teclado.nextInt();
                teclado.nextLine();
                System.out.print("Digite a data que você está alocando: ");
                String d = teclado.nextLine();
                LocalDate data = LocalDate.parse(d, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                System.out.print("\n1 - ALOCAR SOLICITAÇÃO FIXA\n2 - ALOCAR SOLICITAÇÃO EVENTUAL\nDigite sua opção: ");
                int op = teclado.nextInt();
                teclado.nextLine();
                if(op==1){
                    System.out.print("Digite a disciplina: ");
                    String disciplina = teclado.nextLine();
                    Request fixedRequest = new FixedRequest(ano, semestre, curso, vagas, horario, espacoFisico, data, disciplina);
                    boolean alocar = department.allocateRequest(fixedRequest);
                    if(alocar){
                        System.out.println("Solicitação cadastrada com sucesso!");
                    }
                }
                else if(op==2){
                    System.out.print("Digite a finalidade(seminário,tcc,palestra,prova): ");
                    String finalidade = teclado.nextLine();
                    Request eventoEsp = new EventualRequest(ano, semestre, curso, vagas, horario, espacoFisico, data, finalidade);
                    boolean alocar = department.allocateRequest(eventoEsp);
                    if(alocar){
                        System.out.println("Solicitação cadastrada com sucesso!");
                    }
                }
            }

            else if(opcao==4){
                System.out.print("Digite o nome do curso: ");
                String nome = teclado.nextLine();
                ArrayList<Request> cursos = department.pesquisaPorCurso(nome);
                if(cursos==null){
                    System.out.println("NOME DO CURSO NÃO ENCONTRADO OU ESTÁ ALOCADO EM UM AUDITÓRIO!");
                }
                else{
                    System.out.println("\nSOLICITAÇÕES DO CURSO DE " + nome + ": ");
                    for(Request e: cursos){
                        System.out.println(e);
                    }
                }
            }

            else if(opcao==5){
                System.out.print("Digite o nome do espaço fisico: ");
                String nome = teclado.nextLine();
                ArrayList<Request> espacos = department.pesquisaPorEspaco(nome);
                if(espacos==null){
                    System.out.println("\nNÃO EXISTEM ALOCAÇÕES NESSE TIPO DE ESPAÇO HÁ UMA SEMANA ATRÁS");
                }
                else{
                    System.out.println("\nTodas as alocações em " + nome + " de até 1 semana atrás: ");
                    for(Request e: espacos){
                        System.out.println(e);
                    }
                }
            }

            else if(opcao == 0){
                System.out.println("PROGRAMA ENCERRADO!");
                break;
            }
            else{
                System.out.println("OPÇÃO INVÁLIDA");
            }
        }

    }
}