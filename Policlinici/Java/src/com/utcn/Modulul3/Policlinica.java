package com.utcn.Modulul3;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Policlinica {
    int id_policlinica, numar_cabinete;
    String nume, descriere, adresa;

    public Policlinica(int id_policlinica, int numar_cabinete, String nume, String descriere, String adresa) {
        this.id_policlinica = id_policlinica;
        this.numar_cabinete = numar_cabinete;
        this.nume = nume;
        this.descriere = descriere;
        this.adresa = adresa;
    }
    public Policlinica(ResultSet resultSet){
        try {
            this.id_policlinica= resultSet.getInt("id_policlinica");
            this.numar_cabinete= resultSet.getInt("numar_cabinete");
            this.nume= resultSet.getString("nume");
            this.descriere= resultSet.getString("descriere");
            this.adresa= resultSet.getString("adresa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId_policlinica() {
        return id_policlinica;
    }

    public void setId_policlinica(int id_policlinica) {
        this.id_policlinica = id_policlinica;
    }

    public int getNumar_cabinete() {
        return numar_cabinete;
    }

    public void setNumar_cabinete(int numar_cabinete) {
        this.numar_cabinete = numar_cabinete;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
