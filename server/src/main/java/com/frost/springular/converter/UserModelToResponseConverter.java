package com.frost.springular.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.model.UserModel;
import com.frost.springular.response.UserResponse;

@Component
public class UserModelToResponseConverter
    implements Converter<UserModel, UserResponse> {
  @Override
  public UserResponse convert(UserModel model) {
    return UserResponse.builder()
        .id(model.getId())
        .firstName(model.getFirstName())
        .lastName(model.getLastName())
        .dateOfBirth(model.getDateOfBirth())
        .email(model.getEmail())
        .createdAt(model.getCreatedAt())
        .build();
  }
}
