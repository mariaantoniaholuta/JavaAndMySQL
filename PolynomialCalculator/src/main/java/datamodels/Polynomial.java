package datamodels;

import java.util.*;

public class Polynomial {

    private List<Monomyal> polynomial;

    public Polynomial() {
        polynomial = new ArrayList<Monomyal>();
    }
    public Polynomial(List<Monomyal> polynomial) {
        this.polynomial = polynomial;
    }

    public List<Monomyal> getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(List<Monomyal> polynomial) {
        this.polynomial = polynomial;
    }

    public void addMon(Monomyal m) {
        polynomial.add(m);
    }

    public void addPol(Polynomial m) {
        polynomial.addAll(m.getPolynomial());
    }

    public void toDisplay() {

        for(Monomyal m: polynomial) {
            System.out.print("(" + m.getCoefficient() + "," + m.getDegree() + ")");
        }
    }

    @Override
    public boolean equals(Object other) {
       if(this == other)
           return true;
       if(!(other instanceof Polynomial))
           return false;
       Polynomial p =(Polynomial) other;
       return polynomial.equals(p.polynomial);
    }
}
