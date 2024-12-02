import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentePrincipal extends Agent {
    private MenuRegresiones menuRegresiones;

    @Override
    protected void setup() {
        System.out.println("Iniciando AgentePrincipal");
        menuRegresiones = new MenuRegresiones();
        menuRegresiones.configurarRegresion("polinomial"); // Configura la regresión polinomial
        addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage mensaje = receive();
                if (mensaje != null) {
                    System.out.println("Agente Principal Recibido: " + mensaje.getContent());
                    if (menuRegresiones.esRegresionSimple()) {
                        procesarRegresionSimple(mensaje);
                    } else if (menuRegresiones.esRegresionMultiple()) {
                        procesarRegresionMultiple(mensaje);
                    } else if (menuRegresiones.esRegresionPolinomial()) {
                        procesarRegresionPolinomial(mensaje);
                    } else {
                        System.out.println("No se ha seleccionado un tipo de regresión.");
                    }
                } else {
                    block();
                }
            }
        });
    }
    private void procesarRegresionSimple(ACLMessage mensaje) {
        System.out.println("Datos adecuados para SLR (usando algoritmo genético)");
        ACLMessage mensajeSLR = new ACLMessage(ACLMessage.REQUEST);
        mensajeSLR.addReceiver(new jade.core.AID("AgenteSLR", jade.core.AID.ISLOCALNAME));
        mensajeSLR.setContent(mensaje.getContent());
        send(mensajeSLR);

        ACLMessage respuesta = blockingReceive(2000);
        if (respuesta != null) {
            System.out.println("Agente Principal Recibido: " + respuesta.getContent());
        } else {
            System.out.println("Error: No se recibió respuesta del Agente SLR.");
        }
    }
    private void procesarRegresionMultiple(ACLMessage mensaje) {
        System.out.println("Datos adecuados para MLR");
        ACLMessage mensajeMLR = new ACLMessage(ACLMessage.REQUEST);
        mensajeMLR.addReceiver(new jade.core.AID("AgenteMLR", jade.core.AID.ISLOCALNAME));
        mensajeMLR.setContent(mensaje.getContent());
        send(mensajeMLR);

        ACLMessage respuesta = blockingReceive(2000);
        if (respuesta != null) {
            System.out.println("Agente Principal Recibido: " + respuesta.getContent());
        } else {
            System.out.println("Error: No se recibió respuesta del Agente MLR.");
        }
    }
    private void procesarRegresionPolinomial(ACLMessage mensaje) {
        System.out.println("Datos adecuados para Regresión Polinomial");
        ACLMessage mensajePolinomial = new ACLMessage(ACLMessage.REQUEST);
        mensajePolinomial.addReceiver(new jade.core.AID("AgentePolinomial", jade.core.AID.ISLOCALNAME));
        mensajePolinomial.setContent(mensaje.getContent());
        mensajePolinomial.addUserDefinedParameter("grado", "3"); // Parámetro grado
        send(mensajePolinomial);

        ACLMessage respuesta = blockingReceive(2000);
        if (respuesta != null) {
            System.out.println("Agente Principal Recibido: " + respuesta.getContent());
        } else {
            System.out.println("Error: No se recibió respuesta del Agente Polinomial.");
        }
    }
}
