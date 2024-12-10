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
                    double[] coeficientesSLR = RegresionSimple.calcularSLR(x, y);
                    double[] prediccionesSLR = new double[x.length];
                    for (int i = 0; i < x.length; i++) {
                        prediccionesSLR[i] = MateDiscret.predecirValor(coeficientesSLR, x[i]);
                    }
                    double r2SLR = RCuadrada.calcularRCuadrada(y, prediccionesSLR);
                    double[] coeficientesAG = AlgoritmoGenetico.optimizarSLR(x, y);
                    double[] prediccionesAG = new double[x.length];
                    for (int i = 0; i < x.length; i++) {
                        prediccionesAG[i] = MateDiscret.predecirValor(coeficientesAG, x[i]);
                    }
                    double r2AG = RCuadrada.calcularRCuadrada(y, prediccionesAG);
                    System.out.println("SLR Clásico:");
                    System.out.println("Intercepto: " + String.format("%.6f", coeficientesSLR[0]) + ", Pendiente: " + String.format("%.6f", coeficientesSLR[1]) + ", R2: " + String.format("%.6f", r2SLR));
                    System.out.println("SLR con AG:");
                    System.out.println("Intercepto: " + String.format("%.6f", coeficientesAG[0]) + ", Pendiente: " + String.format("%.6f", coeficientesAG[1]) + ", R2: " + String.format("%.6f", r2AG));
                    ACLMessage respuesta = msg.createReply();
                    respuesta.setPerformative(ACLMessage.INFORM);
                    respuesta.setContent("Resultados:\n"+"Intercepto: " + String.format("%.6f", coeficientesSLR[0])+", Pendiente: " + String.format("%.6f", coeficientesSLR[1]) + ", R2: " + String.format("%.6f", r2SLR) + "\n" + "SLR con AG:\n" + "Intercepto: " + String.format("%.6f", coeficientesAG[0]) + "," +
                            " Pendiente: " + String.format("%.6f", coeficientesAG[1]) + ", R2: " + String.format("%.6f", r2AG));
                    send(respuesta);
                    System.out.println("Agente SLR Enviado Resultado ");
                } else {
                    block();
                }
            }
        });
    }
}
