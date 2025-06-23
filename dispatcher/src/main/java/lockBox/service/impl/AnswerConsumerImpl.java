package lockBox.service.impl;

import lockBox.controller.UpdateController;
import lockBox.service.AnswerConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static lockBox.RabbitQueue.ANSWER_MESSAGE;
//DispatcherApplication
@Slf4j
@Service
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateController updateController;//UpdateProcessor->UpdateController

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        log.debug("Received answer: {}", sendMessage);
        updateController.setView(sendMessage);
    }
}
