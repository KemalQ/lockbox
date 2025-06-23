package lockBox.service;

import org.springframework.amqp.core.AmqpTemplate;

import static lockBox.module.RabbitQueue.*;

public interface ProduserRabbitService {
    public void sendTextMessage(String message);

    public void sendPhotoMessage(String message);

    public void sendDocMessage(String message);

    public void sendAnswerMessage(String message);
}
