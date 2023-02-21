package com.utcn.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Angajat {

    public String cnp, utilizator, parola,  adresa, email, nume, prenume, telefon, iban, data_angajare, functie, salariu;
    public int numar_ore;

    public Angajat() {

    }

    public Angajat(ResultSet resultSet) throws SQLException {
        this.cnp = resultSet.getString("cnp");
        this.utilizator = resultSet.getString("utilizator");
        this.parola = resultSet.getString("parola");
        this.adresa = resultSet.getString("adresa");
        this.email = resultSet.getString("email");
        this.nume = resultSet.getString("nume");
        this.prenume = resultSet.getString("prenume");;
        this.telefon = resultSet.getString("telefon");
        this.iban = resultSet.getString("iban");
        this.data_angajare = resultSet.getString("data_angajare");
        this.functie = resultSet.getString("functie");
        this.numar_ore = resultSet.getInt("numar_ore");
        this.salariu = resultSet.getString("salariu_negociat");
    }

    public String getUtilizator() {
        return utilizator;
    }

    public String getParola() {
        return parola;
    }

    public String getCnp() {
        return cnp;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getEmail() {
        return email;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getIban() {
        return iban;
    }

    public String getData_angajare() {
        return data_angajare;
    }

    public String getFunctie() {
        return functie;
    }

    public int getNumar_ore() {
        return numar_ore;
    }

    public String getSalariu() {
        return salariu;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "cnp='" + cnp + '\'' +
                ", utilizator='" + utilizator + '\'' +
                ", parola='" + parola + '\'' +
                ", adresa='" + adresa + '\'' +
                ", email='" + email + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", telefon='" + telefon + '\'' +
                ", iban='" + iban + '\'' +
                ", data_angajare='" + data_angajare + '\'' +
                ", functie='" + functie + '\'' +
                ", numar_ore=" + numar_ore +
                '}';
    }
}
