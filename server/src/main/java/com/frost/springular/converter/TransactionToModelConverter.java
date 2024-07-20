package com.frost.springular.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.model.TransactionGroupModel;
import com.frost.springular.model.TransactionModel;
import com.frost.springular.model.UserModel;
import com.frost.springular.request.TransactionRequest;
import com.frost.springular.util.Pair;
import com.frost.springular.util.Triplet;

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
