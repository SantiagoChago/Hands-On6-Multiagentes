public class RegresionPolinomial {
    private int grado;
    private float[] coeficientes;
    public RegresionPolinomial(int grado) {
        this.grado = grado;
    }
    public void fit(double[] x, double[] y) {
        int n = x.length;
        float[][] matrizDiseño = new float[n][grado + 1];
        // Construir la matriz de diseño
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= grado; j++) {
                matrizDiseño[i][j] = (float) Math.pow(x[i], j); // Convertir a float
            }
        }
        // Resolver el sistema de ecuaciones
        this.coeficientes = MateDiscret.resolver(matrizDiseño, convertirAFloat(y));
    }
    private float[] convertirAFloat(double[] y) {
        float[] resultado = new float[y.length];
        for (int i = 0; i < y.length; i++) {
            resultado[i] = (float) y[i];
        }
        return resultado;
    }
    public float[] getCoeficientes() {
        return coeficientes;
    }
}
