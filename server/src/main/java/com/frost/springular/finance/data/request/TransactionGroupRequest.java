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
  private String path;
  private String description;
}
