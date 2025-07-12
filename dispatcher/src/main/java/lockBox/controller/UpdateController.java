package lockBox.controller;

import lockBox.service.impl.UpdateProducerImpl;
import lockBox.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static lockBox.RabbitQueue.*;

//Dispatcher
@Controller
@Slf4j
public class UpdateController {//distribute incoming messages from the telegram bot
    private TelegramBot telegramBot;//no Dependancy Injection, connected via @PostConstruct
    private final MessageUtils messageUtils;//Dependancy Injection
    private final UpdateProducerImpl updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducerImpl updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }
    public void processUpdate(Update update) {// 2. getting update from TelegramBot->checking in distributeMessagesByType
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
    private void distributeMessagesByType(Update update) {// 3. Checking update type
        var message = update.getMessage();
        if (message.hasText()){
            processTextMessage(update);
        } else if (message.hasDocument()) {
            processDocMessage(update);
        } else if (message.hasPhoto()) {
            processPhotoMessage(update);
        }else{
            setUnsupportedMessageTypeView(update);
        }
    }

    private void processPhotoMessage(Update update) {// 4.
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
        var sendMessage = messageUtils.generateSendMessageWithText(update, "Image received! Loading...");//TODO experıment delete after
        setFileReceivedView(update);//to prevent users nervous and attempt to send the same photo.
    }
    private void processDocMessage(Update update) {// 4.
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
        setFileReceivedView(update);//to prevent users nervous and attempt to send the same document.
    }
    private void processTextMessage(Update update) {// 4.
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }
    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "Unsupported message type!");
        setView(sendMessage);
    }
    private void setFileReceivedView(Update update) {// 5. To notify user that update received
        var sendMessage = messageUtils.generateSendMessageWithText(update,// constructing answer message in MessageUtils
                "File received! Loadind...");
        setView(sendMessage);// 6. Sending answer message in SendMessage format->(chatId, text)
    }


    public void setView(SendMessage sendMessage) { // 7. Sending message to Telegrambot.sendAnswerMessage()
        telegramBot.sendAnswerMessage(sendMessage);
    }
}
