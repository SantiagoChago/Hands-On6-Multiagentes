public class EliminacionGaussiana {

    public static float[] resolver(float[][] matriz, float[] vector) {
        int n = matriz.length;
        //Eliminacion
        for (int i = 0; i < n; i++) {
            // Pivot principal
            for (int k = i + 1; k < n; k++) {
                float factor = matriz[k][i] / matriz[i][i];
                for (int j = i; j < n; j++) {
                    matriz[k][j] -= factor * matriz[i][j];
                }
                vector[k] -= factor * vector[i];
            }
        }
        //sustitucion
        float[] solucion = new float[n];
        for (int i = n - 1; i >= 0; i--) {
            solucion[i] = vector[i];
            for (int j = i + 1; j < n; j++) {
                solucion[i] -= matriz[i][j] * solucion[j];
            }
            solucion[i] /= matriz[i][i];
        }

        return solucion;
    }
}
