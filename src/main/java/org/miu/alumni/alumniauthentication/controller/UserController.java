package org.miu.alumni.alumniauthentication.controller;


import lombok.RequiredArgsConstructor;
import org.miu.alumni.alumniauthentication.entity.User;
import org.miu.alumni.alumniauthentication.entity.dto.request.LoginRequest;
import org.miu.alumni.alumniauthentication.entity.dto.response.LoginResponse;
import org.miu.alumni.alumniauthentication.service.AuthService;
import org.miu.alumni.alumniauthentication.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    static String QUEUE_NAME = "user-removal";

    private final RabbitTemplate rabbitTemplate;

    private final UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<?> singin(@PathVariable String email) {
        var userDetails = userService.findUserByEmail(email);
        return new ResponseEntity<User>(
                userDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        User user = userService.findUserByEmail(email);
        rabbitTemplate.convertAndSend(QUEUE_NAME, user.getId());
        return new ResponseEntity<Map<String, String>>(
                Map.of("message: ", "User with (" + email + ") is deleted successfully"),
                HttpStatus.OK
        );
    }
}
