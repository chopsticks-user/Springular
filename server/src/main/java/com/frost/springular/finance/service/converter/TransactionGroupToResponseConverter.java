package com.frost.springular.finance.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.finance.data.model.TransactionGroupModel;
import com.frost.springular.finance.data.response.TransactionGroupResponse;

@Component
public class TransactionGroupToResponseConverter
    implements Converter<TransactionGroupModel, TransactionGroupResponse> {
  @Override
  public TransactionGroupResponse convert(TransactionGroupModel model) {
    return TransactionGroupResponse.builder()
        .id(model.getId())
        .path(model.getPath())
        .level(model.getLevel())
        .description(model.getDescription())
        .revenues(model.getRevenues())
        .expenses(model.getExpenses())
        .userId(model.getUser().getId())
        .build();
  }
}
