package com.frost.springular.user.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.data.response.UserResponse;

@Component
public class UserToResponseConverter
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
