import java.util.Arrays;
import java.util.Random;

public class AlgoritmoGenetico {
    private static final int GENERACIONES = 500;
    private static final int POBLACION = 50;
    private static final double MUTACION = 0.05;
    private static final double CONVERGENCIA = 1e-6;// Equivale a 0.000001;
    public static double[] optimizarSLR(double[] x, double[] y) {
        Random random = new Random();
        // Inicializar población con mejores límites iniciales
        double[][] poblacion = new double[POBLACION][2];
        for (int i = 0; i < POBLACION; i++) {
            poblacion[i][0] = random.nextDouble() * 20 - 10; // intercepto inicial [-10, 10]
            poblacion[i][1] = random.nextDouble() * 20 - 10; // pendiente inicial [-10, 10]
        }
        double mejorECMAnterior = Double.MAX_VALUE;
        for (int generacion = 0; generacion < GENERACIONES; generacion++) {
            // Evaluar aptitud (fitness) de cada individuo
            double[] aptitudes = new double[POBLACION];
            for (int i = 0; i < POBLACION; i++) {
                aptitudes[i] = calcularECM(x, y, poblacion[i]);
            }
            // Ordenar la población según el ECM
            ordenarPoblacionPorFitness(poblacion, aptitudes);
            // Mostrar progreso cada 50 generaciones
            if (generacion % 50 == 0) {
                System.out.println("Generación " + generacion + ": Mejor ECM = " + String.format("%.6f", aptitudes[0]) + ", Intercepto = " + String.format("%.4f", poblacion[0][0]) + ", Pendiente = " + String.format("%.4f", poblacion[0][1]));

            }
            // Criterio de convergencia
            if (Math.abs(aptitudes[0] - mejorECMAnterior) < CONVERGENCIA) {
                break;
            }
            mejorECMAnterior = aptitudes[0];
            // Generar nueva población con elitismo
            double[][] nuevaPoblacion = new double[POBLACION][2];
            nuevaPoblacion[0] = poblacion[0]; // Elitismo: copiar el mejor individuo
            for (int i = 1; i < POBLACION; i++) {
                double[] padre1 = seleccionPorTorneo(poblacion, aptitudes, random);
                double[] padre2 = seleccionPorTorneo(poblacion, aptitudes, random);
                nuevaPoblacion[i] = cruzar(padre1, padre2, random);
            }
            // Aplicar mutación
            for (int i = 1; i < POBLACION; i++) { // Evitar mutar el mejor individuo
                if (random.nextDouble() < MUTACION) {
                    mutar(nuevaPoblacion[i], random);
                }
            }

            poblacion = nuevaPoblacion;
        }

        return poblacion[0];
    }
    private static double calcularECM(double[] x, double[] y, double[] coeficientes) {
        double error = 0;
        for (int i = 0; i < x.length; i++) {
            double prediccion = coeficientes[0] + coeficientes[1] * x[i];
            error += Math.pow(prediccion - y[i], 2);
        }
        return error / x.length;
    }
    private static void ordenarPoblacionPorFitness(double[][] poblacion, double[] aptitudes) {
        // Ordenar con Arrays.sort
        Integer[] indices = new Integer[POBLACION];
        for (int i = 0; i < POBLACION; i++) indices[i] = i;
        Arrays.sort(indices, (a, b) -> Double.compare(aptitudes[a], aptitudes[b]));
        double[][] nuevaPoblacion = new double[POBLACION][2];
        double[] nuevasAptitudes = new double[POBLACION];
        for (int i = 0; i < POBLACION; i++) {
            nuevaPoblacion[i] = poblacion[indices[i]].clone();
            nuevasAptitudes[i] = aptitudes[indices[i]];
        }

        System.arraycopy(nuevaPoblacion, 0, poblacion, 0, POBLACION);
        System.arraycopy(nuevasAptitudes, 0, aptitudes, 0, POBLACION);
    }
    private static double[] seleccionPorTorneo(double[][] poblacion, double[] aptitudes, Random random) {
        int idx1 = random.nextInt(POBLACION);
        int idx2 = random.nextInt(POBLACION);
        return aptitudes[idx1] < aptitudes[idx2] ? poblacion[idx1] : poblacion[idx2];
    }
    private static double[] cruzar(double[] padre1, double[] padre2, Random random) {
        double alpha = random.nextDouble();
        double[] hijo = new double[2];
        hijo[0] = alpha * padre1[0] + (1 - alpha) * padre2[0];
        hijo[1] = alpha * padre1[1] + (1 - alpha) * padre2[1];
        return hijo;
    }
    private static void mutar(double[] individuo, Random random) {
        individuo[0] += random.nextGaussian() * 0.5;
        individuo[1] += random.nextGaussian() * 0.5;
    }
}
