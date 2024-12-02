import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteSLR extends Agent {
    private DataSet dataSet = new DataSet();

    @Override
    protected void setup() {
        System.out.println("Agente SLR iniciado " );
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) {
                    String contenido = msg.getContent();
                    System.out.println("Agente SLR Recibiendo datos : " + contenido);
                    double[] x = dataSet.getDataSetX();
                    double[] y = dataSet.getDataSetY();
                    double[] coeficientes = RegresionSimple.calcularSLR(x, y);
                    double[] predicciones = new double[x.length];
                    for (int i = 0; i < x.length; i++) {
                        predicciones[i] = MateDiscret.predecirValor(coeficientes, x[i]);
                    }

                    double r2 = RCuadrada.calcularRCuadrada(y, predicciones);
                    double[] nuevosValoresX = {60, 70, 80, 90, 100};
                    StringBuilder prediccionesNuevas = new StringBuilder("Predicciones : \n");
                    for (double valor : nuevosValoresX) {
                        double prediccion = MateDiscret.predecirValor(coeficientes, valor);
                        prediccionesNuevas.append("X : ").append(valor).append(", Y : ").append(prediccion).append("\n");
                    }
                    ACLMessage respuesta = msg.createReply();
                    respuesta.setPerformative(ACLMessage.INFORM);
                    respuesta.setContent("Coeficientes SLR :\n" +
                            "Intercepto : " + coeficientes[0] + "\n" +
                            "Pendiente : " + coeficientes[1] + "\n" +
                            "R2 : " + r2 + "\n" +
                            prediccionesNuevas);
                    send(respuesta);
                    System.out.println("Agente SLR Enviado Resultado ");
                } else {
                    block();
                }
            }
        });
    }
}
