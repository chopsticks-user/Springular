package com.frost.springular.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.model.TransactionGroupModel;
import com.frost.springular.model.UserModel;
import com.frost.springular.request.TransactionGroupRequest;
import com.frost.springular.util.Pair;

@Component
public class TransactionGroupToModelConverter implements
    Converter<Pair<TransactionGroupRequest, UserModel>, TransactionGroupModel> {
  @Override
  public TransactionGroupModel convert(
      Pair<TransactionGroupRequest, UserModel> pair) {
    return TransactionGroupModel.builder()
        .name(pair.getFirst().getName())
        .description(pair.getFirst().getDescription())
        .revenues(pair.getFirst().getRevenues())
        .expenses(pair.getFirst().getExpenses())
        .parentId(pair.getFirst().getParentId())
        .user(pair.getSecond())
        .build();
  }
}
