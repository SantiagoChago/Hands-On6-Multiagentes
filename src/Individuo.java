import java.util.Random;

public class Individuo {
    private double intercepto;
    private double pendiente;
    private double ecm; // Error cuadrático medio (fitness)
    public Individuo(double intercepto, double pendiente) {
        this.intercepto = intercepto;
        this.pendiente = pendiente;
    }
    public double getIntercepto() { return intercepto; }
    public double getPendiente() { return pendiente; }
    public double getECM() { return ecm; }

    public void setECM(double ecm) {
        this.ecm = ecm;
    }

    public double predecir(double x) {
        return intercepto + pendiente * x;
    }
    public Individuo cruzar(Individuo otro, Random random) {
        double alpha = random.nextDouble();
        double nuevoIntercepto=alpha*this.intercepto+(1-alpha)*otro.intercepto;
        double nuevaPendiente=alpha*this.pendiente+(1-alpha)*otro.pendiente;
        return new Individuo(nuevoIntercepto, nuevaPendiente);
    }

    public void mutar(Random random) {
        this.intercepto += random.nextGaussian() * 0.5;
        this.pendiente += random.nextGaussian() * 0.5;
    }
}
