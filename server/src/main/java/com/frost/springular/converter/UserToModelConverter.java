package com.frost.springular.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.model.UserModel;
import com.frost.springular.request.SignupRequest;
import com.frost.springular.util.Pair;

@Component
public class UserToModelConverter
    implements Converter<Pair<SignupRequest, String>, UserModel> {
  @Override
  public UserModel convert(Pair<SignupRequest, String> pair) {
    return UserModel.builder()
        .firstName(pair.getFirst().getFirstName())
        .lastName(pair.getFirst().getLastName())
        .dateOfBirth(pair.getFirst().getDateOfBirth())
        .email(pair.getFirst().getEmail())
        .password(pair.getSecond())
        .build();
  }
}
