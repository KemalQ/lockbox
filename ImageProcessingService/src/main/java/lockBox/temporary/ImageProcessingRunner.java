package lockBox.temporary;

import lockBox.Service.embedingMethods.MethodEmbedding;
import lockBox.Service.impl.ImageProcessingImpl;
import lockBox.Service.impl.KohJao;
import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static lockBox.Utils.SecureRandomStringGenerator.generateRandomString;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

@Component
public class ImageProcessingRunner implements CommandLineRunner {
    @Value("${image.file.path}")
    private String imageFilePath;

    @Value("${image.file.pathDecode}")
    private String fileToDecode;

    MethodEmbedding methodOne;
    ImageProcessingImpl imageProcessing;

    ImageProcessingRunner(ImageProcessingImpl imageProcessing, MethodEmbedding methodOne){
        this.imageProcessing = imageProcessing;
        this.methodOne = methodOne;
    }

    @Override
    public void run(String... args) throws Exception {

//        double[][] input1 = {
//                {120, 132, 145, 140, 138, 150, 155, 160},
//                {125, 130, 142, 148, 152, 158, 162, 165},
//                {130, 135, 140, 152, 158, 165, 170, 175},
//                {140, 145, 150, 158, 165, 170, 178, 182},
//                {150, 155, 160, 165, 172, 180, 185, 190},
//                {160, 165, 170, 175, 180, 185, 190, 195},
//                {165, 170, 175, 182, 188, 192, 198, 202},
//                {170, 175, 180, 185, 190, 195, 205, 210}
//        };

        // 1. Embedding text to photo
        File imageFile = new File(imageFilePath);
        if (!imageFile.exists()) {
            throw new IllegalArgumentException("File was not found: " + imageFile.getAbsolutePath());
        }

        Mat mat = imageProcessing.photoToMat(imageFile.getAbsolutePath());// returns mat object
        Mat blueChannel = imageProcessing.getBlueChannel(mat);//returns blue channel
        double[][] array = imageProcessing.matToDoubleArray(blueChannel);//returns double[][]
        List<double[][]> arrayOfBlocks = imageProcessing.splitIntoArrayOfBlocks(array);//transforms double[][] to List<double[][]>

        arrayOfBlocks = methodOne.embeddingMethodOne(arrayOfBlocks, "Checking ukrainian symbols, ну коли вже");

//        //TODO delete after testing
        KohJao kohJao1 = new KohJao();
        for (int i = 0; i<arrayOfBlocks.size(); i++){
//            arrayOfBlocks.set(i, kohJao1.embedBitInBlock(arrayOfBlocks.get(i), true));

            if (array.length==i){//TODO delete after testing extracted bits
                System.out.println("");
            }
            System.out.print(kohJao1.extractBitFromBlock(arrayOfBlocks.get(i))+"  ");
        }//TODO delete after testing

        double[][] imageArray = imageProcessing.mergeFromArrayOfBlocks(arrayOfBlocks, array.length, array[0].length);//TODO 14.05.2025 debug

        Mat modifiedBlue = imageProcessing.doubleArrayToMat2(imageArray);//TODO 14.05.2025 debug

        Mat finalImage = imageProcessing.replaceBlueChannel(mat, modifiedBlue);//TODO 14.05.2025 debug

        String path = System.getProperty("user.home") + "/Desktop/OutputFiles/"+ generateRandomString(16) + ".png";
        imwrite(path, finalImage);
        System.out.println("Saved: " + path);


        for (int i = 0; i < 10; i++){
            System.out.println("");
        }

        // 2. Extracting text from photo
        File imageFileDecode = new File(fileToDecode);
        if (!imageFileDecode.exists()) {
            throw new IllegalArgumentException("File was not found: " + imageFileDecode.getAbsolutePath());
        }
        Mat matDecode = imageProcessing.photoToMat(imageFileDecode.getAbsolutePath());// returns mat object
        Mat blueChannelDecode = imageProcessing.getBlueChannel(matDecode);//returns blue channel
        double[][] arrayDecode = imageProcessing.matToDoubleArray(blueChannelDecode);//returns double[][]
        List<double[][]> arrayOfBlocksDecode = imageProcessing.splitIntoArrayOfBlocks(arrayDecode);

        //TODO заглушка для вывода содержимого
        KohJao kohJao = new KohJao();
        for (int i = 0; i<arrayOfBlocksDecode.size(); i++){
            if (arrayDecode.length==i){//TODO delete after testing extracted bits
                System.out.println("");
            }
            System.out.print(kohJao.extractBitFromBlock(arrayOfBlocksDecode.get(i))+"  ");
        }

        System.out.println(methodOne.extractingMethodOne(arrayOfBlocksDecode));




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

                //TODO внедряем 0 в фото подряд во все блоки
//        KohJao kohJao = new KohJao();
//        for (int i = 0; i<arrayOfBlocks.size(); i++){
//            arrayOfBlocks.set(i, kohJao.embedBitInBlock(arrayOfBlocks.get(i), false));
//
//            if (array.length==i){//TODO delete after testing extracted bits
//                System.out.println("");
//            }
//            System.out.print(kohJao.extractBitFromBlock(arrayOfBlocks.get(i))+"  ");
//        }
 */