package com.frost.springular.object.response;

import com.frost.springular.object.model.TransactionGroupModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionGroupResponse {
  private String id;
  private String name;
  private String description;
  private double revenues;
  private double expenses;
  private String userId;
  private String parentId;
  private String path;

  public TransactionGroupResponse(TransactionGroupModel model) {
    this.id = model.getId();
    this.name = model.getName();
    this.description = model.getDescription();
    this.revenues = model.getRevenues();
    this.expenses = model.getExpenses();
    this.userId = model.getUserModel().getId();
    this.parentId = model.getParentId();
    this.path = model.getPath();
  }
}
