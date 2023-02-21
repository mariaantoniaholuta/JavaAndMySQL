package com.utcn.Modulul3;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class Programare {
    int id_programare, id_policlinica;
    String cnp_pacient, cnp_medic;
    Date data_consultatie;
    Time ora;
    Time ora_sfarsit;

    public Programare() {
    }

    public Programare(int id_programare, int id_policlinica, String cnp_pacient, String cnp_medic, Date data_consultatie, Time ora) {
        this.id_programare = id_programare;
        this.id_policlinica = id_policlinica;
        this.cnp_pacient = cnp_pacient;
        this.cnp_medic = cnp_medic;
        this.data_consultatie = data_consultatie;
        this.ora = ora;
    }

    @Override
    public String toString() {
        String s="";
        s+=String.valueOf(this.id_programare);
        s+=": ";
        s+=this.data_consultatie.toString();
        s+="  ";
        s+=this.ora.toString().substring(0,5);
        return s;
    }

    public Programare(ResultSet resultSet) {
        try {
            this.id_programare= resultSet.getInt("id_programare");
            this.id_policlinica= resultSet.getInt("id_policlinica");
            this.cnp_medic= resultSet.getString("cnp_medic");
            this.cnp_pacient= resultSet.getString("cnp_pacient");
            this.data_consultatie=resultSet.getDate("data_consultatie");
            this.ora=resultSet.getTime("ora");
            this.ora_sfarsit=resultSet.getTime("ora_sfarsit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId_programare() {
        return id_programare;
    }

    public void setId_programare(int id_programare) {
        this.id_programare = id_programare;
    }

    public int getId_policlinica() {
        return id_policlinica;
    }

    public void setId_policlinica(int id_policlinica) {
        this.id_policlinica = id_policlinica;
    }

    public String getCnp_pacient() {
        return cnp_pacient;
    }

    public void setCnp_pacient(String cnp_pacient) {
        this.cnp_pacient = cnp_pacient;
    }

    public String getCnp_medic() {
        return cnp_medic;
    }

    public void setCnp_medic(String cnp_medic) {
        this.cnp_medic = cnp_medic;
    }

    public void setData_consultatie(Date data_consultatie) {
        this.data_consultatie = data_consultatie;
    }

    public void setOra(Time ora) {
        this.ora = ora;
    }

    public void setOra_sfarsit(Time ora_sfarsit) {
        this.ora_sfarsit = ora_sfarsit;
    }
}
