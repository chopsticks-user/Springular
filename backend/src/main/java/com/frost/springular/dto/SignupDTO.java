package com.frost.springular.dto;

import java.sql.Date;

public record SignupDTO(String firstName, String lastName, Date dataOfBirth, String email, String password) {
}
