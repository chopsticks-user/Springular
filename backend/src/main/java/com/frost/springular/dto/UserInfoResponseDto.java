package com.frost.springular.dto;

import java.time.LocalDate;

import com.frost.springular.entity.UserEntity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private Date createdAt;

    public UserInfoResponseDto(UserEntity userEntity) {
        id = userEntity.getId();
        firstName = userEntity.getFirstName();
        lastName = userEntity.getLastName();
        dateOfBirth = userEntity.getDateOfBirth();
        email = userEntity.getEmail();
        createdAt = userEntity.getCreatedAt();
    }
}