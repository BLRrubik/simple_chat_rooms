package ru.vibelab.stompwebsocket.service;

import org.springframework.stereotype.Service;
import ru.vibelab.stompwebsocket.entity.User;
import ru.vibelab.stompwebsocket.requests.UserCreateRequest;
import ru.vibelab.stompwebsocket.requests.UserLoginRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();
    private Long counter = 1L;


    public User addUser(UserCreateRequest userCreateRequest) {
        if(users.stream().map(User::getName).anyMatch(name -> name.equals(userCreateRequest.getName()))) {
            return null;
        }

        User user = new User();
        user.setId(counter++);
        user.setName(userCreateRequest.getName());
        user.setPassword(userCreateRequest.getPassword());

        users.add(user);

        System.out.println("User registered");
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        return user;
    }

    public User login(UserLoginRequest request) {
        if (users.stream().map(User::getName).noneMatch(name -> name.equals(request.getName()))) {
            return null;
        }

        User user = users.stream().filter(usr -> usr.getName().equals(request.getName())).findFirst().get();

        if (!user.getPassword().equals(request.getPassword())) {
            return null;
        }

        return user;
    }

    public List<User> getAll() {
        return users;
    }
}
