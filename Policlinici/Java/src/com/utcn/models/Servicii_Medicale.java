package com.utcn.models;

public class Servicii_Medicale {
    int id_serviciu, durata;
    String denumire_serviciu;
    float pret;

    public Servicii_Medicale(int id_serviciu, int durata, String denumire_serviciu, float pret) {
        this.id_serviciu = id_serviciu;
        this.durata = durata;
        this.denumire_serviciu = denumire_serviciu;
        this.pret = pret;
    }

    public int getId_serviciu() {
        return id_serviciu;
    }

    public void setId_serviciu(int id_serviciu) {
        this.id_serviciu = id_serviciu;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getDenumire_serviciu() {
        return denumire_serviciu;
    }

    public void setDenumire_serviciu(String denumire_serviciu) {
        this.denumire_serviciu = denumire_serviciu;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }


}
