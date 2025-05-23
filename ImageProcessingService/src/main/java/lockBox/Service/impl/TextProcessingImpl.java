package lockBox.Service.impl;

import lockBox.Service.TextProcessing;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TextProcessingImpl implements TextProcessing {
    public String bitsToAscii(boolean[] bits) {//TODO переводит массив битов true или false в String ASCI, не использовать
        if (bits.length % 8 != 0) {
            throw new IllegalArgumentException("The length of the bit array must be a multiple of 8");
        }
        StringBuilder stringBuilder = new StringBuilder();

        // Обрабатываем по 8 бит за раз (1 байт = 1 символ ASCII)
        for (int i = 0; i < bits.length; i += 8) {
            int value = 0;

            // Преобразуем 8 бит в целое число
            for (int j = 0; j < 8; j++) {
                if (bits[i + j]) {
                    // Если бит равен 1, добавляем соответствующее значение
                    // j-й бит соответствует 2^(7-j)
                    value += (1 << (7 - j));
                }
            }

            // Преобразуем целое число в символ и добавляем к результату
            stringBuilder.append((char) value);
        }

        return stringBuilder.toString();
    }

    public boolean[] bitStringToBitArray(String bitString) {//переводит String битов 1 или 0 в массив bool
        // Удаляем все пробелы из строки
        bitString = bitString.replaceAll("\\s+", "");

        boolean[] result = new boolean[bitString.length()];

        for (int i = 0; i < bitString.length(); i++) {
            if (bitString.charAt(i) == '1') {
                result[i] = true;
            } else if (bitString.charAt(i) == '0') {
                result[i] = false;
            } else {
                throw new IllegalArgumentException("String must contain only '0' or '1'");
            }
        }
        return result;
    }

    @Override
    public String binaryToString(String bitString) {//
        return Arrays.stream(bitString.split(" "))
                .map(binary -> Integer.parseInt(binary, 2))
                .map(Character::toString)
                .collect(Collectors.joining());
    }

//    public boolean[] decimalLengthToBynary(boolean[] decimalLength){
//        boolean[] bynaryLength;
//        for (int i = 0; i < decimalLength.length; i++){
//
//        }
//        return bynaryLength;
//    }
//    public char[] text(){
//
//    }
}
