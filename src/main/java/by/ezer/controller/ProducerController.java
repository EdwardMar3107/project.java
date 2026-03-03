package by.ezer.controller;

import by.ezer.dto.EmailMessage;
import by.ezer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public String send(@RequestBody EmailMessage message) {
        producerService.sendEmail(message);
        return "Message sent";
    }
}
