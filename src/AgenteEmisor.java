import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgenteEmisor extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente Emisor iniciado");

        // Instancia del dataset
        DataSet dataSet = new DataSet();

        // Obtener los resultados optimizados de SLR
        double[] coeficientes = dataSet.optimizarSLR();
        StringBuilder contenido = new StringBuilder();
        contenido.append("Intercepto: ").append(coeficientes[0])
                .append(" Pendiente: ").append(coeficientes[1]);

        // Crear el mensaje
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(contenido.toString());
        msg.addReceiver(getAID("AgentePrincipal"));

        // Enviar el mensaje
        send(msg);
        System.out.println("Agente Emisor enviado resultados optimizados: " + contenido.toString());
    }
}
