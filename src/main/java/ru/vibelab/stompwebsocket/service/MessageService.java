package ru.vibelab.stompwebsocket.service;

import org.springframework.stereotype.Service;
import ru.vibelab.stompwebsocket.entity.Message;
import ru.vibelab.stompwebsocket.requests.MessageAddRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private Long counter = 1L;
    private List<Message> messages = new ArrayList<>();

    public List<Message> getAll() {
        return  messages;
    }

    public Message addMessage(MessageAddRequest messageAddRequest) {
        Message message = new Message();
        message.setId(counter++);
        message.setSenderName(messageAddRequest.getSenderName());
        message.setContent(messageAddRequest.getContent());

        messages.add(message);

        return message;
    }
}
