package com.club.controllers;

import com.club.entities.Customer;
import com.club.entities.Message;
import com.club.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages(@AuthenticationPrincipal Customer customer) {
        System.out.println(customer);

    return messageService.getAllMessages();
    }

}
