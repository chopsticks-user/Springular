package com.frost.springular.response;

import java.time.Instant;

import com.frost.springular.model.TransactionModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
  private String id;
  private Instant time;
  private String note;
  private double revenues;
  private double expenses;
  private String groupId;
  private String userId;

  public TransactionResponse(TransactionModel model) {
    this.id = model.getId();
    this.time = model.getTime();
    this.note = model.getNote();
    this.revenues = model.getRevenues();
    this.expenses = model.getExpenses();
    this.groupId = model.getTransactionGroupModel().getId();
    this.userId = model.getUserModel().getId();
  }
}
