public class DataSet {
    private double[] dataSetX = {1,2,3,4,5,6,7,8,9};
    protected double[] dataSetX2 = {529, 676, 900, 1156, 1849, 2304, 2704, 3249, 3364};
    private double[] dataSetY = {7,14,21,28,35,42,49,56,63};
    public double[] getDataSetX() {
        return dataSetX;
    }
    public double[] getDataSetY() {
        return dataSetY;
    }
    public double[] getDataSetX2() {
        return dataSetX2;
    }
    // Metodo para obtener los datos como cadenas en formato "(x,y)"
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
}
