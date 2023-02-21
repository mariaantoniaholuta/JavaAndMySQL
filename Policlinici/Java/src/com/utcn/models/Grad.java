package com.utcn.models;

public class Grad {
    String cnp, denumire_specialitate;
    Grad_Medic grad;

    public Grad(String cnp, String denumire_specialitate, Grad_Medic grad) {
        this.cnp = cnp;
        this.denumire_specialitate = denumire_specialitate;
        this.grad = grad;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getDenumire_specialitate() {
        return denumire_specialitate;
    }

    public void setDenumire_specialitate(String denumire_specialitate) {
        this.denumire_specialitate = denumire_specialitate;
    }

    public Grad_Medic getGrad() {
        return grad;
    }

    public void setGrad(Grad_Medic grad) {
        this.grad = grad;
    }

}
