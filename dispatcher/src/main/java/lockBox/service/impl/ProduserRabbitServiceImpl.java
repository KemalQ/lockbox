package lockBox.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import static lockBox.RabbitQueue.*;
//DispatcherApplication
@Service
public class ProduserRabbitServiceImpl {
    private AmqpTemplate amqpTemplate;

    public ProduserRabbitServiceImpl(AmqpTemplate amqpTemplate){
        this.amqpTemplate = amqpTemplate;
    }

    public void sendTextMessage(String message){
        amqpTemplate.convertAndSend(TEXT_MESSAGE_UPDATE, message);
    }
    public void sendPhotoMessage(String message){
        amqpTemplate.convertAndSend(PHOTO_MESSAGE_UPDATE, message);
    }
    public void sendDocMessage(String message){
        amqpTemplate.convertAndSend(DOC_MESSAGE_UPDATE, message);
    }
    public void sendAnswerMessage(String message){
        amqpTemplate.convertAndSend(ANSWER_MESSAGE, message);
    }
}
