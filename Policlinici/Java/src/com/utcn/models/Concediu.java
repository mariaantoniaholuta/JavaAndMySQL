package com.utcn.models;

public class Concediu {
    String cnp, data_plecare, data_revenire;

    public Concediu(String cnp, String data_plecare, String data_revenire) {
        this.cnp = cnp;
        this.data_plecare = data_plecare;
        this.data_revenire = data_revenire;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getData_plecare() {
        return data_plecare;
    }

    public void setData_plecare(String data_plecare) {
        this.data_plecare = data_plecare;
    }

    public String getData_revenire() {
        return data_revenire;
    }

    public void setData_revenire(String data_revenire) {
        this.data_revenire = data_revenire;
    }


}
