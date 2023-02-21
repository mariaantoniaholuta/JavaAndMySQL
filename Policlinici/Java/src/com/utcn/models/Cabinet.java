package com.utcn.models;

public class Cabinet{
    int id;
    int numar_cabinet;
    int id_policlinica;
    String cnp;

    public Cabinet(int id_policlinica, int numar_cabinete, String nume, String descriere, String adresa, int id, int numar_cabinet, int id_policlinica1, String cnp) {
        this.id = id;
        this.numar_cabinet = numar_cabinet;
        this.id_policlinica = id_policlinica1;
        this.cnp = cnp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumar_cabinet() {
        return numar_cabinet;
    }

    public void setNumar_cabinet(int numar_cabinet) {
        this.numar_cabinet = numar_cabinet;
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

}
