package lockBox.Service;

import org.bytedeco.opencv.opencv_core.Mat;

import java.util.List;

public interface ImageProcessing {
    Mat photoToMat(String filePath);
    Mat getBlueChannel(Mat image);

    double[][] matToDoubleArray(Mat blueChannel);
    List<double[][]> splitIntoArrayOfBlocks(double[][] channel);
    public double[][] mergeFromArrayOfBlocks(List<double[][]> blocks, int imageHeight, int imageWidth);

    double[][] dct(double[][] input);
    double[][] idct(double[][] input);
}
