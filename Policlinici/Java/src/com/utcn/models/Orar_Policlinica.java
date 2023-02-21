package com.utcn.models;

public class Orar_Policlinica {
    int id_policlinica, id_orar;
    Ora oraSfarsit, oraInceput;
    String zi;

    public Orar_Policlinica(int id_policlinica, int id_orar, Ora oraSfarsit, Ora oraInceput, String zi) {
        this.id_policlinica = id_policlinica;
        this.id_orar = id_orar;
        this.oraSfarsit = oraSfarsit;
        this.oraInceput = oraInceput;
        this.zi = zi;
    }

    public int getId_policlinica() {
        return id_policlinica;
    }

    public void setId_policlinica(int id_policlinica) {
        this.id_policlinica = id_policlinica;
    }

    public int getId_orar() {
        return id_orar;
    }

    public void setId_orar(int id_orar) {
        this.id_orar = id_orar;
    }

    public Ora getOraSfarsit() {
        return oraSfarsit;
    }

    public void setOraSfarsit(Ora oraSfarsit) {
        this.oraSfarsit = oraSfarsit;
    }

    public Ora getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(Ora oraInceput) {
        this.oraInceput = oraInceput;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }
}
