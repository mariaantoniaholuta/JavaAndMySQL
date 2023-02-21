package graphicaluserinterface;

import businesslogic.MakePolynomial;
import businesslogic.Operations;
import datamodels.Monomyal;
import datamodels.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {

    private CalculatorView view;
    private int next = 0;

    public Polynomial polynomial1 = new Polynomial();
    public Polynomial polynomial2 = new Polynomial();
    public Polynomial polynomialResult = new Polynomial();
    public Monomyal nextMon = new Monomyal();

    public CalculatorController(CalculatorView view) {

        this.view = view;

        this.view.addClearListener(new ClearListener());
        this.view.addDeleteListener(new DeleteListener());
        this.view.addNextListener(new NextListener());
        this.view.add0Listener(new B0Listener());
        this.view.add1Listener(new B1Listener());
        this.view.add2Listener(new B2Listener());
        this.view.add3Listener(new B3Listener());
        this.view.add4Listener(new B4Listener());
        this.view.add5Listener(new B5Listener());
        this.view.add6Listener(new B6Listener());
        this.view.add7Listener(new B7Listener());
        this.view.add8Listener(new B8Listener());
        this.view.add9Listener(new B9Listener());
        this.view.addPlusListener(new PlusListener());
        this.view.addMinusListener(new MinusListener());
        this.view.addMultListener(new MultListener());
        this.view.addPowerListener(new PowerListener());
        this.view.addXListener(new XListener());

        this.view.addSumListener(new SumListener());
        this.view.addSubListener(new SubListener());
        this.view.addMultiplicateListener(new MultiplicateListener());
        this.view.addDerivativeListener(new DerivateListener());
        this.view.addIntegrateListener(new IntegrateListener());
        this.view.addDivideListener(new DivideListener());

    }

    class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setFirstPolFld("");
            view.setSecondPolFld("");
            view.setResultFld("");
        }
    }

    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld().substring(0, view.getfirstPolFld().length() - 1));
            } else {
                view.setSecondPolFld(view.getSecondPolFld().substring(0, view.getSecondPolFld().length() - 1));
            }
        }
    }

    class NextListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (next == 0) {
                view.getSecondPolLbl().setForeground(new Color(198, 211, 232));
                view.getFirstPolLbl().setForeground(new Color(147, 158, 172));
                next = 1;
            } else {
                view.getFirstPolLbl().setForeground(new Color(198, 211, 232));
                view.getSecondPolLbl().setForeground(new Color(147, 158, 172));
                next = 0;
            }
        }
    }

    class B0Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "0");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "0");
            }
        }
    }

    class B1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "1");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "1");
            }
        }
    }

    class B2Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "2");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "2");
            }
        }
    }

    class B3Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "3");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "3");
            }
        }
    }

    class B4Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "4");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "4");
            }
        }
    }

    class B5Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "5");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "5");
            }
        }
    }

    class B6Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "6");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "6");
            }
        }
    }

    class B7Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "7");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "7");
            }
        }
    }

    class B8Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "8");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "8");
            }
        }
    }

    class B9Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "9");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "9");
            }
        }
    }

    class PlusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "+");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "+");
            }
        }
    }

    class MinusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "-");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "-");
            }
        }
    }

    class MultListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "*");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "*");
            }
        }
    }

    class XListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "X");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "X");
            }
        }
    }

    class PowerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (next == 0) {
                view.setFirstPolFld(view.getfirstPolFld() + "^");
            } else {
                view.setSecondPolFld(view.getSecondPolFld() + "^");
            }
        }
    }

    class SumListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setResultFld("");
            polynomialResult.getPolynomial().clear();

            String result = "";
            String finalResult = "";

            polynomial1 = MakePolynomial.makePolynomial(view.getfirstPolFld());
            polynomial1 = MakePolynomial.checkPolynomial(polynomial1);

            polynomial2 = MakePolynomial.makePolynomial(view.getSecondPolFld());
            polynomial2 = MakePolynomial.checkPolynomial(polynomial2);

            polynomialResult = Operations.add(polynomial1, polynomial2);

            for (Monomyal m : polynomialResult.getPolynomial()) {
                result = result + m.toString() + "+";
            }
            finalResult = result.substring(0, result.length() - 1);

            if(finalResult == "") {
                finalResult = "0";
            }

            finalResult = finalResult.replace("+-", "-");
            if(finalResult == "") {
                finalResult = "0";
            }
            System.out.println(finalResult);
            view.setResultFld(finalResult);
        }
    }

    class SubListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setResultFld("");
            polynomialResult.getPolynomial().clear();
            String result = "";
            String finalResult = "";

            polynomial1 = MakePolynomial.makePolynomial(view.getfirstPolFld());
            polynomial1 = MakePolynomial.checkPolynomial(polynomial1);

            polynomial2 = MakePolynomial.makePolynomial(view.getSecondPolFld());
            polynomial2 = MakePolynomial.checkPolynomial(polynomial2);

            polynomialResult = Operations.sub(polynomial1, polynomial2);

            for (Monomyal m : polynomialResult.getPolynomial()) {
                result = result + m.toString() + "+";
            }

            if(finalResult == "") {
                finalResult = "0";
            }

            finalResult = result.substring(0, result.length() - 1);
            finalResult = finalResult.replace("+-", "-");
            System.out.println(finalResult);
            view.setResultFld(finalResult);
        }
    }

    class MultiplicateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setResultFld("");
            polynomialResult.getPolynomial().clear();
            String result = "";
            String finalResult = "";

            polynomial1 = MakePolynomial.makePolynomial(view.getfirstPolFld());
            polynomial1 = MakePolynomial.checkPolynomial(polynomial1);

            polynomial2 = MakePolynomial.makePolynomial(view.getSecondPolFld());
            polynomial2 = MakePolynomial.checkPolynomial(polynomial2);

            polynomialResult = Operations.multiplicate(polynomial1, polynomial2);

            for (Monomyal m : polynomialResult.getPolynomial()) {
                result = result + m.toString() + "+";
            }
            finalResult = result.substring(0, result.length() - 1);
            finalResult = finalResult.replace("+-", "-");

            if(finalResult == "") {
                finalResult = "0";
            }

            if (finalResult.charAt(0) == '+' || finalResult.charAt(finalResult.length() - 1) == '+') {
                finalResult = finalResult.substring(1, finalResult.length());
            }

            System.out.println(finalResult);
            view.setResultFld(finalResult);
        }
    }

    class DerivateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setResultFld("");
            polynomialResult.getPolynomial().clear();
            String result = "";
            String finalResult = "";

            polynomial1 = MakePolynomial.makePolynomial(view.getfirstPolFld());
            polynomial1 = MakePolynomial.checkPolynomial(polynomial1);

            polynomialResult = Operations.derivate(polynomial1);
            for (Monomyal m : polynomialResult.getPolynomial()) {
                result = result + m.toString() + "+";
            }
            finalResult = result.substring(0, result.length() - 1);
            finalResult = finalResult.replace("+-", "-");

            if(finalResult == "") {
                finalResult = "0";
            }

            if (finalResult.charAt(0) == '+' || finalResult.charAt(finalResult.length() - 1) == '+') {
                finalResult = finalResult.substring(1, finalResult.length());
            }
            for (Monomyal m : polynomialResult.getPolynomial()) {
                System.out.println(m.getCoefficient() + " " + m.getDegree());
            }
            System.out.println(finalResult);
            view.setResultFld(finalResult);
            JOptionPane.showMessageDialog(view.getCalculatorPanel(), "Note: The result is from the first polynomial!");
        }
    }

    class IntegrateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setResultFld("");

            polynomialResult.getPolynomial().clear();
            String result = "";
            String finalResult = "";

            polynomial1 = MakePolynomial.makePolynomial(view.getfirstPolFld());
            polynomial1 = MakePolynomial.checkPolynomial(polynomial1);

            polynomialResult = Operations.integrate(polynomial1);
            for (Monomyal m : polynomialResult.getPolynomial()) {
                result = result + m.toStringForIntegration() + "+";
            }
            finalResult = result.substring(0, result.length() - 1);
            finalResult = finalResult.replace("+-", "-");

            if(finalResult == "") {
                finalResult = "0";
            }

            if (finalResult.charAt(0) == '+' || finalResult.charAt(finalResult.length() - 1) == '+') {
                finalResult = finalResult.substring(1, finalResult.length());
            }

            System.out.println(finalResult);
            view.setResultFld(finalResult);
            JOptionPane.showMessageDialog(view.getCalculatorPanel(), "Note: The result is from the first polynomial!");
        }
    }

    class DivideListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setResultFld("");
            polynomialResult.getPolynomial().clear();
            String result = "";
            String finalResult = "";

            polynomial1 = MakePolynomial.makePolynomial(view.getfirstPolFld());
            polynomial1 = MakePolynomial.checkPolynomial(polynomial1);

            polynomial2 = MakePolynomial.makePolynomial(view.getSecondPolFld());
            polynomial2 = MakePolynomial.checkPolynomial(polynomial2);

            polynomialResult = Operations.divide(polynomial1, polynomial2);
            for (Monomyal m : polynomialResult.getPolynomial()) {
                result = result + m.toString() + "+";
            }
            finalResult = result.substring(0, result.length() - 1);
            finalResult = finalResult.replace("+-", "-");

            if(finalResult == "") {
                finalResult = "0";
            }

            if (finalResult.charAt(0) == '+' || finalResult.charAt(finalResult.length() - 1) == '+') {
                finalResult = finalResult.substring(1, finalResult.length());
            }
            System.out.println(finalResult);
            view.setResultFld(finalResult);
        }
    }
}

