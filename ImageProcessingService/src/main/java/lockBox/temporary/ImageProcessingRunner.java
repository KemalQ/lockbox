package lockBox.temporary;

import lockBox.Service.impl.ImageProcessingImpl;
import lockBox.Service.impl.KohJao;
import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static lockBox.Utils.SecureRandomStringGenerator.generateRandomString;
import static lockBox.Utils.printMatrix.printMatrix;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

@Component
public class ImageProcessingRunner implements CommandLineRunner {
    @Value("${image.file.path}")
    private String imageFilePath;

    ImageProcessingImpl imageProcessing;

    ImageProcessingRunner(ImageProcessingImpl imageProcessing){
        this.imageProcessing = imageProcessing;
    }

    @Override
    public void run(String... args) throws Exception {

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
        File imageFile = new File(imageFilePath);
        if (!imageFile.exists()) {
            throw new IllegalArgumentException("File was not found: " + imageFile.getAbsolutePath());
        }

        Mat mat = imageProcessing.photoToMat(imageFile.getAbsolutePath());// returns mat object
        Mat blueChannel = imageProcessing.getBlueChannel(mat);//returns blue channel
        double[][] array = imageProcessing.matToDoubleArray(blueChannel);//returns double[][]
        List<double[][]> arrayOfBlocks = imageProcessing.splitIntoArrayOfBlocks(array);//transforms double[][] to List<double[][]>

        //TODO внедряем 0 в фото подряд во все блоки
        KohJao kohJao = new KohJao();
        for (int i = 0; i<arrayOfBlocks.size(); i++){
            arrayOfBlocks.set(i, kohJao.embedBitInBlock(arrayOfBlocks.get(i), false));

            if (array.length==i){//TODO delete after testing extracted bits
                System.out.println("");
            }
            System.out.print(kohJao.extractBitFromBlock(arrayOfBlocks.get(i))+"  ");
        }

        double[][] imageArray = imageProcessing.mergeFromArrayOfBlocks(arrayOfBlocks, array.length, array[0].length);//TODO 14.05.2025 debug

        Mat modifiedBlue = imageProcessing.doubleArrayToMat2(imageArray);//TODO 14.05.2025 debug

        Mat finalImage = imageProcessing.replaceBlueChannel(mat, modifiedBlue);//TODO 14.05.2025 debug

        String path = System.getProperty("user.home") + "/Desktop/OutputFiles/"+ generateRandomString(16) + ".jpeg";
        imwrite(path, finalImage);
        System.out.println("Saved: " + path);


//        System.out.println("DCT jTransform: ");
//        var resultDct = imageProcessing.dct(input);
//        printMatrix(resultDct);
//
//        System.out.println("IDCT jTransform: ");
//        var resultIdct = imageProcessing.idct(resultDct);
//        printMatrix(resultIdct);
//
//        System.out.println("Обычная проверка работы кода внедрения. After embding: ");//TODO когда сравниваю не использовать матрицу из верхнего кода, так как она уже изменена
//        KohJao kohJao = new KohJao();
//        double[][] resultKoh = kohJao.embedBitInBlock(input, false);
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

/*
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
 */