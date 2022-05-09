package ru.vibelab.stompwebsocket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private Long id;
    private List<User> users = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
}
