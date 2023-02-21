package datamodels;

public class Monomyal {

    boolean done;
    private int coefficient;
    private int degree;
    private int denominator;

    public Monomyal() {
    }

    public Monomyal(int coefficient, int degree, int denominator) {
        this.coefficient = coefficient;
        this.degree = degree;
        this.denominator = denominator;
    }

    public Monomyal(int coefficient, int degree) {
        this.coefficient = coefficient;
        this.degree = degree;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        if(coefficient == 0) {
          return "";
        }
        else if(coefficient == 1 && degree == 0) {
            return "1";
        }
        else if(coefficient == 1 && degree == 1) {
            return "X";
        }
        else if (coefficient == 1 && degree != 1) {
            return "X^" + degree;
        }
        else if(coefficient == -1 && degree == 0) {
            return "-1";
        }
        else if(coefficient == -1 && degree == 1) {
            return "-X";
        }
        else if (coefficient == -1) {
            return "-X^" + degree;
        }
        else if (degree == 0) {
            return coefficient + "";
        }
        else if (degree == 1) {
            return coefficient + "*X";
        }
        else {
            return coefficient + "*X^" + degree;
        }
    }

    public String toStringForIntegration() {
        String result="";
        if(coefficient == 0) {
            result = "";
        }
        else if(coefficient == 1 && degree == 0) {
            result = "1";
        }
        else if(coefficient == 1 && degree == 1) {
            result = "X";
        }
        else if (coefficient == 1 && degree != 1) {
            result = "X^" + degree;
        }
        else if(coefficient == -1 && degree == 0) {
            result = "-1";
        }
        else if(coefficient == -1 && degree == 1) {
            result = "-X";
        }
        else if (coefficient == -1) {
            result = "-X^" + degree;
        }
        else if (degree == 0) {
            result = coefficient + "";
        }
        else if (degree == 1) {
            result = coefficient + "*X";
        }
        else {
            result = coefficient + "*X^" + degree;
        }
        if(denominator != 1 && denominator != 0) {
            result = result + "/" + denominator;
        }
        return result;
    }
}
