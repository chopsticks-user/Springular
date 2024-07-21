package com.frost.springular.finance.data.request;

import com.frost.springular.finance.service.validator.TransactionGroupRequestConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TransactionGroupRequestConstraint
public class TransactionGroupRequest {
  private String name;
  private String description;
  private double revenues;
  private double expenses;
  private String parentId;
  private String userId;
}
