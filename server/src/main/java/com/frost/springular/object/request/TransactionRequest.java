package com.frost.springular.object.request;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
  private Instant time;
  private String note;
  private double revenues;
  private double expenses;
  private String groupId;
  private String userId;
}
