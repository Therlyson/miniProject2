package model.solicitações;

import model.Espaco;
import model.Horario;
import model.exceptions.ProjectExceptions;

import java.time.LocalDate;

public abstract class Request {
    protected Integer ano;
    protected String semestre;
    protected String curso;
    protected Integer vagas;
    protected Horario horario;
    protected Espaco espaco;
    protected LocalDate dataCadastro;

    public Request(Integer ano, String semestre, String curso, Integer vagas, Horario horario) {
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
        this.vagas = vagas;
        this.horario = horario;
    }

    public Request(Integer ano, String semestre, String curso, Integer vagas, Horario horario, Espaco espaco, LocalDate dataCadastro) {
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
        this.vagas = vagas;
        this.horario = horario;
        this.espaco = espaco;
        this.dataCadastro = dataCadastro;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Espaco getEspaco() {
        return espaco;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    //Método que testa se determinado espaço tem capacidade suficiente para uma determinada solicitação
    public void testEspace() throws ProjectExceptions {
        if(vagas > espaco.getCapacity()){
            throw new ProjectExceptions("ERRO! Quantidade de vagas maior que a capacidade do espaço!");
        }
    }


    @Override
    public String toString() {
        return "Ano: " + ano +
                ", Semestre: " + semestre +
                ", Curso: " + curso +
                ", Vagas: " + vagas +
                ", Horario: " + horario.getHorarioSigaa() +
                (espaco != null ? ", Espaço Fisico: " + espaco.getName() : "") +
                (dataCadastro != null ? ", Data de cadastro: " + dataCadastro : "");
    }
}
