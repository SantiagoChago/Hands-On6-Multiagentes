public class MenuRegresiones {
    private boolean usarRegresionSimple = false;
    private boolean usarRegresionMultiple = false;
    private boolean usarRegresionPolinomial = false;

    public void configurarRegresion(String tipo) {
        switch (tipo.toLowerCase()) {
            case "simple":
                usarRegresionSimple = true;
                usarRegresionMultiple = false;
                usarRegresionPolinomial = false;
                System.out.println("Tipo de regresión configurado: Simple");
                break;
            case "multiple":
                usarRegresionSimple = false;
                usarRegresionMultiple = true;
                usarRegresionPolinomial = false;
                System.out.println("Tipo de regresión configurado: Múltiple");
                break;
            case "polinomial":
                usarRegresionSimple = false;
                usarRegresionMultiple = false;
                usarRegresionPolinomial = true;
                System.out.println("Tipo de regresión configurado: Polinomial");
                break;
            default:
                System.out.println("Tipo de regresión no válido.");
        }
    }

    public boolean esRegresionSimple() {
        return usarRegresionSimple;
    }

    public boolean esRegresionMultiple() {
        return usarRegresionMultiple;
    }

    public boolean esRegresionPolinomial() {
        return usarRegresionPolinomial;
    }
}
