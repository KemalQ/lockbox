package lockBox.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.username}")
    private String userName;
    @Value("${telegram.bot.token}")
    private String token;
    private UpdateController updateController;

    public TelegramBot(UpdateController updateController){
        this.updateController = updateController;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        String txt = message.getText();
        log.info("Получено сообщение: {}", txt);

        var response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        response.setText("Hello from bot");
        sendAnswerMessage(response);
    }
    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try{
                ReplyKeyboardMarkup KeyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                for(int i = 1; i <= 30; i++)
                {
                    row.add(String.valueOf(i));
                    if (i%5 == 0){
                        keyboard.add(row);
                        row = new KeyboardRow();
                    }
                }
                KeyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(KeyboardMarkup);

                execute(message);
            } catch (TelegramApiException e){
                log.error(String.valueOf(e));
            }
        }
    }
    @PostConstruct
    public void init() {
        //enable message sending from telegrambot to controller & controller to telegram bot
        updateController.registerBot(this);
    }
}
