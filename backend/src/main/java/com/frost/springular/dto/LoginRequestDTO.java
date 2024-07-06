package com.frost.springular.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Email is required.")
    // @Pattern(regexp = "^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$", message =
    // "Invalid email address.")
    @Email(message = "Invalid email address.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
