package ru.vibelab.stompwebsocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vibelab.stompwebsocket.entity.User;
import ru.vibelab.stompwebsocket.requests.UserCreateRequest;
import ru.vibelab.stompwebsocket.requests.UserLoginRequest;
import ru.vibelab.stompwebsocket.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(@RequestBody UserLoginRequest userLoginRequest) {
        return userService.login(userLoginRequest);
    }

    @PostMapping("/register")
    public User register(@RequestBody UserCreateRequest request) {
        return userService.addUser(request);
    }
}
