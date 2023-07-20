package com.glaze.flow.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SignUpRequest(
    @NotBlank(message = "{generic.required}")
    String username,

    @Email(message = "{email.invalid}")
    @NotBlank(message = "{generic.required}")
    String email,

    @NotBlank(message = "{generic.required}")
    @Length(message = "{password.length}", max = 100, min = 8)
    String password
){

    @Override
    public String toString() {
        return "SignUpRequest{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
