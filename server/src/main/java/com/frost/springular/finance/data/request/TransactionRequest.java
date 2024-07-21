package com.frost.springular.finance.data.request;

import java.time.Instant;

import com.frost.springular.finance.service.validator.TransactionRequestConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TransactionRequestConstraint
public class TransactionRequest {
  private Instant time;
  private String note;
  private double revenues;
  private double expenses;
  private String groupPath;
  // private String userId;
}
