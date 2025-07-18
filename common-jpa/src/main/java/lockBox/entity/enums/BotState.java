package lockBox.entity.enums;

public enum BotState {
    INITIAL_STATE,
    WAITING_FOR_ACTION,
    WAITING_FOR_IMAGE,
    WAITING_FOR_TEXT,
    WAITING_FOR_ALGORITHM,
    PROCESSING
}

/*
                                                            BASIC_STATE,
1. Получение изображения (.jpg/.png)                        WAITING_FOR_IMAGE,
2. Вопрос: "Зашифровать или Расшифровать?"                  WAITING_FOR_COMMAND,
3. Если "Зашифровать":
   4. Ожидается ввод ТЕКСТА                                 WAITING_FOR_TEXT,
   5. Показывается клавиатура 1–30 (выбор алгоритма)        WAITING_FOR_ALGORITHM
   6. После выбора – вызов Steganography Service (encrypt)
   7. Возврат картинки с внедрённым текстом


   WAITING_FOR_AVAIBILITY_DATE,
*/