import java.io.*;
import java.util.Arrays;

public class Polynomial {

    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial x) {
        int maxDegree = Math.max(this.getMaxExponent(), x.getMaxExponent());
        double[] addedCoefficients = new double[maxDegree + 1];

        for (int i = 0; i < this.coefficients.length; i++) {
            int exponent = this.exponents[i];
            addedCoefficients[exponent] += this.coefficients[i];
        }

        for (int i = 0; i < x.coefficients.length; i++) {
            int exponent = x.exponents[i];
            addedCoefficients[exponent] += x.coefficients[i];
        }

        int[] newExponents = new int[addedCoefficients.length];
        for (int i = 0; i < newExponents.length; i++) {
        	newExponents[i] = i;
        }

        return new Polynomial(addedCoefficients, newExponents);
    }


    public double evaluate(double value) {
        double sum = 0;

        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(value, exponents[i]);
        }

        return sum;
    }

    public boolean hasRoot(double root) {
        return (evaluate(root) == 0);
    }

    public Polynomial multiply(Polynomial x) {
        double[] multipliedCoefficients = new double[this.coefficients.length * x.coefficients.length];
        int[] newExponents = new int[this.coefficients.length * x.coefficients.length];

        int index = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < x.coefficients.length; j++) {
            	multipliedCoefficients[index] = this.coefficients[i] * x.coefficients[j];
            	newExponents[index] = this.exponents[i] + x.exponents[j];
                index++;
            }
        }

        return new Polynomial(multipliedCoefficients, newExponents);
    }

    public Polynomial(File file) throws IOException {
        BufferedReader b = new BufferedReader(new FileReader(file));
        String equation = b.readLine();
        b.close();

        String[] digits = equation.split("[+-]");
        double[] parsedCoefficients = new double[digits.length];
        int[] parsedExponents = new int[digits.length];

        for (int i = 0; i < digits.length; i++) {
            String digit = digits[i].trim();
            if (digit.isEmpty()) continue;

            if (digit.charAt(0) == 'x') {
                parsedCoefficients[i] = digit.startsWith("-") ? -1 : 1;
                parsedExponents[i] = digit.length() == 1 ? 1 : Integer.parseInt(digit.substring(2));
            } else {
                parsedCoefficients[i] = Double.parseDouble(digit);
                parsedExponents[i] = 0;
            }
        }

        this.coefficients = parsedCoefficients;
        this.exponents = parsedExponents;
    }

    public void saveToFile(String fileName) throws IOException {
        BufferedWriter b = new BufferedWriter(new FileWriter(fileName));
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] == 0) continue;

            if (coefficients[i] > 0 && i > 0) {
                b.write("+");
            }
            b.write(coefficients[i] + "x" + (exponents[i] == 1 ? "" : exponents[i]));
        }
        b.newLine();
        b.close();
    }

    private int getMaxExponent() {
        int maxExponent = Integer.MIN_VALUE;
        for (int exp : exponents) {
            if (exp > maxExponent) {
                maxExponent = exp;
            }
        }
        return maxExponent;
    }
}
