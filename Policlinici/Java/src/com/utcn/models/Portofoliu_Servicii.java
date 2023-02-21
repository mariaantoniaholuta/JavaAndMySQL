package com.utcn.models;

public class Portofoliu_Servicii {
    String denumire_specialitate;
    int id_serviciu;

    public Portofoliu_Servicii(String denumire_specialitate, int id_serviciu) {
        this.denumire_specialitate = denumire_specialitate;
        this.id_serviciu = id_serviciu;
    }

    public String getDenumire_specialitate() {
        return denumire_specialitate;
    }

    public void setDenumire_specialitate(String denumire_specialitate) {
        this.denumire_specialitate = denumire_specialitate;
    }

    public int getId_serviciu() {
        return id_serviciu;
    }

    public void setId_serviciu(int id_serviciu) {
        this.id_serviciu = id_serviciu;
    }

}
