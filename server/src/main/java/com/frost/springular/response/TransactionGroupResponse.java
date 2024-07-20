package com.frost.springular.response;

import com.frost.springular.model.TransactionGroupModel;

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
}
