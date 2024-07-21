package com.frost.springular.finance.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.finance.data.model.TransactionGroupModel;
import com.frost.springular.finance.data.model.TransactionModel;
import com.frost.springular.finance.data.request.TransactionRequest;
import com.frost.springular.shared.tuple.Triplet;
import com.frost.springular.user.data.model.UserModel;

@Component
public class TransactionToModelConverter implements
    Converter<Triplet<TransactionRequest, TransactionGroupModel, UserModel>, TransactionModel> {
  @Override
  public TransactionModel convert(
      Triplet<TransactionRequest, TransactionGroupModel, UserModel> pair) {
    return TransactionModel.builder()
        .time(pair.getFirst().getTime())
        .note(pair.getFirst().getNote())
        .revenues(pair.getFirst().getRevenues())
        .expenses(pair.getFirst().getExpenses())
        .group(pair.getSecond())
        .user(pair.getThird())
        .build();
  }
}
