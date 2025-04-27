package lockBox.Service.impl;

import lockBox.Service.ImageProcessing;
import org.jtransforms.dct.DoubleDCT_2D;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import java.util.ArrayList;
import java.util.List;


public class ImageProcessingImpl implements ImageProcessing {
    private Mat image;

    @Override
    public Mat getBlueChannel(Mat image) {
        // Создаём список для каналов
        List<Mat> channels = new ArrayList<>();

        // Разделяем изображение на 3 канала (BGR)
        Core.split(image, channels);//TODO задебажить
        // Возвращаем синий канал (индекс 0 в BGR)
        return channels.get(0);
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
