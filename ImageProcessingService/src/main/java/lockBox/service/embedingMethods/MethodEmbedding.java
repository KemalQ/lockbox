package lockBox.service.embedingMethods;

import lockBox.service.impl.ImageProcessingImpl;
import lockBox.service.impl.KohJao;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Data
@Service
public class MethodEmbedding {
    public MethodEmbedding() {}

    public List<double[][]> embeddingMethodOne(List<double[][]> arrayOfBlocks, String text){
        KohJao kohJao = new KohJao();
        ImageProcessingImpl imageProcessing = new ImageProcessingImpl();

        String binaryMessage = toBinary(text);
        String binaryLength = String.format("%10s", Integer.toBinaryString(binaryMessage.length())).replace(' ', '0');

        // 1. флаг наличия сообщения
        for (int i = 0; i < 3; i++){
            arrayOfBlocks.set(i, kohJao.embedBitInBlock(arrayOfBlocks.get(i), true));
        }
        // 2. Внедряю длину сообщения (в битах)
        for (int i = 0; i < 10; i++) {
            int bit = Character.getNumericValue(binaryLength.charAt(i));
            if (bit==0){
                arrayOfBlocks.set(3 + i, kohJao.embedBitInBlock(arrayOfBlocks.get(3 + i), false));//starts from 3 block
            }
            else arrayOfBlocks.set(3+ i, kohJao.embedBitInBlock(arrayOfBlocks.get(3 + i), true));//starts from 3 block
        }

        // 3. Внедряею само сообщение
        for (int i = 0; i < binaryMessage.length(); i++) {
            int bit = Character.getNumericValue(binaryMessage.charAt(i));
            if (bit==0){
                arrayOfBlocks.set(3 + 10 + i, kohJao.embedBitInBlock(arrayOfBlocks.get(3 + 10 + i), false));
            }
            else arrayOfBlocks.set(3 + 10 + i, kohJao.embedBitInBlock(arrayOfBlocks.get(3 + 10 + i), true));
        }

        return arrayOfBlocks;


//        for (int repeat = 0; repeat < 3; repeat++) {
//            int baseIndex = repeat * (3 + 10 + binaryMessage.length());
//
//            // 1. Внедряем флаг "сообщение есть"
//            for (int i = 0; i < 3; i++) {
//                arrayOfBlocks.set(baseIndex+i, kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + i), true));
//                //kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + i), true); // вставляем 1
//            }
//
//            // 2. Внедряем длину сообщения (в битах)
//            for (int i = 0; i < 10; i++) {
//                int bit = Character.getNumericValue(binaryLength.charAt(i));
//                if (bit==0){
//                    arrayOfBlocks.set(baseIndex + 3 + i, kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + i), false));
//                    //kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + i), false);
//                }
//                else arrayOfBlocks.set(baseIndex + 3 + i, kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + i), true));
//                    //kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + i), true);
//            }
//
//            // 3. Внедряем само сообщение
//            for (int i = 0; i < binaryMessage.length(); i++) {
//                int bit = Character.getNumericValue(binaryMessage.charAt(i));
//                if (bit==0){
//                    arrayOfBlocks.set(baseIndex + 3 + 10 + i, kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + 10 + i), false));
//                    //kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + 10 + i), false);
//                }
//                else arrayOfBlocks.set(baseIndex + 3 + 10 + i, kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + 10 + i), true));
//                    //kohJao.embedBitInBlock(arrayOfBlocks.get(baseIndex + 3 + 10 + i), true);
//            }
//        }
//        return arrayOfBlocks;
    }

    public String extractingMethodOne(List<double[][]> arrayOfBlocks) {
        KohJao kohJao = new KohJao();

        //1. Чтение флага "сообщение есть"
        int trueCount = 0;
        for (int i = 0; i < 3; i++) {
            Boolean bit = kohJao.extractBitFromBlock(arrayOfBlocks.get(i));
            if (bit != null && bit) {
                trueCount++;
            }
        }

        log.info("MethodEmbedding - extractingMethodOne - trueCount: " + trueCount);//TODO delete after checking 01.06.2025
        if (trueCount >= 2){// флаг подтверждён
            //2. Чтение длины
            StringBuilder lengthBinary = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                Boolean bit = kohJao.extractBitFromBlock(arrayOfBlocks.get(3 + i));
                if (bit == null) return null;
                lengthBinary.append(bit ? '1' : '0');
            }
            int messageLength = Integer.parseInt(lengthBinary.toString(), 2);// получил длину внедренного сообщения в int
            log.info("MethodEmbedding - extractingMethodOne - messageLength: " + messageLength);//TODO delete after checking 01.06.2025

            // 3. Чтение сообщения
            StringBuilder binaryMessage = new StringBuilder();
            for (int i = 0; i < messageLength; i++) {
                Boolean bit = kohJao.extractBitFromBlock(arrayOfBlocks.get(3 + 10 + i));
                if (bit == null) return null;
                binaryMessage.append(bit ? '1' : '0');
            }
            log.info("MethodEmbedding - extractingMethodOne - fromBinaryToStringMessage: " + fromBinary(binaryMessage.toString()));//TODO delete after checking 01.06.2025
            // Расшифровка бинарного текста в строку
            return fromBinary(binaryMessage.toString());
        }
        else return null;


//        for (int repeat = 0; repeat < 3; repeat++) {
//            int baseIndex = repeat * (3 + 10 + 512); //пока max длина сообщения 512 бит, можно позже вычислять динамически
//
//            //1. Чтение флага "сообщение есть"
//            int trueCount = 0;
//            for (int i = 0; i < 3; i++) {
//                Boolean bit = kohJao.extractBitFromBlock(arrayOfBlocks.get(baseIndex + i));
//                if (bit != null && bit) {
//                    trueCount++;
//                }
//            }
//
//            if (trueCount >= 2) { // флаг подтверждён
//                //2. Чтение длины
//                StringBuilder lengthBinary = new StringBuilder();
//                for (int i = 0; i < 10; i++) {
//                    Boolean bit = kohJao.extractBitFromBlock(arrayOfBlocks.get(baseIndex + 3 + i));
//                    if (bit == null) return null;
//                    lengthBinary.append(bit ? '1' : '0');
//                }
//
//                int messageLength = Integer.parseInt(lengthBinary.toString(), 2);//получил длину внедренного сообщения в int
//
//                // 3. Чтение сообщения
//                StringBuilder binaryMessage = new StringBuilder();
//                for (int i = 0; i < messageLength; i++) {
//                    Boolean bit = kohJao.extractBitFromBlock(arrayOfBlocks.get(baseIndex + 3 + 10 + i));
//                    if (bit == null) return null;
//                    binaryMessage.append(bit ? '1' : '0');
//                }
//
//                // Расшифровка бинарного текста в строку
//                return fromBinary(binaryMessage.toString());
//            }
//        }
//        return null; // сообщение не найдено
    }


    private String toBinary(String message){//Преобразование строки в binary строку
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        return binary.toString();
    }
    private String fromBinary(String binary) {//Преобразование из binary в строку
        StringBuilder text = new StringBuilder();
        for (int i = 0; i + 8 <= binary.length(); i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            text.append((char) charCode);
        }
        return text.toString();
    }
}