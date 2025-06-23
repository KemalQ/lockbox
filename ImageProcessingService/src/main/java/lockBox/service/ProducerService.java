package lockBox.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
// Sending text&Document to the RabbitMQ queue:
public interface ProducerService {// ImageProcessingService-> RabbitMQ queue -> dispatcher
    void produceTextAnswer(SendMessage sendMessage);// producing text message with user chatId
    void produceDocAnswer(SendMessage sendMessage);// producing photo-document message
}
