import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteMLR extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente MLR iniciado: " + getLocalName());
        addBehaviour(new ProcesarDatasetBehaviour()); // comportamiento inicial
    }
    private class ProcesarDatasetBehaviour extends OneShotBehaviour {
        @Override
        public void action() {
            ACLMessage mensaje = blockingReceive(); // espera el mensaje
            if (mensaje != null) {
                System.out.println("Mensaje recibido para MLR: " + mensaje.getContent());
                String contenido = mensaje.getContent(); // Parsear el contenido del mensaje al formato esperado
                double[][] x;
                double[] y;
                try {
                    Object[] datos = parsearDataset(contenido);//parsear datos
                    x = (double[][]) datos[0];
                    y = (double[]) datos[1];
                    // Realizar la regresión múltiple
                    double[] coeficientes = RegresionMultiple.fit(x, y);
                    String ecuacion = "y = ";
                    for (int i = 0; i < coeficientes.length; i++) {
                        System.out.printf("b%d: %.4f%n", i, coeficientes[i]);
                        if (i == 0) {
                            ecuacion += coeficientes[i];
                        } else {
                            ecuacion +=" + " + coeficientes[i] + " * X" + i;
                        }
                    }
                    System.out.println("Ecuación ajustada: " + ecuacion);
                    double[] nuevosValoresX = {60, 70, 80, 90, 100}; // Nuevos valores
                    double[] predicciones = new double[nuevosValoresX.length];
                    for (int i = 0; i < nuevosValoresX.length; i++) {
                        predicciones[i] = 0;
                        for (int j = 0; j < coeficientes.length; j++) {
                            predicciones[i] += coeficientes[j] * (j == 0 ? 1 :nuevosValoresX[i]); // j == 0 para el término constante
                        }
                    }
                    System.out.println("Predicciones con nuevos valores de X:");
                    for (int i = 0; i < nuevosValoresX.length; i++) {
                        System.out.println("X = " + String.format("%.2f", nuevosValoresX[i]) + " , Predicción Y = " + String.format("%.2f", predicciones[i]));
                    }
                    double[] yPredicha = new double[y.length];//Calcular r2
                    for (int i = 0; i < y.length; i++) {
                        yPredicha[i] = 0;
                        for (int j = 0; j < coeficientes.length; j++) {
                            yPredicha[i] += coeficientes[j] * (j == 0 ? 1 : x[i][j - 1]);  // j == 0 para el término constante
                        }
                    }
                    double rCuadrada = MateDiscret.calcularRCuadrada(y, yPredicha);
                    System.out.printf("R2 Coeficiente de determinación : %.4f%n", rCuadrada);
                    // Enviar los coeficientes y predicciones al agente principal
                    StringBuilder respuesta = new StringBuilder();
                    respuesta.append("Coeficientes: ");
                    for (int i = 0; i < coeficientes.length; i++) {
                        respuesta.append(String.format("b%d=%.4f ", i, coeficientes[i]));
                    }
                    for (int i = 0; i < predicciones.length; i++) {
                    }
                    // Responder con los coeficientes y predicciones
                    ACLMessage respuestaMensaje = mensaje.createReply();
                    respuestaMensaje.setPerformative(ACLMessage.INFORM);
                    respuestaMensaje.setContent(respuesta.toString().trim());
                    send(respuestaMensaje);
                    System.out.println("Coeficientes y predicciones enviados: " + respuesta.toString());
                } catch (Exception e) {
                    System.err.println("Error usando el dataset: " + e.getMessage());
                }
            }
        }
        // Parsear el dataset con el mensaje recibido
        private Object[] parsearDataset(String contenido) {
            String[] puntos = contenido.trim().split("\\s+");
            int n = puntos.length;
            int m = puntos[0].split(",").length - 1;
            double[][] x = new double[n][m];
            double[] y = new double[n];
            for (int i = 0; i < n; i++) {
                String[] valores = puntos[i].replace("(", "").replace(")", "").split(",");
                for (int j = 0; j < m; j++) {
                    x[i][j] = Double.parseDouble(valores[j]);
                }
                y[i] = Double.parseDouble(valores[m]);
            }
            return new Object[]{x, y};
        }
    }

}
