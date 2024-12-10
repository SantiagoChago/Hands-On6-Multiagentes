import java.util.Arrays;
import java.util.Random;

public class Poblacion {
    private Individuo[] individuos;

    public Poblacion(int tamano, Random random) {
        individuos = new Individuo[tamano];
        for (int i = 0; i < tamano; i++) {
            double intercepto = random.nextDouble() * 20 - 10; // [-10, 10]
            double pendiente = random.nextDouble() * 20 - 10; // [-10, 10]
            individuos[i] = new Individuo(intercepto, pendiente);
        }
    }
    public Individuo getMejor() {
        return individuos[0];
    }
    public void evaluarFitness(double[] x, double[] y) {
        for (Individuo ind:individuos) {
            double error=0;
            for (int i=0;i<x.length;i++) {
                double prediccion=ind.predecir(x[i]);
                error+=Math.pow(prediccion-y[i], 2);
            }
            ind.setECM(error/x.length);
        }
    }

    public void ordenarPorFitness() {
        Arrays.sort(individuos, (a, b) -> Double.compare(a.getECM(), b.getECM()));
    }
    public Individuo seleccionPorTorneo(Random random) {
        Individuo ind1 = individuos[random.nextInt(individuos.length)];
        Individuo ind2 = individuos[random.nextInt(individuos.length)];
        return (ind1.getECM() < ind2.getECM()) ? ind1 : ind2;
    }

    public void actualizarPoblacion(Poblacion nuevaPoblacion) {
        this.individuos = nuevaPoblacion.individuos;
    }

    public Individuo[] getIndividuos() {
        return individuos;
    }
}
