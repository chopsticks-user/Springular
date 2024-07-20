package com.frost.springular.request;

import com.frost.springular.validator.TransactionGroupRequestConstraint;

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
