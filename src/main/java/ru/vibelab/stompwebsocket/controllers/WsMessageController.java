package ru.vibelab.stompwebsocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.vibelab.stompwebsocket.entity.Message;
import ru.vibelab.stompwebsocket.requests.MessageAddRequest;
import ru.vibelab.stompwebsocket.service.MessageService;

@Controller
@CrossOrigin("*")
public class WsMessageController {

    private final MessageService messageService;

    @Autowired
    public WsMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public Message sendMessage(@Payload MessageAddRequest messageAddRequest) {
        return messageService.addMessage(messageAddRequest);
    }
}
