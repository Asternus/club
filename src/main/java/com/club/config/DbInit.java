package com.club.config;


import com.club.entities.Message;
import com.club.services.MessageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DbInit {

    private final MessageService messageService;

    private Random random = new Random();

    @Autowired
    public DbInit(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 100; i++) {
            final Message message = new Message();
            message.setText(random.nextInt() + "");
            messageService.saveMassage(message);
        }

    }
}
