import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgenteEmisor extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente Emisor iniciado");
        DataSet dataSet = new DataSet();//instancia del dataset
        String contenido = dataSet.getFormattedData();//obtener datos
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(contenido);
        msg.addReceiver(getAID("AgentePrincipal"));
        send(msg);
        System.out.println("Agente Emisor enviado datos : " + contenido);
    }
}
