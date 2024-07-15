package com.frost.springular.object.response;

import java.time.LocalDate;

import com.frost.springular.object.model.UserModel;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private Date createdAt;

    public UserInfoResponse(UserModel userEntity) {
        id = userEntity.getId();
        firstName = userEntity.getFirstName();
        lastName = userEntity.getLastName();
        dateOfBirth = userEntity.getDateOfBirth();
        email = userEntity.getEmail();
        createdAt = userEntity.getCreatedAt();
    }
}