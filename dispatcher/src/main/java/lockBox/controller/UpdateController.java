package lockBox.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@Slf4j
public class UpdateController {
    private TelegramBot telegramBot;

    public void registerBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }
    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update.hasMessage()){
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported message type is:  {}" , update);
        }
    }
    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();
        if (message.hasText()){
            //processTextMessage(update);
        } else if (message.hasDocument()) {
            //processDocMessage(update);
        } else if (message.hasPhoto()) {
            //processPhotoMessage(update);
        }else{
            //setUnsupportedMessageTypeView(update);
        }
    }
}
