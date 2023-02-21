package com.utcn.models;

public class Competenta {
    String cnp;
    Competente competenta;

    public Competenta(String cnp, Competente competenta) {
        this.cnp = cnp;
        this.competenta = competenta;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Competente getCompetenta() {
        return competenta;
    }

    public void setCompetenta(Competente competenta) {
        this.competenta = competenta;
    }

}
