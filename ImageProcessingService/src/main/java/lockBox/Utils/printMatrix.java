package lockBox.Utils;

public class printMatrix {
    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%10.2f", matrix[i][j]);
            }
            System.out.println();
        }
    }
}