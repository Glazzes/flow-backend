package com.glaze.flow.controllers;

import com.glaze.flow.dtos.request.SignUpRequest;
import com.glaze.flow.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/account-registration")
    public ResponseEntity<Long> save(@Valid @RequestBody SignUpRequest request) {
        Long savedId = userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedId);
    }

}