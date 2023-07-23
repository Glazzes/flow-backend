package com.glaze.flow.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SignUpRequest(
    @NotBlank(message = "{constraints.field-required}")
    String username,

    @Email(message = "{constraints.email.invalid}")
    @NotBlank(message = "{constraints.field-required}")
    String email,

    @NotBlank(message = "{constraints.field-required}")
    @Length(message = "{constraints.password.length}", max = 100, min = 8)
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
