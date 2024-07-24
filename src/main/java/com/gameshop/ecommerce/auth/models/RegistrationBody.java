package com.gameshop.ecommerce.auth.models;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrationBody {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(\\+380)[0-9]{9}")
    private String phoneNumber;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
