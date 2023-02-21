package ro.tuc.tp;
import businesslogic.Operations;
import datamodels.Monomyal;
import datamodels.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SumOperationTest {
    @Test
    public void sumTest() {

        Monomyal m1 = new Monomyal(3,2);
        Monomyal m2 = new Monomyal(-1,1);
        List<Monomyal> list1 = new ArrayList<>(Arrays.asList(m1,m2));

        Monomyal m3 = new Monomyal(2,1);
        List<Monomyal> list2 = new ArrayList<>(Arrays.asList(m3));

        Monomyal m4 = new Monomyal(1,1);
        List<Monomyal> list3 = new ArrayList<>(Arrays.asList(m4,m1));

        Polynomial polynomial1 = new Polynomial(list1);
        Polynomial polynomial2 = new Polynomial(list2);
        Polynomial polynomial3 = new Polynomial(list3);

        String result = "";
        String finalResult = "";
        polynomial3 = Operations.add(polynomial1,polynomial2);
        for( Monomyal m : polynomial3.getPolynomial()) {
            result = result + m.toString() + "+";
        }
        finalResult = result.substring(0,result.length()-1);
        finalResult = finalResult.replace("+-", "-");

      //  assertTrue(Operations.Sum(polynomial1,polynomial2).equals(polynomial3));
        assertEquals(finalResult,"X+3*X^2");
    }
}
