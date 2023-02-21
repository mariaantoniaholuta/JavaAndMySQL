package com.utcn.models;

import com.utcn.Modul1.Tip;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Asistent_medical extends Angajat {

    String cnp;
    Grad_Asistent grad;
    Tip tip;

    public Asistent_medical(ResultSet resultSet, String cnp, Grad_Asistent grad, Tip tip) throws SQLException {
        super(resultSet);
        this.cnp = cnp;
        this.grad = grad;
        this.tip = tip;
    }

    @Override
    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Grad_Asistent getGrad() {
        return grad;
    }

    public void setGrad(Grad_Asistent grad) {
        this.grad = grad;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

}
