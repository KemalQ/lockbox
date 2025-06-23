package lockBox.Service.embeddingMethodsTest;

import lockBox.service.embedingMethods.MethodEmbedding;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MethodEmbeddingTest {

    @Test
    public void testEmbeddingAndExtracting() {
        MethodEmbedding method = new MethodEmbedding();
        String originalMessage = "hello";

        // 1. Генерируем пустые блоки
        int totalBlocks = 3 * (3 + 10 + originalMessage.length() * 8); // умножаем на 8 т.к. в битах
        List<double[][]> blocks = new ArrayList<>();
        for (int i = 0; i < totalBlocks; i++) {
            blocks.add(generateEmptyBlock()); // создаём пустой блок 8x8
        }

        // 2. Внедрение
        List<double[][]> embeddedBlocks = method.embeddingMethodOne(blocks, originalMessage);

        // 3. Извлечение
        String extractedMessage = method.extractingMethodOne(embeddedBlocks);

        // 4. Проверка
        assertEquals(originalMessage, extractedMessage);
    }

    private double[][] generateEmptyBlock() {
        double[][] block = new double[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                block[i][j] = Math.random(); // или 0.0, если нужны предсказуемые значения
        return block;
    }
}
