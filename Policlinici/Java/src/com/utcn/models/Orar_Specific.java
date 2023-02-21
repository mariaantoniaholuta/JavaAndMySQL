package com.utcn.models;

public class Orar_Specific {
    int id_policlinica;
    Ora oraInceput, OraSfarsit;
    String data, cnp;

    public Orar_Specific(int id_policlinica, String cnp, Ora oraInceput, Ora oraSfarsit, String data) {
        this.id_policlinica = id_policlinica;
        this.cnp = cnp;
        this.oraInceput = oraInceput;
        OraSfarsit = oraSfarsit;
        this.data = data;
    }

    public int getId_policlinica() {
        return id_policlinica;
    }

    public void setId_policlinica(int id_policlinica) {
        this.id_policlinica = id_policlinica;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Ora getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(Ora oraInceput) {
        this.oraInceput = oraInceput;
    }

    public Ora getOraSfarsit() {
        return OraSfarsit;
    }

    public void setOraSfarsit(Ora oraSfarsit) {
        OraSfarsit = oraSfarsit;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
