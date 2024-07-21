package com.frost.springular.finance.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.finance.data.model.TransactionModel;
import com.frost.springular.finance.data.response.TransactionResponse;

@Component
public class TransactionToResponseConverter
    implements Converter<TransactionModel, TransactionResponse> {
  @Override
  public TransactionResponse convert(TransactionModel model) {
    return TransactionResponse.builder()
        .id(model.getId())
        .time(model.getTime())
        .note(model.getNote())
        .revenues(model.getRevenues())
        .expenses(model.getExpenses())
        .groupId(model.getGroup().getId())
        .userId(model.getUser().getId())
        .build();
  }
}
