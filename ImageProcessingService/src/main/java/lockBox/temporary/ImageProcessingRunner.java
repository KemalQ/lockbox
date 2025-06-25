package lockBox.temporary;

import lockBox.service.embedingMethods.MethodEmbedding;
import lockBox.service.impl.ImageProcessingImpl;
import lockBox.service.impl.KohJao;
import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static lockBox.Utils.SecureRandomStringGenerator.generateRandomString;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

@Component
public class ImageProcessingRunner /*implements CommandLineRunner*/{
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

    /*@Override
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

        //TODO заглушка для вывода содержимого
        KohJao kohJao1 = new KohJao();//TODO delete after testing
        for (int i = 0; i<arrayOfBlocks.size(); i++){
//            arrayOfBlocks.set(i, kohJao1.embedBitInBlock(arrayOfBlocks.get(i), true));
            if (array.length==i){//TODO delete after testing extracted bits
                System.out.println("");
            }
            System.out.print(kohJao1.extractBitFromBlock(arrayOfBlocks.get(i))+"  ");
        }

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
//        KohJao kohJao = new KohJao();
//        for (int i = 0; i<arrayOfBlocksDecode.size(); i++){
//            if (arrayDecode.length==i){//TODO delete after testing extracted bits
//                System.out.println("");
//            }
//            System.out.print(kohJao.extractBitFromBlock(arrayOfBlocksDecode.get(i))+"  ");
//        }

        System.out.println(methodOne.extractingMethodOne(arrayOfBlocksDecode));
    }*/
}


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