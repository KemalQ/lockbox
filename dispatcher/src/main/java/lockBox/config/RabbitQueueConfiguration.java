package lockBox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

import static lockBox.module.RabbitQueue.*;

@Configuration
public class RabbitQueueConfiguration {
    @Bean
    public Queue textMessageQueue(){
        return new Queue(TEXT_MESSAGE_QUEUE, true);
    }
    @Bean
    public Queue photoMessageQueue(){
        return new Queue(PHOTO_MESSAGE_QUEUE, true);
    }
    @Bean
    public Queue docMessageQueue(){
        return new Queue(DOC_MESSAGE_QUEUE, true);
    }
    @Bean
    public Queue answerMessageQueue(){
        return new Queue(ANSWER_MESSAGE_QUEUE, true);
    }
}
