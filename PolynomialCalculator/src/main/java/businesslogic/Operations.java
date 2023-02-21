package businesslogic;

import datamodels.Monomyal;
import datamodels.Polynomial;

public class Operations {

    public static Polynomial add(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial polynomialResult = new Polynomial();
        Monomyal monomyalResult;
        for(Monomyal m1 : polynomial1.getPolynomial()) {
            for(Monomyal m2 : polynomial2.getPolynomial()) {
                if(m1.getDegree() == m2.getDegree()) {
                     monomyalResult = new Monomyal(m1.getCoefficient()+m2.getCoefficient(), m1.getDegree());
                     polynomialResult.addMon(monomyalResult);
                     m1.setDone(true);
                     m2.setDone(true);
                }
            }
        }
        for(Monomyal m1 : polynomial1.getPolynomial()) {
            if(m1.isDone() == false) {
                polynomialResult.addMon(m1);
               // m1.setDone(true);
            }
        }
        for(Monomyal m2 : polynomial2.getPolynomial()) {
            if(m2.isDone() == false) {
                polynomialResult.addMon(m2);
              //  m2.setDone(true);
            }
        }
        return polynomialResult;
    }

    public static Polynomial sub(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial polynomialResult = new Polynomial();
        Monomyal monomyalResult;
        for(Monomyal m1 : polynomial1.getPolynomial()) {
            for(Monomyal m2 : polynomial2.getPolynomial()) {
                if(m1.getDegree() == m2.getDegree()) {
                    monomyalResult = new Monomyal(m1.getCoefficient()-m2.getCoefficient(), m1.getDegree());
                    polynomialResult.addMon(monomyalResult);
                    m1.setDone(true);
                    m2.setDone(true);
                }
            }
        }
        for(Monomyal m1 : polynomial1.getPolynomial()) {
            if(m1.isDone() == false) {
                polynomialResult.addMon(m1);
                // m1.setDone(true);
            }
        }
        for(Monomyal m2 : polynomial2.getPolynomial()) {
            if(m2.isDone() == false) {
                m2.setCoefficient(-m2.getCoefficient());
                polynomialResult.addMon(m2);
                //  m2.setDone(true);
            }
        }
        return polynomialResult;
    }

    public static Polynomial multiplicate(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial polynomialResult = new Polynomial();
        Monomyal monomyalResult;
        for(Monomyal m1 : polynomial1.getPolynomial()) {
            for(Monomyal m2 : polynomial2.getPolynomial()) {
                    monomyalResult = new Monomyal(m1.getCoefficient()*m2.getCoefficient(), m1.getDegree()+m2.getDegree());
                    polynomialResult.addMon(monomyalResult);
            }
        }
        return polynomialResult;
    }

    public static Polynomial derivate(Polynomial polynomial1) {
        Polynomial polynomialResult = new Polynomial();
        Monomyal monomyalResult;
        for(Monomyal m1 : polynomial1.getPolynomial()) {
                    monomyalResult = new Monomyal(m1.getCoefficient()*m1.getDegree(), m1.getDegree()-1);
                    polynomialResult.addMon(monomyalResult);
        }
        return polynomialResult;
    }

    public static Polynomial integrate(Polynomial polynomial1) {
        Polynomial polynomialResult = new Polynomial();
        Monomyal monomyalResult;
        for(Monomyal m1 : polynomial1.getPolynomial()) {
            monomyalResult = new Monomyal(m1.getCoefficient(), m1.getDegree()+1, m1.getDegree()+1);
            polynomialResult.addMon(monomyalResult);
        }
        return polynomialResult;
    }

    public static Polynomial divide(Polynomial polynomial1, Polynomial polynomial2) {

        Polynomial polynomialResult = new Polynomial();
        Monomyal monomyalResult = new Monomyal();
        Monomyal monomyalResult2 = new Monomyal();
        Integer coefficient, degree;
        Polynomial firstResult = new Polynomial();
        Polynomial secondResult = new Polynomial();
        for(Monomyal m1 : polynomial1.getPolynomial()) {
            for (Monomyal m2 : polynomial2.getPolynomial()) {
                if (m2.getDegree() == 0 && m2.getCoefficient() == 0) {
                    System.out.println("Divide by 0 not allowed!");
                    return null;
                }
                if (m1.getDegree() < m2.getDegree()) {
                    monomyalResult = new Monomyal(0, 0);
                    polynomialResult.addMon(monomyalResult);
                    return polynomialResult;
                }
                coefficient = m1.getCoefficient() / m2.getCoefficient();
                degree = m1.getDegree() - m2.getDegree();
                monomyalResult.setDegree(degree);
                monomyalResult.setCoefficient(coefficient);

                firstResult.addMon(monomyalResult);

                secondResult = sub(polynomial1, (multiplicate(polynomial2, firstResult)));

                polynomialResult =  add(firstResult, (divide(polynomialResult,secondResult)));
             //   polynomialResult.addPol(Sum(firstResult, (Divide(polynomialResult,secondResult))));
                polynomialResult.toDisplay();
            }
        }
        return polynomialResult;
    }
}
