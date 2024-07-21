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
