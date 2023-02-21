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

public class IntegrateOperationTest {
    @Test
    public void sumTest() {
        Monomyal m1 = new Monomyal(3,3);
        Monomyal m2 = new Monomyal(-1,1);

        Monomyal m3 = new Monomyal(3,4,4);
        Monomyal m4 = new Monomyal(-1,2,2);

        Polynomial polynomial1 = new Polynomial();
        polynomial1.addMon(m1);
        polynomial1.addMon(m2);
        Polynomial polynomial2 = new Polynomial();
        polynomial2.addMon(m4);
        polynomial2.addMon(m3);

        Polynomial polynomial3 = new Polynomial();

        String result = "";
        String finalResult = "";
        polynomial2= Operations.integrate(polynomial1);
        for( Monomyal m : polynomial2.getPolynomial()) {
            result = result + m.toString() + "+";
        }
        finalResult = result.substring(0,result.length()-1);
        finalResult = finalResult.replace("+-", "-");

     //   assertTrue(Operations.Integrate(polynomial1).equals(polynomial2));
        assertEquals(finalResult,"3*X^4-X^2");
    }
}