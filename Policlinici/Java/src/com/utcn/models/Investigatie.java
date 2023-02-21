package com.utcn.models;

public class Investigatie {
    int id_raport, id_serviciu;
    String detalii_investigatie;

    public Investigatie(int id_raport, int id_serviciu, String detalii_investigatie) {
        this.id_raport = id_raport;
        this.id_serviciu = id_serviciu;
        this.detalii_investigatie = detalii_investigatie;
    }

    public int getId_raport() {
        return id_raport;
    }

    public void setId_raport(int id_raport) {
        this.id_raport = id_raport;
    }

    public int getId_serviciu() {
        return id_serviciu;
    }

    public void setId_serviciu(int id_serviciu) {
        this.id_serviciu = id_serviciu;
    }

    public String getDetalii_investigatie() {
        return detalii_investigatie;
    }

    public void setDetalii_investigatie(String detalii_investigatie) {
        this.detalii_investigatie = detalii_investigatie;
    }
    
}
