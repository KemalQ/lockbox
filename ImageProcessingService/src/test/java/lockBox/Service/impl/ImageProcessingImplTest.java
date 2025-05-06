package lockBox.Service.impl;

import org.bytedeco.opencv.opencv_core.Mat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ImageProcessingImplTest {
    @Value("${image.file.path}")
    String filePath;
    @Test
    void testDCT(){
        ImageProcessingImpl imageProcessing = new ImageProcessingImpl();
        double[][] input = {
                {239, 246, 255, 255, 239, 235, 242, 230},
                {255, 253, 255, 255, 251, 243, 250, 244},
                {255, 255, 254, 255, 253, 244, 252, 250},
                {251, 207, 198, 225, 235, 239, 250, 252},
                {192, 149, 135, 167, 199, 227, 246, 255},
                {177, 152, 129, 132, 160, 201, 233, 245},
                {183, 190, 158, 119, 122, 166, 210, 237},
                {167, 198, 167, 103, 91, 137, 195, 234}
        };
        double[][] expected = {
                {663.25, -88.24, 118.09, 2.63, -5.50, 6.54, -5.62, 0.57},
                {271.55, 70.66, -123.93, 14.36, 26.26, 15.64, -3.61, 3.53},
                {-35.47, 66.97, 19.98, -63.08, -43.99, -12.12, -1.98, 0.54},
                {-56.33, -56.48, -2.07, 29.29, 7.64, 6.59, 1.70, -2.01},
                {-22.25, -10.44, 0.80, 2.21, 7.50, 5.77, 1.29, -2.40},
                {18.57, 24.59, -0.99, -3.57, -0.85, 2.19, -0.22, -1.17},
                {-3.98, -2.64, -4.73, -5.83, -5.59, -1.40, -1.23, 0.66},
                {-1.79, -3.63, -3.88, -5.94, -4.51, -3.72, -0.58, -0.64}
        };
        var actual = imageProcessing.dct(input);

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], actual[i][j], 0.1,
                        String.format("Mismatch at (%d,%d): expected %.2f but got %.2f",
                                i, j, expected[i][j], actual[i][j]));
            }
        }
    }

    @Test
    void testIDCT(){
        ImageProcessingImpl imageProcessing = new ImageProcessingImpl();
        double[][] input = {
                {663.25, -88.24, 118.09, 2.63, -5.50, 6.54, -5.62, 0.57},
                {271.55, 70.66, -123.93, 14.36, 26.26, 15.64, -3.61, 3.53},
                {-35.47, 66.97, 19.98, -63.08, -43.99, -12.12, -1.98, 0.54},
                {-56.33, -56.48, -2.07, 29.29, 7.64, 6.59, 1.70, -2.01},
                {-22.25, -10.44, 0.80, 2.21, 7.50, 5.77, 1.29, -2.40},
                {18.57, 24.59, -0.99, -3.57, -0.85, 2.19, -0.22, -1.17},
                {-3.98, -2.64, -4.73, -5.83, -5.59, -1.40, -1.23, 0.66},
                {-1.79, -3.63, -3.88, -5.94, -4.51, -3.72, -0.58, -0.64}
        };
        double[][] expected = {
                {239, 246, 255, 255, 239, 235, 242, 230},
                {255, 253, 255, 255, 251, 243, 250, 244},
                {255, 255, 254, 255, 253, 244, 252, 250},
                {251, 207, 198, 225, 235, 239, 250, 252},
                {192, 149, 135, 167, 199, 227, 246, 255},
                {177, 152, 129, 132, 160, 201, 233, 245},
                {183, 190, 158, 119, 122, 166, 210, 237},
                {167, 198, 167, 103, 91, 137, 195, 234}
        };
        double[][] actual = imageProcessing.idct(input);

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], actual[i][j], 0.1,
                        String.format("Mismatch at (%d,%d): expected %.2f but got %.2f",
                                i, j, expected[i][j], actual[i][j]));
            }
        }
    }

    @Test
    void testPhotoToMat()
    {
        ImageProcessingImpl imageProcessing = new ImageProcessingImpl();

        Mat result = imageProcessing.photoToMat(filePath);
        assertFalse(result.empty(), "The Mat should not be empty for a valid image path");
        assertEquals(3, result.channels(), "The Mat should have 3 color channels (BGR)");
    }
}
