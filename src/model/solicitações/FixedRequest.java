package model.solicitações;

import model.Espaco;
import model.Horario;
import model.exceptions.ProjectExceptions;

import java.time.LocalDate;

public class FixedRequest extends Request{
    private String disciplina;

    public FixedRequest(Integer ano, String semestre, String curso, Integer vagas, Horario horario, String disciplina) {
        super(ano, semestre, curso, vagas, horario);
        this.disciplina = disciplina;
    }

    public FixedRequest(Integer ano, String semestre, String curso, Integer vagas,
                        Horario horario, Espaco espaco, LocalDate dataCadastro, String disciplina) {
        super(ano, semestre, curso, vagas, horario, espaco, dataCadastro);
        this.disciplina = disciplina;
    }


    public String getDisciplina() {
        return disciplina;
    }

    @Override
    public String toString() {
        return super.toString() + ", Disciplina: " + disciplina;
    }
}
