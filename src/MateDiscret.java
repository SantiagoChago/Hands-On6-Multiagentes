public class MateDiscret {
    // transponer una matriz
    public static float[][] transponerMatriz(float[][] matriz) {
        int fila = matriz.length;
        int columna = matriz[0].length;
        float[][] transpuesta = new float[columna][fila];
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                transpuesta[j][i] = matriz[i][j];
            }
        }
        return transpuesta;
    }
    //  multiplicar dos matrices
    public static float[][] multiplicar(float[][] A, float[][] B) {
        int filaA = A.length;
        int columnaA = A[0].length;
        int columnaB = B[0].length;
        float[][] resultado = new float[filaA][columnaB];
        for (int i = 0; i < filaA; i++) {
            for (int j = 0; j < columnaB; j++) {
                for (int k = 0; k < columnaA; k++) {
                    resultado[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return resultado;
    }
    // multiplicar una matriz por un vector
    public static float[] multiplicarPorVector(float[][] A, float[] B) {
        int filaA = A.length;
        int columnaA = A[0].length;
        float[] resultado = new float[filaA];
        for (int i = 0; i < filaA; i++) {
            for (int j = 0; j < columnaA; j++) {
                resultado[i] += A[i][j] * B[j];
            }
        }
        return resultado;
    }
    //  resolver un sistema de ecuaciones usando la eliminación gaussiana
    public static float[] resolver(float[][] X, float[] y) {
        float[][] Xt = transponerMatriz(X); // matriz transpuesta X^T
        float[][] XtX = multiplicar(Xt, X); // producto X^T * X
        float[] XtY = multiplicarPorVector(Xt, y); // producto X^T * y
        return EliminacionGaussiana.resolver(XtX, XtY); // Resolver el sistema
    }
    public static double predecirValor(double[]coeficientes,double x){
        double prediccion = coeficientes[0];//Intercepto
        for (int i = 1; i < coeficientes.length; i++){
            prediccion += coeficientes[i] * Math.pow(x, i);//Para modelos polinómicos
        }
        return prediccion;
    }
}
