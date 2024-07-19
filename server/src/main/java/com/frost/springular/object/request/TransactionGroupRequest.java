package com.frost.springular.object.request;

import com.frost.springular.object.validator.TransactionGroupRequestConstraint;

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
