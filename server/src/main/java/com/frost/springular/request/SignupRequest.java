package com.frost.springular.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
  @NotBlank(message = "First name is required.")
  private String firstName;

  @NotBlank(message = "Last name is required.")
  private String lastName;

  @Past(message = "Invalid date of birth.")
  @NotNull(message = "Date of birth is required.")
  private LocalDate dateOfBirth;

  @NotBlank(message = "Email is required.")
  // @Pattern(regexp = "^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$", message =
  // "Invalid email address.")
  @Email(message = "Invalid email address.")
  private String email;

  @NotBlank(message = "Password is required.")
  private String password;
}
