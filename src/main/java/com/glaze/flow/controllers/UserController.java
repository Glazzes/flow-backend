package com.glaze.flow.controllers;

import jakarta.validation.Valid;

import com.glaze.flow.dtos.request.SignUpRequest;
import com.glaze.flow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/account-registration")
    public ResponseEntity<Long> save(@Valid @RequestBody SignUpRequest request) {
        LOGGER.info("User registration endpoint reached with request {}", request);
        Long savedId = userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedId);
    }

}
