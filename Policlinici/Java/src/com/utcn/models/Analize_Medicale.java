package com.utcn.models;

public class Analize_Medicale {
    int id_raport;
    String detalii_analize, analiza_efectuata;
    Boolean validare;

    public Analize_Medicale(int id_raport, String detalii_analize, String analiza_efectuata, Boolean validare) {
        this.id_raport = id_raport;
        this.detalii_analize = detalii_analize;
        this.analiza_efectuata = analiza_efectuata;
        this.validare = validare;
    }

    public int getId_raport() {
        return id_raport;
    }

    public void setId_raport(int id_raport) {
        this.id_raport = id_raport;
    }

    public String getDetalii_analize() {
        return detalii_analize;
    }

    public void setDetalii_analize(String detalii_analize) {
        this.detalii_analize = detalii_analize;
    }

    public String getAnaliza_efectuata() {
        return analiza_efectuata;
    }

    public void setAnaliza_efectuata(String analiza_efectuata) {
        this.analiza_efectuata = analiza_efectuata;
    }

    public Boolean getValidare() {
        return validare;
    }

    public void setValidare(Boolean validare) {
        this.validare = validare;
    }

}
