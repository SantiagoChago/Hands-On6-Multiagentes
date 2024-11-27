import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentePolinomial extends Agent {
    private int grado = 3; // Por defecto, grado 3 (cúbico)
    @Override
    protected void setup() {
        System.out.println("Agente Polinomial iniciado : " + getLocalName());
        // Recibir el grado desde el AgentePrincipal (ejemplo: grado 2, 3, etc.)
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            grado = Integer.parseInt((String) args[0]);
            System.out.println("Grado de regresión configurado : " + grado);
        }
        addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage mensaje = receive();
                if (mensaje != null) {
                    System.out.println("Agente Polinomial Recibido : " + mensaje.getContent());
                    // Parsear los datos recibidos
                    String[] pares = mensaje.getContent().split(" ");
                    double[] x = new double[pares.length];
                    double[] y = new double[pares.length];
                    for (int i = 0; i < pares.length; i++) {
                        String[] valores = pares[i].replace("(", "").replace(")", "").split(",");
                        x[i] = Double.parseDouble(valores[0]);
                        y[i] = Double.parseDouble(valores[1]);
                    }
                    // Realizar la regresión polinomial con el grado especificado
                    RegresionPolinomial regresion = new RegresionPolinomial(grado); // Usar el grado seleccionado
                    regresion.fit(x, y);
                    float[] coeficientes = regresion.getCoeficientes();//obtiene los coficientes
                    // Mostrar la ecuación ajustada
                    StringBuilder ecuacion = new StringBuilder(" Ecuación ajustada y = ");
                    for (int i = 0; i < coeficientes.length; i++) {
                        System.out.printf("b%d: %.4f%n", i, coeficientes[i]);
                        if (i == 0) {
                            ecuacion.append(coeficientes[i]);
                        } else {
                            ecuacion.append(" + ").append(coeficientes[i]).append(" * X ").append(i);
                        }
                    }
                    System.out.println(ecuacion.toString());
                    // Hacer predicciones con cinco nuevos valores de X
                    double[] nuevosValoresX = {60, 70, 80, 90, 100}; // Nuevos valores de publicidad (o cualquier variable)
                    System.out.println(" Predicciones para nuevos valores de X :");
                    for (double valor : nuevosValoresX) {
                        double prediccion = predecirValor(coeficientes, valor);
                        System.out.println("Nuevo valor de X: " + valor + "  Predicción de Y: " + prediccion);
                    }
                    // Calcular R2
                    double r2 = calcularR2(x, y, coeficientes);
                    System.out.println("R 2: " + r2);
                    // Preparar la respuesta con los coeficientes
                    StringBuilder respuesta = new StringBuilder("Coeficientes Polinomiales: ");
                    for (int i = 0; i < coeficientes.length; i++) {
                        respuesta.append("b").append(i).append("=").append(coeficientes[i]).append(" ");
                    }
                    respuesta.append("\nR^2: ").append(r2);
                    ACLMessage mensajeRespuesta = mensaje.createReply();
                    mensajeRespuesta.setPerformative(ACLMessage.INFORM);
                    mensajeRespuesta.setContent(respuesta.toString().trim());
                    send(mensajeRespuesta);
                    System.out.println("Agente Polinomial enviado resultado.");
                } else {
                    block();
                }
            }
        });
    }
    //predicción usando los coeficientes y un valor de X
    private double predecirValor(float[] coeficientes, double x) {
        double prediccion = 0;
        for (int i = 0; i < coeficientes.length; i++) {
            prediccion += coeficientes[i] * Math.pow(x, i); // x elevado al exponente de i
        }
        return prediccion;
    }

    //calcular R2
    private double calcularR2(double[]x,double[]y,float[]coeficientes) {
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
}
