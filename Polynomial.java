public class Polynomial {
	
	double[] coefficients;
	
	public Polynomial() {
		coefficients = new double [0];
	}
	
	public Polynomial(double[] coefficients) {
		this.coefficients = coefficients;
	}
	
	public Polynomial add(Polynomial x) {
		Polynomial newpoly = new Polynomial();
		if (coefficients.length >= x.coefficients.length) {
			newpoly.coefficients = coefficients;
		} else {
			newpoly.coefficients = x.coefficients;
		}
		for (int i = 0; i < Math.min(coefficients.length, x.coefficients.length); i++) {
			newpoly.coefficients[i] = coefficients[i] + x.coefficients[i];
		}
		return newpoly;
	}
	
	public double evaluate(double value) {
		double sum = 0;
		
		for (int i = 0; i < coefficients.length; i++) {
			sum += coefficients[i] * Math.pow(value, i);
		}
		
		return (sum);
	}
	
	public boolean hasRoot(double root) {
		return (evaluate(root) == 0);
	}
}
