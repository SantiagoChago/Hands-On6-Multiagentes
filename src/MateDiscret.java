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

    //  para calcular R cuadrada
    public static double calcularRCuadrada(double[] y, double[] yPredicha) {
        double sumaTotal =0,sumaResiduos =0;
        double mediaY =0;
        for (double valor : y){
            mediaY += valor;
        }
        mediaY /=y.length;
        for (int i = 0; i < y.length; i++) {
            sumaTotal +=Math.pow(y[i] - mediaY, 2);
            sumaResiduos +=Math.pow(y[i]-yPredicha[i], 2);
        }
        return 1-(sumaResiduos/sumaTotal);
    }
    //  calcular los coeficientes de regresión lineal simple (pendiente e intercepto)
    public static double[] calcularSLR(double[] x, double[] y) {
        int n =x.length;
        double sumX=0,sumY=0,sumXY=0,sumX2=0;
        for (int i=0;i<n;i++) {
            sumX +=x[i];
            sumY +=y[i];
            sumXY +=x[i] * y[i];
            sumX2 +=x[i] * x[i];
        }
        double pendiente =(n * sumXY - sumX * sumY)/(n * sumX2 - sumX * sumX);
        double intercepto =(sumY - pendiente * sumX)/n;
        return new double[]{intercepto, pendiente};
    }
    // predecir valores usando los coeficientes
    public static double predecirValor(double[]coeficientes,double x){
        double prediccion = coeficientes[0];//Intercepto
        for (int i = 1; i < coeficientes.length; i++){
            prediccion += coeficientes[i] * Math.pow(x, i);//Para modelos polinómicos
        }
        return prediccion;
    }
}
