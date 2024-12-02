public class DataSet {
    private double[] dataSetX = {23, 26, 30, 34, 43, 48, 52, 57, 58};
    protected double[] dataSetX2 = {529, 676, 900, 1156, 1849, 2304, 2704, 3249, 3364};
    private double[] dataSetY = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};
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
}
