package com.utcn.models;

public class Lista_Servicii_Client {
    int id_serviciu, id_programare;

    public Lista_Servicii_Client(int id_serviciu, int id_programare) {
        this.id_serviciu = id_serviciu;
        this.id_programare = id_programare;
    }

    public int getId_serviciu() {
        return id_serviciu;
    }

    public void setId_serviciu(int id_serviciu) {
        this.id_serviciu = id_serviciu;
    }

    public int getId_programare() {
        return id_programare;
    }

    public void setId_programare(int id_programare) {
        this.id_programare = id_programare;
    }

}
