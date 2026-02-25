package by.ezer.service;

import by.ezer.config.RabbitConfig;
import by.ezer.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveAndSendEmail(EmailMessage message) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(message.getTo());
            mail.setSubject(message.getSubject());
            mail.setText(message.getText());
            mail.setFrom("your-email@gmail.com");

            javaMailSender.send(mail);

            log.info("Письмо успешно отправлено на {}", message.getTo());
        } catch (MailException e) {
            log.error("Ошибка отправки письма на {}: {}", message.getTo(), e.getMessage());
            throw e;
        }
    }
}
