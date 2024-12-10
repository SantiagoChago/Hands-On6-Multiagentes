import java.util.Random;

public class AlgoritmoGenetico {
    private static final int Generaciones=500;
    private static final int POBLACION=50;
    private static final double MUTACION=0.05;
    private static final double CONVERGENCIA=1e-6;

    public static double[] optimizarSLR(double[]x,double[]y){
        Random random = new Random();
        Poblacion poblacion = new Poblacion(POBLACION, random);
        double mejorECMAnterior = Double.MAX_VALUE;
        for (int generacion = 0; generacion < Generaciones; generacion++) {
            poblacion.evaluarFitness(x, y);
            poblacion.ordenarPorFitness();
            Individuo mejor = poblacion.getMejor();
            if (Math.abs(mejor.getECM() - mejorECMAnterior) < CONVERGENCIA) break;
            mejorECMAnterior = mejor.getECM();
            Poblacion nuevaPoblacion = new Poblacion(POBLACION, random);
            nuevaPoblacion.getIndividuos()[0] = mejor; // Elitismo
            for (int i = 1; i < POBLACION; i++) {
                Individuo padre1 = poblacion.seleccionPorTorneo(random);
                Individuo padre2 = poblacion.seleccionPorTorneo(random);
                Individuo hijo = padre1.cruzar(padre2, random);
                if (random.nextDouble() < MUTACION) {
                    hijo.mutar(random);
                }
                nuevaPoblacion.getIndividuos()[i] = hijo;
            }
            poblacion.actualizarPoblacion(nuevaPoblacion);
        }
        Individuo mejor = poblacion.getMejor();
        return new double[]{mejor.getIntercepto(), mejor.getPendiente()};
    }
}
