import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentePrincipal extends Agent {
    private boolean usarRegresionSimple=false;
    private boolean usarRegresionMultiple=false;
    private boolean usarRegresionPolinomial=false;

    @Override// usado para sobreescribrir
    protected void setup() {
        System.out.println("Iniciando AgentePrincipal");
        MenuRegresiones("simple");// cambiar regresion
        addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage mensaje=receive();
                if (mensaje!=null) {
                    System.out.println("Agente Principal  Recibido :" + mensaje.getContent());
                    if (usarRegresionSimple) {//depende que regresion usar
                        procesarRegresionSimple(mensaje);
                    } else if (usarRegresionMultiple) {
                        procesarRegresionMultiple(mensaje);
                    } else if (usarRegresionPolinomial) {
                        procesarRegresionPolinomial(mensaje);
                    } else {
                        System.out.println("Agente Principal no se ha seleccionado un tipo de regresiion");
                    }
                } else {
                    block();
                }
            }
        }
        );
    }

    private void procesarRegresionSimple(ACLMessage mensaje) {
        System.out.println("Datos adecuados para SLR");
        ACLMessage mensajeSLR=new ACLMessage(ACLMessage.REQUEST);
        mensajeSLR.addReceiver(new jade.core.AID("AgenteSLR",jade.core.AID.ISLOCALNAME));
        mensajeSLR.setContent(mensaje.getContent());
        send(mensajeSLR);
        ACLMessage respuesta=blockingReceive();
        if(respuesta!=null){
            System.out.println("Agente Principal  Recibido :" + respuesta.getContent());
        }
    }
    private void procesarRegresionMultiple(ACLMessage mensaje) {
        System.out.println("Datos adecuados para MLR");
        ACLMessage mensajeMLR= new ACLMessage(ACLMessage.REQUEST);
        mensajeMLR.addReceiver(new jade.core.AID("AgenteMLR",jade.core.AID.ISLOCALNAME));
        mensajeMLR.setContent(mensaje.getContent());
        send(mensajeMLR);
        ACLMessage respuesta=blockingReceive();
        if (respuesta!=null){
            System.out.println("Agente Principal Recibido  : "+respuesta.getContent());
        }
    }
    private void procesarRegresionPolinomial(ACLMessage mensaje) {
        System.out.println("Datos adecuados para Regresion Polinomial");
        ACLMessage mensajePolinomial = new ACLMessage(ACLMessage.REQUEST);
        mensajePolinomial.addReceiver(new jade.core.AID("AgentePolinomial", jade.core.AID.ISLOCALNAME));
        mensajePolinomial.setContent(mensaje.getContent());
        mensajePolinomial.addUserDefinedParameter("grado","3"); // Enviar el grado deseado
        send(mensajePolinomial);
        ACLMessage respuesta = blockingReceive();
        if (respuesta != null) {
            System.out.println("Agente Principal Recibido: " + respuesta.getContent());
        }
    }
    public void MenuRegresiones(String tipo) {
        switch (tipo) {
            case "simple":
                usarRegresionSimple = true;
                usarRegresionMultiple = false;
                usarRegresionPolinomial = false;
                System.out.println("Tipo de regresion configurado Simple");
                break;
            case "multiple":
                usarRegresionSimple = false;
                usarRegresionMultiple = true;
                usarRegresionPolinomial = false;
                System.out.println("Tipo de regresion configurado  Multiple");
                break;
            case "polinomial":
                usarRegresionSimple = false;
                usarRegresionMultiple = false;
                usarRegresionPolinomial = true;
                System.out.println("Tipo de regresion configurado Polinomial");
                break;
            default:
                System.out.println("Tipo de regresion no valido ");
                break;
        }
    }
}
