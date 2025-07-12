package lockBox.service.impl;

import lockBox.service.UpdateProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;
//DispatcherApplication
@Service
@Slf4j
public class UpdateProducerImpl implements UpdateProducer {
    private final RabbitTemplate rabbitTemplate;

    public UpdateProducerImpl(RabbitTemplate rabbitTemplate) {//
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String rabbitQueue, Update update) {
        if (update.getMessage().hasText()){//TODO plug, delete after
            log.debug(update.getMessage().getText());
        }
        if (update.getMessage().hasPhoto()){//TODO plug, delete after
            log.debug("photo message received");
        }
        if (update.getMessage().hasDocument()){//TODO plug, delete after
            log.debug("Document message: ", update.getMessage().getDocument().getFileName());
        }

        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }
}
