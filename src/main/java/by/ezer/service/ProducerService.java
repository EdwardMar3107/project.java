package by.ezer.service;

import by.ezer.aspect.annotation.Loggable;
import by.ezer.config.RabbitConfig;
import by.ezer.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmail(EmailMessage message) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                message
        );
        log.info("Сообщение для отправки добавлено в очередь: {}", message.getTo());
    }
}
