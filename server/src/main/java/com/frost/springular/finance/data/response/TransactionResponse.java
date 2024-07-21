package com.frost.springular.finance.data.response;

import java.time.Instant;

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
}
