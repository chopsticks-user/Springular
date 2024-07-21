package com.frost.springular.finance.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.finance.data.model.TransactionGroupModel;
import com.frost.springular.finance.data.request.TransactionGroupRequest;
import com.frost.springular.shared.tuple.Pair;
import com.frost.springular.user.data.model.UserModel;

@Component
public class TransactionGroupToModelConverter implements
    Converter<Pair<TransactionGroupRequest, UserModel>, TransactionGroupModel> {
  @Override
  public TransactionGroupModel convert(
      Pair<TransactionGroupRequest, UserModel> pair) {
    String path = pair.getFirst().getPath();
    int level = (int) (path.equals("/") ? 0
        : path.chars()
            .filter(c -> c == '/')
            .count() - 1);

    return TransactionGroupModel.builder()
        .path(path)
        .level(level)
        .description(pair.getFirst().getDescription())
        .user(pair.getSecond())
        .build();
  }
}
