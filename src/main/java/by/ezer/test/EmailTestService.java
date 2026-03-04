//package by.ezer.test;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailTestService {
//
//    private final JavaMailSender mailSender;
//
//    public void sendTestEmail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setFrom("pakachun48@gmail.com");           // ← должен совпадать с username выше
//        message.setTo("pakachun48@gmail.com");             // ← куда отправить (можно себе)
//        message.setSubject("Тестовое письмо из Java");
//        message.setText("Hello Eduard");
//
//        mailSender.send(message);
//
//        System.out.println("Тестовое письмо отправлено!");
//    }
//}

