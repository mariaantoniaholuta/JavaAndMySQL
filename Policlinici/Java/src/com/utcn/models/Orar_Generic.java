package com.utcn.models;

public class Orar_Generic{
    Ora oraInceput, oraSfarsit;
    String zi, cnp;
    int id_policlinica;

    public Orar_Generic(Ora oraInceput, Ora oraSfarsit, String zi, String cnp, int id_policlinica) {
        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
        this.zi = zi;
        this.cnp = cnp;
        this.id_policlinica = id_policlinica;
    }

    public Ora getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(Ora oraInceput) {
        this.oraInceput = oraInceput;
    }

    public Ora getOraSfarsit() {
        return oraSfarsit;
    }

    public void setOraSfarsit(Ora oraSfarsit) {
        this.oraSfarsit = oraSfarsit;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public int getId_policlinica() {
        return id_policlinica;
    }

    public void setId_policlinica(int id_policlinica) {
        this.id_policlinica = id_policlinica;
    }
}
