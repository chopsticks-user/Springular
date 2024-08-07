package com.frost.springular.finance.data.response;

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
  private String path;
  private int level;
  private String description;
  private double revenues;
  private double expenses;
  private String userId;
}
