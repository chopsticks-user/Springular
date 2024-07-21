package com.frost.springular.user.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.shared.util.tuple.Pair;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.data.request.SignupRequest;

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
