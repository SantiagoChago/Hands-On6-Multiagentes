public class DataSet {
    private double[] dataSetX = {1,2,3,4,5,6,7,8,9};
    protected double[] dataSetX2 = {529, 676, 900, 1156, 1849, 2304, 2704, 3249, 3364};
    private double[] dataSetY = {4,8,12,16,20,24,28,32,36};

    public double[] getDataSetX() {
        return dataSetX;
    }
    public double[] getDataSetY() {
        return dataSetY;
    }
    public double[] getDataSetX2() {
        return dataSetX2;
    }
    //obtener el formato de datos
    public String getFormattedData() {
        StringBuilder builder =new StringBuilder();
        for (int i=0;i<dataSetX.length;i++) {
            builder.append("(").append(dataSetX[i]).append(",").append(dataSetY[i]).append(")");
            if (i < dataSetX.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
    public double[] optimizarSLR() {
        return AlgoritmoGenetico.optimizarSLR(dataSetX, dataSetY);
    }
    public void mostrarResultadosSLR() {
        double[] coeficientes = optimizarSLR();
        System.out.println("Resultados del simple  con Algoritmo Genético:");
        System.out.println("Intercepto : "+coeficientes[0]);
        System.out.println("Pendiente : "+coeficientes[1]);
    }
}
