package businesslogic;

import datamodels.Monomyal;
import datamodels.Polynomial;

import java.util.*;

public class MakePolynomial {

    public static Polynomial makePolynomial (String pol) {
        Polynomial polynomial = new Polynomial();
        String firstPol = "";
        firstPol = replacePlus(pol);

        System.out.println(pol);
        List<String> monoms = Arrays.asList(firstPol.split("\\+"));
        int coeff = 0, degree = 0;
        for (String s : monoms) {
            //calculate the coefficients
            int splitIndexCoeff = s.indexOf("*");
            if (splitIndexCoeff != -1) {
                coeff = Integer.parseInt(s.substring(0, splitIndexCoeff));
            } else if (s.charAt(0) == 'X') {
                coeff = 1;
            } else if (s.charAt(0) == '-' && s.charAt(1) == 'X') {
                coeff = -1;
            } else {
                coeff = Integer.parseInt(s);
            }
            //calculate the degree
            int splitIndexDegree = s.indexOf("^");
            if (splitIndexDegree != -1) {
                degree = Integer.parseInt(s.substring(splitIndexDegree + 1, s.length()));
            } else {
                degree = 1;
            }
            if (s.indexOf("X") == -1) {
                degree = 0;
            }
            Monomyal monomyal = new Monomyal(coeff, degree);
            polynomial.addMon(monomyal);
        }
        return polynomial;
    }

    // checks if the polynomial has more than one coefficient with the same degree (ex: X+1+X)
    public static Polynomial checkPolynomial(Polynomial polynomial) {
        Polynomial polynomialResult = new Polynomial();
        Monomyal monomyal;
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (Monomyal m : polynomial.getPolynomial()) {
            if (hash.containsKey(m.getDegree())) {
                hash.put(m.getDegree(), hash.get(m.getDegree()) + m.getCoefficient());
            } else {
                hash.put(m.getDegree(), m.getCoefficient());
            }
        }
        Iterator iter = hash.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry element = (Map.Entry) iter.next();
            monomyal = new Monomyal((Integer) element.getValue(), (Integer) element.getKey());
            polynomialResult.addMon(monomyal);
        }

        return polynomialResult;
    }

    static String replacePlus(String pol) {
        char prev = 0;
        String replacedPol = "";
        for(char ch:pol.toCharArray()) {
            if(ch == '-') {
                if( prev != '^') {
                    replacedPol = replacedPol + "+-";
                }
                else {
                    replacedPol = replacedPol + "-";
                }
            }
            else {
                replacedPol = replacedPol + ch;
            }
            prev = ch;
        }
        return replacedPol;
    }
}
