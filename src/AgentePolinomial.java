import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentePolinomial extends Agent {
    private int grado = 3;
    @Override
    protected void setup() {
        System.out.println("Agente Polinomial iniciado : " + getLocalName());
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
                    String[] pares = mensaje.getContent().split(" ");
                    double[] x = new double[pares.length];
                    double[] y = new double[pares.length];
                    for (int i = 0; i < pares.length; i++) {
                        String[] valores = pares[i].replace("(", "").replace(")", "").split(",");
                        x[i] = Double.parseDouble(valores[0]);
                        y[i] = Double.parseDouble(valores[1]);
                    }
                    RegresionPolinomial regresion = new RegresionPolinomial(grado); // Usar el grado seleccionado
                    regresion.fit(x, y);
                    float[] coeficientes = regresion.getCoeficientes();
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
                    double[] nuevosValoresX = {60, 70, 80, 90, 100}; // Nuevos valores de publicidad (o cualquier variable)
                    System.out.println(" Predicciones para nuevos valores de X :");
                    for (double valor : nuevosValoresX) {
                        double prediccion = RCuadrada.predecirValor(coeficientes, valor);
                        System.out.println("Nuevo valor de X: " + valor + "  Predicción de Y: " + prediccion);
                    }
                    double r2 = RCuadrada.calcularR2(x, y, coeficientes);
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


}
