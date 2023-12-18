package model;

public class Horario {
    private String horarioSigaa;
    private String dias;
    private String turnos;
    private String horas;

    public Horario(String horarioSigaa) {
        this.horarioSigaa = horarioSigaa;
        formatarHorario();
    }

    public String getHorarioSigaa() {
        return horarioSigaa;
    }

    public void setHorarioSigaa(String horarioSigaa) {
        this.horarioSigaa = horarioSigaa;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }


    public String getTurnos() {
        return turnos;
    }

    public void setTurnos(String turnos) {
        this.turnos = turnos;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public void formatarHorario(){
        String[] hr = horarioSigaa.split(" ");
        String day, turn = "", hours;
        String conc1 = "", conc2 = "", conc3 = "";
        for(int i =0; i<hr.length; i++){
            String[] separar = null;
            if(hr[i].contains("M")){
                separar = hr[i].split("M");
                turn = "M";
            }else if(hr[i].contains("T")){
                separar = hr[i].split("T");
                turn = "T";
            }else if(hr[i].contains("N")){
                separar = hr[i].split("N");
                turn = "N";
            }
            if(separar != null){
                day = separar[0];
                conc1 = conc1.concat(day);
                conc2 = conc2.concat(turn);
                hours = separar[1];
                conc3 = conc3.concat(hours);
            }
        }
        setDias(conc1);
        setTurnos(conc2);
        setHoras(conc3);
    }
}
