package lockBox.service.impl;

import lockBox.service.embedingMethods.MethodEmbedding;
import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static lockBox.Utils.SecureRandomStringGenerator.generateRandomString;

//@Service
public class EmbeddingOrchestratorService {
    private final MethodEmbedding methodOne;
    private final ImageProcessingImpl imageProcessing;


    public EmbeddingOrchestratorService(MethodEmbedding methodOne, ImageProcessingImpl imageProcessing) {
        this.methodOne = methodOne;
        this.imageProcessing = imageProcessing;
    }

//    // 1. Embedding text to photo
//    File imageFile = new File(imageFilePath);
//        if (!imageFile.exists()) {
//        throw new IllegalArgumentException("File was not found: " + imageFile.getAbsolutePath());
//    }
//
//    Mat mat = imageProcessing.photoToMat(imageFile.getAbsolutePath());// returns mat object
//    Mat blueChannel = imageProcessing.getBlueChannel(mat);//returns blue channel
//    double[][] array = imageProcessing.matToDoubleArray(blueChannel);//returns double[][]
//    List<double[][]> arrayOfBlocks = imageProcessing.splitIntoArrayOfBlocks(array);//transforms double[][] to List<double[][]>
//
//    arrayOfBlocks = methodOne.embeddingMethodOne(arrayOfBlocks, "Checking ukrainian symbols, ну коли вже");
//
//    //TODO заглушка для вывода содержимого
//    KohJao kohJao1 = new KohJao();//TODO delete after testing
//        for (int i = 0; i<arrayOfBlocks.size(); i++){
////            arrayOfBlocks.set(i, kohJao1.embedBitInBlock(arrayOfBlocks.get(i), true));
//        if (array.length==i){//TODO delete after testing extracted bits
//            System.out.println("");
//        }
//        System.out.print(kohJao1.extractBitFromBlock(arrayOfBlocks.get(i))+"  ");
//    }
//
//    double[][] imageArray = imageProcessing.mergeFromArrayOfBlocks(arrayOfBlocks, array.length, array[0].length);//TODO 14.05.2025 debug
//
//    Mat modifiedBlue = imageProcessing.doubleArrayToMat2(imageArray);//TODO 14.05.2025 debug
//
//    Mat finalImage = imageProcessing.replaceBlueChannel(mat, modifiedBlue);//TODO 14.05.2025 debug
//
//    String path = System.getProperty("user.home") + "/Desktop/OutputFiles/"+ generateRandomString(16) + ".png";
//    imwrite(path, finalImage);
//        System.out.println("Saved: " + path);
//
//
//        for (int i = 0; i < 10; i++){
//        System.out.println("");
//    }
}
