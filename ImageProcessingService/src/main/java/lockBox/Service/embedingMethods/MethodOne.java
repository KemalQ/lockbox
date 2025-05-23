package lockBox.Service.embedingMethods;

import lockBox.Service.TextProcessing;
import lockBox.Service.impl.ImageProcessingImpl;
import lockBox.Service.impl.KohJao;
import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

@Service
public class MethodOne {
    private KohJao kohJao;
    private final ImageProcessingImpl imageProcessing;

    public MethodOne(ImageProcessingImpl imageProcessing, KohJao kohJao) {
        this.imageProcessing = imageProcessing;
        this.kohJao = kohJao;
    }

    public void process(Update update, String text) {
        List<PhotoSize> photos = update.getMessage().getPhoto();
        PhotoSize photo = photos.get(photos.size() - 1); // самая большая версия
        String fileId = photo.getFileId();

        String binaryMessage = toBinary(text);
        String binaryLength = String.format("%10s", Integer.toBinaryString(binaryMessage.length())).replace(' ', '0');

        // получить путь к файлу через TelegramBot API (через bot.execute(new GetFile(fileId)))
        // скачать файл в imageFile

        File imageFile = new File("путь_к_скачанному_файлу.jpg");

        Mat mat = imageProcessing.photoToMat(imageFile.getAbsolutePath());
        Mat blueChannel = imageProcessing.getBlueChannel(mat);
        double[][] array = imageProcessing.matToDoubleArray(blueChannel);
        List<double[][]> arrayOfBlocks = imageProcessing.splitIntoArrayOfBlocks(array);


        for (int repeat = 0; repeat < 3; repeat++) {
            int baseIndex = repeat * (3 + 10 + binaryMessage.length());

            // 1. Внедряем флаг "сообщение есть"
            for (int i = 0; i < 3; i++) {
                kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + i), true); // вставляем 1
            }

            // 2. Внедряем длину сообщения (в битах)
            for (int i = 0; i < 10; i++) {
                int bit = Character.getNumericValue(binaryLength.charAt(i));
                if (bit==0){
                    kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + i), false);
                }
                else kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + i), true);
            }

            // 3. Внедряем само сообщение
            for (int i = 0; i < binaryMessage.length(); i++) {
                int bit = Character.getNumericValue(binaryMessage.charAt(i));
                if (bit==0){
                    kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + 10 + i), false);
                }
                else kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + 10 + i), true);
            }
        }
    }
    private String toBinary (String message){
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        return binary.toString();
    }
}