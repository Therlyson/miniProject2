package model.solicitações;

import model.Espaco;
import model.Horario;
import model.exceptions.ProjectExceptions;

import java.time.LocalDate;

public class EventualRequest extends Request{
    private String finalidade;

    public EventualRequest(Integer ano, String semestre, String curso, Integer vagas, Horario horario, String finalidade) {
        super(ano, semestre, curso, vagas, horario);
        this.finalidade = finalidade;
    }

    public EventualRequest(Integer ano, String semestre, String curso, Integer vagas, Horario horario, Espaco espaco, LocalDate dataCadastro, String finalidade) {
        super(ano, semestre, curso, vagas, horario, espaco, dataCadastro);
        this.finalidade = finalidade;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    @Override
    public void testEspace() throws ProjectExceptions {
        super.testEspace();
        if(!(espaco.getType().equalsIgnoreCase("Auditório"))){
            throw new ProjectExceptions("ERRO!! Solicitação eventual só pode ser alocada em auditórios.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Finalidade: " + finalidade;
    }
}
