package com.utcn.models;

public class Lista_Servicii_Cabinet {
    int id_cabinet, id_serviciu;

    public Lista_Servicii_Cabinet(int id_cabinet, int id_serviciu) {
        this.id_cabinet = id_cabinet;
        this.id_serviciu = id_serviciu;
    }

    public int getId_cabinet() {
        return id_cabinet;
    }

    public void setId_cabinet(int id_cabinet) {
        this.id_cabinet = id_cabinet;
    }

    public int getId_serviciu() {
        return id_serviciu;
    }

    public void setId_serviciu(int id_serviciu) {
        this.id_serviciu = id_serviciu;
    }

}
