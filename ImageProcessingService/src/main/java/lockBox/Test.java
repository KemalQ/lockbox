package lockBox;

import lockBox.Service.impl.ImageProcessingImpl;
import lockBox.Service.impl.KohJao;
import lockBox.Service.impl.TextProcessingImpl;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.List;

import static lockBox.Utils.printMatrix.printMatrix;

public class Test {
    public static void main(String[] args) {
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
        double[][] input1 = {
                {120, 132, 145, 140, 138, 150, 155, 160},
                {125, 130, 142, 148, 152, 158, 162, 165},
                {130, 135, 140, 152, 158, 165, 170, 175},
                {140, 145, 150, 158, 165, 170, 178, 182},
                {150, 155, 160, 165, 172, 180, 185, 190},
                {160, 165, 170, 175, 180, 185, 190, 195},
                {165, 170, 175, 182, 188, 192, 198, 202},
                {170, 175, 180, 185, 190, 195, 205, 210}
        };

        double[][] input2 = {
                {200, 210, 215, 220, 215, 208, 205, 210},
                {205, 215, 220, 225, 218, 210, 208, 212},
                {210, 218, 225, 228, 220, 215, 210, 215},
                {215, 220, 228, 230, 225, 220, 215, 218},
                {212, 215, 222, 225, 220, 218, 212, 215},
                {208, 210, 215, 220, 218, 215, 210, 208},
                {205, 208, 212, 215, 212, 210, 205, 202},
                {200, 205, 208, 210, 208, 205, 200, 198}
        };

        double[][] input3 = {
                {50, 55, 60, 150, 155, 160, 165, 170},
                {52, 58, 65, 155, 160, 165, 170, 175},
                {55, 62, 70, 160, 165, 170, 175, 180},
                {60, 65, 75, 165, 170, 175, 180, 185},
                {150, 155, 160, 70, 75, 80, 85, 90},
                {155, 160, 165, 75, 80, 90, 95, 100},
                {160, 165, 170, 80, 85, 95, 105, 110},
                {165, 170, 175, 85, 90, 100, 115, 120}
        };

        File imageFile = new File("C:\\Users\\SHERFEDINOV KEMAL\\Desktop\\Colors\\canyon.jpg");

        ImageProcessingImpl imageProcessing = new ImageProcessingImpl();


        var mat = imageProcessing.photoToMat(imageFile.getAbsolutePath());// returns mat object

        var blueChannel = imageProcessing.getBlueChannel(mat);//returns blue channel

        var array = imageProcessing.matToDoubleArray(blueChannel);//returns double[][]

        var arrayOfBlocks = imageProcessing.splitIntoArrayOfBlocks(array);//List<double[][]>



//        System.out.println("DCT jTransform: ");
//        var resultDct = imageProcessing.dct(input3);
//        printMatrix(resultDct);
//
//        System.out.println("IDCT jTransform: ");
//        var resultIdct = imageProcessing.idct(resultDct);
//        printMatrix(resultIdct);
//
//        System.out.println("Обычная проверка работы кода внедрения. After embding: ");
//        KohJao kohJao = new KohJao();
//        double[][] resultKoh = kohJao.embedBitInBlock(input3, true);
//        printMatrix(resultKoh);
//        System.out.println(kohJao.extractBitFromBlock(resultKoh));


        //TODO for ASCII format
//        boolean[] bits = {
//                // 'H' (72 или 01001000 в двоичном)
//                false, true, false, false, true, false, false, false,
//                // 'i' (105 или 01101001 в двоичном)
//                false, true, true, false, true, false, false, true,
//                // '!' (33 или 00100001 в двоичном)
//                false, false, true, false, false, false, false, true
//        };
//        TextProcessingImpl textProcessing = new TextProcessingImpl();
//        String asciiString = textProcessing.bitsToAscii(bits);
//        System.out.println("Биты как ASCII: " + asciiString);

        // Пример: строка в виде битов из строки int
//        String bitString = "01001000 01101001 00100001"; // "Hi!" в битах
//        boolean[] bitsFromString = textProcessing.bitStringToBitArray(bitString);
//        String result = textProcessing.bitsToAscii(bitsFromString);
//        System.out.println("Строка битов как ASCII: " + textProcessing.binaryToString(bitString));


    }
}

/*
[  [239, 246, 255, 255, 239, 235, 242, 230],
  [255, 253, 255, 255, 251, 243, 250, 244],
  [255, 255, 254, 255, 253, 244, 252, 250],
  [251, 207, 198, 225, 235, 239, 250, 252],
  [192, 149, 135, 167, 199, 227, 246, 255],
  [177, 152, 129, 132, 160, 201, 233, 245],
  [183, 190, 158, 119, 122, 166, 210, 237],
  [167, 198, 167, 103, 91, 137, 195, 234] ]
 */