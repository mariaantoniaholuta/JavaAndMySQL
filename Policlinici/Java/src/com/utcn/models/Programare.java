package com.utcn.models;

public class Programare {
    int id_programare, id_policlinica;
    String cnp_pacient, cnp_medic, data_consultatie;
    Ora ora;

    public Programare(int id_programare, int id_policlinica, String cnp_pacient, String cnp_medic, String data_consultatie, Ora ora) {
        this.id_programare = id_programare;
        this.id_policlinica = id_policlinica;
        this.cnp_pacient = cnp_pacient;
        this.cnp_medic = cnp_medic;
        this.data_consultatie = data_consultatie;
        this.ora = ora;
    }

    public int getId_programare() {
        return id_programare;
    }

    public void setId_programare(int id_programare) {
        this.id_programare = id_programare;
    }

    public int getId_policlinica() {
        return id_policlinica;
    }

    public void setId_policlinica(int id_policlinica) {
        this.id_policlinica = id_policlinica;
    }

    public String getCnp_pacient() {
        return cnp_pacient;
    }

    public void setCnp_pacient(String cnp_pacient) {
        this.cnp_pacient = cnp_pacient;
    }

    public String getCnp_medic() {
        return cnp_medic;
    }

    public void setCnp_medic(String cnp_medic) {
        this.cnp_medic = cnp_medic;
    }

    public String getData_consultatie() {
        return data_consultatie;
    }

    public void setData_consultatie(String data_consultatie) {
        this.data_consultatie = data_consultatie;
    }

    public Ora getOra() {
        return ora;
    }

    public void setOra(Ora ora) {
        this.ora = ora;
    }

}
