package com.frost.springular.user.data.response;

import java.time.LocalDate;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private String id;
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private String email;
  private Date createdAt;
}