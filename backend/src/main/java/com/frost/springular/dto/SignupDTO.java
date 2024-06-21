package com.frost.springular.dto;

// TODO: dob should be of type Date
public record SignupDTO(String firstName, String lastName, String dateOfBirth, String email, String password) {
}
