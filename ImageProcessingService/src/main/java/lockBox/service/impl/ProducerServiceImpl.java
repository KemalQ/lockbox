package lockBox.service.impl;

import lockBox.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static lockBox.RabbitQueue.ANSWER_MESSAGE;

//ImageProcessingService
@Slf4j
@Service
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceTextAnswer(SendMessage sendMessage) {
        log.debug("Sending answer back: {}", sendMessage);
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

    @Override
    public void produceDocAnswer(SendMessage sendMessage) {

    }
}
