package lockBox.Service;

import org.opencv.core.Mat;

public interface ImageProcessing {
    Mat getBlueChannel(Mat image);
    double[][] dct(double[][] input);
    double[][] idct(double[][] input);
}
