import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteSLR extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente SLR iniciado.");
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) {
                    String contenido = msg.getContent();
                    System.out.println("Agente SLR Recibiendo datos: " + contenido);
                    String[] pares = contenido.split(" ");
                    double[] x = new double[pares.length];
                    double[] y = new double[pares.length];
                    for (int i = 0; i < pares.length; i++) {
                        String[] valores = pares[i].replace("(", "").replace(")", "").split(",");
                        x[i] = Double.parseDouble(valores[0]);
                        y[i] = Double.parseDouble(valores[1]);
                    }
                    double[] coeficientes = MateDiscret.calcularSLR(x, y);
                    // Imprimir ecuación ajustada
                    String ecuacion = "y = " + coeficientes[0] + " + " + coeficientes[1] + " * X";
                    double[] nuevosValoresX = {60, 70, 80, 90, 100};  // Ejemplo de nuevos valores de X
                    for (double valorX : nuevosValoresX) {
                        double prediccion = MateDiscret.predecirValor(coeficientes, valorX);
                        System.out.println("Publicidad: " + valorX + "  Predicción de ventas: " + prediccion);
                    }
                    // Calcular R cuadrada
                    double[] yPredicha = new double[y.length];
                    for (int i = 0; i < y.length; i++) {
                        yPredicha[i] = MateDiscret.predecirValor(coeficientes, x[i]);
                    }
                    double rCuadrada = MateDiscret.calcularRCuadrada(y, yPredicha);
                    System.out.println("R cuadrada: " + rCuadrada);
                    // Enviar los coeficientes y R cuadrada al agente principal
                    ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
                    respuesta.setContent("Coeficientes SLR Intercepto = " + coeficientes[0] + " Pendiente = " + coeficientes[1] + " R2 = " + rCuadrada);
                    respuesta.addReceiver(msg.getSender());
                    send(respuesta);
                    System.out.println("Agente SLR Enviado Resultado:");
                } else {
                    block();
                }
            }
        });
    }
}
