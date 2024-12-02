public class RegresionSimple {
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

}

