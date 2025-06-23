package lockBox.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {//getting Updates from RabbitMQ: dispatcher -> RabbitMQ Queue -> ImageProcessingService
    void consumeTextMessageUpdates(Update update);
    void consumeDocMessageUpdates(Update update);
    void consumePhotoMessageUpdates(Update update);
}
