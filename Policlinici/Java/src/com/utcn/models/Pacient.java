package com.utcn.models;

public class Pacient {
    String cnp, nume, prenume;

    public Pacient(String cnp, String nume, String prenume) {
        this.cnp = cnp;
        this.nume = nume;
        this.prenume = prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
}
