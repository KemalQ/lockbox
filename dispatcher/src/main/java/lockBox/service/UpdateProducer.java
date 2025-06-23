package lockBox.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducer {//to transfer 'updates' to rabMq
    void produce(String rabbitQueue, Update update);
}
