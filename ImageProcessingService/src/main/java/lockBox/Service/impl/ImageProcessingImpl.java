package lockBox.Service.impl;

import lockBox.Service.ImageProcessing;
import org.jtransforms.dct.DoubleDCT_2D;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import java.util.ArrayList;
import java.util.List;
/**
 * 1 getting blue channel from Mat
 * 2 Mat to double[][] array
 * 3 double[][] array to ArrayList<double[8][8]>
 *
 *
 */
public class ImageProcessingImpl implements ImageProcessing {
    private Mat image;

    @Override
    public Mat getBlueChannel(Mat image) {
        // Создаём список для каналов
        List<Mat> channels = new ArrayList<>();

        // Разделяем изображение на 3 канала (BGR)
        Core.split(image, channels);//TODO задебажить
        // Возвращаем синий канал (индекс 0 в BGR)
        //var m = matToIntArray(channels.get(0));//TODO experiment check
        return channels.get(0);
    }

    public double[][] matToIntArray(Mat blueChannel) {
        int rows = blueChannel.rows();
        int cols = blueChannel.cols();
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = blueChannel.get(i, j)[0];
            }
        }
        return result;
    }
    public static List<double[][]> splitIntoArrayOfBlocks(double[][] channel) {
        int height = channel.length;
        int width = channel[0].length;

        int blocksPerRow = width / 8;
        int blocksPerCol = height / 8;

        List<double[][]> blocks = new ArrayList<>();
        //TODO проверить разбил ли массив на блоки с шагом 8 блоков
        for (int blockRow = 0; blockRow < blocksPerCol; blockRow++) {
            for (int blockCol = 0; blockCol < blocksPerRow; blockCol++) {
                double[][] block = new double[8][8];
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        block[i][j] = channel[blockRow * 8 + i][blockCol * 8 + j];
                    }
                }
                blocks.add(block);
            }
        }
        return blocks;
    }



    @Override
    public double[][] dct(double[][] input){
        int rows = input.length;
        int cols = input[0].length;
        // JTransforms требует 2D-массив в виде одномерного массива
        double[][] data = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            System.arraycopy(input[i], 0, data[i], 0, cols);

        // Создаём DCT-объект
        DoubleDCT_2D dct2d = new DoubleDCT_2D(rows, cols);

        // Прямое DCT (in-place)
        dct2d.forward(data, true);
        return data;
    }
    @Override
    public double[][] idct(double[][] input){
        int rows = input.length;
        int cols = input[0].length;

        // JTransforms требует 2D-массив в виде одномерного массива
        double[][] data = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            System.arraycopy(input[i], 0, data[i], 0, cols);

        // Создаём DCT-объект
        DoubleDCT_2D dct2d = new DoubleDCT_2D(rows, cols);

        // Обратное DCT
        dct2d.inverse(data, true);
        return data;
    }
}
