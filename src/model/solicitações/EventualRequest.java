package model.solicitações;
import model.Espaco;
import model.Horario;
import model.exceptions.ProjectExceptions;
import java.time.LocalDate;

public class EventualRequest extends Request{
    private String purpose;
    private LocalDate initial_date;
    private LocalDate final_date;

    public EventualRequest(Integer ano, String semestre, String curso, Integer vagas,
                           Horario horario, String purpose, LocalDate initial_date, LocalDate final_date) {
        super(ano, semestre, curso, vagas, horario);
        this.purpose = purpose;
        this.initial_date = initial_date;
        this.final_date = final_date;
    }

    public EventualRequest(Integer ano, String semestre, String curso, Integer vagas,
                           Horario horario, Espaco espaco, LocalDate dataCadastro, String purpose, LocalDate initial_date, LocalDate final_date) {
        super(ano, semestre, curso, vagas, horario, espaco, dataCadastro);
        this.purpose = purpose;
        this.initial_date = initial_date;
        this.final_date = final_date;
    }

    public String getPurpose() {
        return purpose;
    }

    public LocalDate getInitial_date() {
        return initial_date;
    }

    public LocalDate getFinal_date() {
        return final_date;
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
        return super.toString() + ", Finalidade: " + purpose +
                ", Data inicial: " + initial_date +
                ", Data final: " + final_date;
    }
}
