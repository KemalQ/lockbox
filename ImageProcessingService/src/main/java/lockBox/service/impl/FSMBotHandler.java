package lockBox.service.impl;

// FSMBotHandler.java

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FSMBotHandler {
//
//    private final AppUserDAO appUserDAO;
//    private final ProducerService producerService;
//    private final ImageService imageService;
//    private final StegoService stegoService;
//
//    public void handleText(Update update) {
//        Long chatId = update.getMessage().getChatId();
//        AppUser user = findUser(update);
//        String text = update.getMessage().getText();
//
//        switch (user.getBotState()) {
//            case BASIC_STATE -> sendIntro(chatId);
//
//            case WAITING_FOR_ACTION -> {
//                if ("Зашифровать".equalsIgnoreCase(text)) {
//                    user.setOperation(OperationType.ENCODE);
//                    user.setBotState(BotState.WAITING_FOR_TEXT);
//                    appUserDAO.save(user);
//                    sendAnswer("Введите текст для шифрования:", chatId);
//                } else if ("Расшифровать".equalsIgnoreCase(text)) {
//                    user.setOperation(OperationType.DECODE);
//                    user.setBotState(BotState.WAITING_FOR_ALGORITHM);
//                    appUserDAO.save(user);
//                    sendAnswer("Выберите номер алгоритма (1–30):", chatId);
//                } else {
//                    sendAnswer("Выберите: Зашифровать или Расшифровать", chatId);
//                }
//            }
//
//            case WAITING_FOR_TEXT -> {
//                user.setTempText(text);
//                user.setBotState(BotState.WAITING_FOR_ALGORITHM);
//                appUserDAO.save(user);
//                sendAnswer("Выберите номер алгоритма (1–30):", chatId);
//            }
//
//            case WAITING_FOR_ALGORITHM -> {
//                if (!text.matches("\\d{1,2}")) {
//                    sendAnswer("Введите число от 1 до 30", chatId);
//                    return;
//                }
//                int algNum = Integer.parseInt(text);
//                if (algNum < 1 || algNum > 30) {
//                    sendAnswer("Номер алгоритма должен быть от 1 до 30", chatId);
//                    return;
//                }
//                user.setBotState(BotState.PROCESSING);
//                appUserDAO.save(user);
//
//                if (user.getOperation() == OperationType.ENCODE) {
//                    stegoService.encode(user, algNum);
//                    sendAnswer("Изображение зашифровано и сохранено", chatId);
//                } else {
//                    String extracted = stegoService.decode(user, algNum);
//                    sendAnswer("Результат расшифровки: " + extracted, chatId);
//                }
//                user.setBotState(BotState.BASIC_STATE);
//                appUserDAO.save(user);
//            }
//
//            default -> sendAnswer("Введите /cancel для сброса.", chatId);
//        }
//    }
//
//    public void handlePhoto(Update update) {
//        Long chatId = update.getMessage().getChatId();
//        AppUser user = findUser(update);
//
//        if (user.getBotState() != BotState.WAITING_FOR_IMAGE) {
//            sendAnswer("Я не ожидаю изображение. Введите /cancel", chatId);
//            return;
//        }
//
//        String path = imageService.save(update);
//        user.setTempImagePath(path);
//        user.setBotState(BotState.WAITING_FOR_ACTION);
//        appUserDAO.save(user);
//
//        sendAnswer("Изображение получено. Что вы хотите сделать? Зашифровать или Расшифровать?", chatId);
//    }
//
//    private AppUser findUser(Update update) {
//        Long telegramId = update.getMessage().getFrom().getId();
//        return appUserDAO.findAppUserByTelegramUserId(telegramId);
//    }
//
//    private void sendIntro(Long chatId) {
//        sendAnswer("Добро пожаловать. Для начала отправьте изображение.", chatId);
//    }
//
//    private void sendAnswer(String msg, Long chatId) {
//        SendMessage sm = new SendMessage(chatId.toString(), msg);
//        producerService.produceTextAnswer(sm);
//    }
}

// Можно вызывать FSMBotHandler.handleText() и .handlePhoto() из любого consumer.
