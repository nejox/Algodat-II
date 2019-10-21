package uebung1;

public class MatrixVektorMultiplikation {
	static double operationen = 0;

	public static void main(String[] args) {

		double[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		double[] vektor = { 4, 4, 4 };

		// Matrix-Vektor Multiplikation:
//		double[] result = doMultiply(matrix, vektor);
//		
//		for (int i = 0; i < result.length; i++) {
//			System.out.println(result[i]);
//		}
//		
//		System.out.println("Operationen: "+Operationen);

		// (A*x)/||A*x||
		for (int j = 0; j < 10; j++) {

			double[] result = doMultiply(matrix, vektor);
			double betrag = calcBetragVektor(result);

			for (int i = 0; i < result.length; i++) {
				result[i] = result[i] / betrag;
				System.out.println(result[i]);
			}
			
			vektor = result;
		}

	}

	public static double[] doMultiply(double[][] matrix, double[] vektor) {
		double[] result = new double[matrix.length];

		for (int i = 0; i < matrix.length; i++) {
			double erg = 0;
			for (int j = 0; j < matrix[i].length; j++) {
				erg += matrix[i][j] * vektor[j];
				operationen++;
			}
			result[i] = erg;
		}

		return result;
	}

	public static double calcBetragVektor(double[] vektor) {
		double erg = 0;

		for (int i = 0; i < vektor.length; i++) {
			erg += vektor[i] * vektor[i];
		}

		return Math.sqrt(erg);
	}
}
