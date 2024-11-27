public class RegresionMultiple {

    public static double[] fit(double[][] x, double[] y) {
        int n =x.length;
        int m =x[0].length;
        float[][] X = new float[n][m + 1];
        for (int i=0;i<n;i++) {
            X[i][0] =1.0f; // Columna de 1s
            for (int j=0;j<m;j++) {
                X[i][j+1]=(float)x[i][j];
            }
        }
        float[] Y = new float[y.length];
        for (int i = 0; i < y.length; i++) {
            Y[i] = (float) y[i];
        }
        // Resolver el sistema usando MateDiscret
        float[] coeficientesFloat = MateDiscret.resolver(X, Y);
        // Convertir de float a double para retornar el resultado
        double[] coeficientes =new double[coeficientesFloat.length];
        for (int i=0;i<coeficientesFloat.length; i++) {
            coeficientes[i] = coeficientesFloat[i];
        }
        // Calcular las predicciones usando los coeficientes
        double[] yPredicha =new double[y.length];
        for (int i =0;i<y.length;i++) {
            yPredicha[i] = 0;
            for (int j=0;j<coeficientes.length;j++){
                yPredicha[i] += coeficientes[j]*(j==0? 1:x[i][j-1]);  // j == 0 para el término constante
            }
        }
        // Calcular Rcuadrada
        double rCuadrada =MateDiscret.calcularRCuadrada(y, yPredicha);
        System.out.printf("R cuadrada : %.4f%n", rCuadrada);
        return coeficientes;
    }
}
