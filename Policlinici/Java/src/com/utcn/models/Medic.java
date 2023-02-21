package com.utcn.models;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Medic extends Angajat {

    String postDidactic, cnp;
    int codParafa;
    public Array competente;

    public Medic(ResultSet resultSet, String postDidactic, String cnp, int codParafa, Array competente) throws SQLException {
        super(resultSet);
        this.postDidactic = postDidactic;
        this.cnp = cnp;
        this.codParafa = codParafa;
        this.competente = competente;
    }

    public String getPostDidactic() {
        return postDidactic;
    }

    public void setPostDidactic(String postDidactic) {
        this.postDidactic = postDidactic;
    }


    @Override
    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public int getCodParafa() {
        return codParafa;
    }

    public void setCodParafa(int codParafa) {
        this.codParafa = codParafa;
    }
}
