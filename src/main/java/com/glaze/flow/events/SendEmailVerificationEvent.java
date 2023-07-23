package com.glaze.flow.events;

import com.glaze.flow.entities.User;

public record SendEmailVerificationEvent(
    User user,
    String token
){}
