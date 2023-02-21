package com.utcn.models;

public class Echipament_Medical {
    int id_cabinet, id_echipament;
    String echipament;

    public Echipament_Medical(int id_cabinet, int id_echipament, String echipament) {
        this.id_cabinet = id_cabinet;
        this.id_echipament = id_echipament;
        this.echipament = echipament;
    }

    public int getId_cabinet() {
        return id_cabinet;
    }

    public void setId_cabinet(int id_cabinet) {
        this.id_cabinet = id_cabinet;
    }

    public int getId_echipament() {
        return id_echipament;
    }

    public void setId_echipament(int id_echipament) {
        this.id_echipament = id_echipament;
    }

    public String getEchipament() {
        return echipament;
    }

    public void setEchipament(String echipament) {
        this.echipament = echipament;
    }

}
