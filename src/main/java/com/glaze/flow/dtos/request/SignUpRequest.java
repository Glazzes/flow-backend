package com.glaze.flow.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.glaze.flow.validators.email.EmailMustNotBePresent;
import com.glaze.flow.validators.username.UsernameMustNotBePresent;
import org.hibernate.validator.constraints.Length;

public record SignUpRequest(

    @UsernameMustNotBePresent(message = "{constraints.username.already-exists}")
    @NotBlank(message = "{constraints.field-required}")
    String username,

    @EmailMustNotBePresent(message = "{constraints.email.already-exists}")
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
