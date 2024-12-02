public class RCuadrada {
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
    public static double calcularR2(double[]x,double[]y,float[]coeficientes) {
        double yMedia = 0;
        for (double valor : y) {
            yMedia += valor;
        }
        yMedia /= y.length;
        double ssTotal = 0;
        double ssResidual = 0;
        for (int i = 0; i < x.length; i++) {
            double yPredicho = predecirValor(coeficientes, x[i]);
            ssTotal += Math.pow(y[i] - yMedia, 2);
            ssResidual += Math.pow(y[i] - yPredicho, 2);
        }
        return 1 - (ssResidual / ssTotal);
    }
    public static double predecirValor(float[] coeficientes, double x) {
        double prediccion = 0;
        for (int i = 0; i < coeficientes.length; i++) {
            prediccion += coeficientes[i] * Math.pow(x, i);
        }
        return prediccion;
    }

}
