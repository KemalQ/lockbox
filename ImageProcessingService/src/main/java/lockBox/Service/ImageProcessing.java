package lockBox.Service;

import org.opencv.core.Mat;

import java.util.List;

public interface ImageProcessing {
    Mat getBlueChannel(Mat image);

    double[][] matToDoubleArray(Mat blueChannel);
    List<double[][]> splitIntoArrayOfBlocks(double[][] channel);
    public double[][] mergeFromArrayOfBlocks(List<double[][]> blocks, int imageHeight, int imageWidth);

    double[][] dct(double[][] input);
    double[][] idct(double[][] input);
}
