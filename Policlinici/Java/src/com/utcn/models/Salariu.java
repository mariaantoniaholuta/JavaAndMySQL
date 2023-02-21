package com.utcn.models;

public class Salariu {

    public String cnp;
    public String data_incasare;
    public float salariu;

    public Salariu(String cnp, String data_incasare, float salariu) {
        this.cnp = cnp;
        this.data_incasare = data_incasare;
        this.salariu = salariu;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getData_incasare() {
        return data_incasare;
    }

    public void setData_incasare(String data_incasare) {
        this.data_incasare = data_incasare;
    }

    public float getSalariu() {
        return salariu;
    }

    public void setSalariu(float salariu) {
        this.salariu = salariu;
    }

}
