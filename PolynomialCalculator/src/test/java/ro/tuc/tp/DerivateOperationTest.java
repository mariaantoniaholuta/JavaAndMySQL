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

public class DerivateOperationTest {
    @Test
    public void sumTest() {
        Monomyal m1 = new Monomyal(3,2);
        Monomyal m2 = new Monomyal(-1,1);

        Monomyal m3 = new Monomyal(6,1);
        Monomyal m4 = new Monomyal(-1,0);

        Polynomial polynomialTest1 = new Polynomial();
        polynomialTest1.addMon(m1);
        Polynomial polynomialTest2 = new Polynomial();
        polynomialTest2.addMon(m3);

        String result = "";
        String finalResult = "";
        polynomialTest2 = Operations.derivate(polynomialTest1);
        for( Monomyal m : polynomialTest2.getPolynomial()) {
            result = result + m.toString() + "+";
        }
        finalResult = result.substring(0,result.length()-1);
        finalResult = finalResult.replace("+-", "-");

      //  assertTrue(Operations.Derivate(polynomialTest1).equals(polynomialTest2));
         assertEquals(finalResult,"6*X");
    }
}