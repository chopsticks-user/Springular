package com.frost.springular.finance.data.model;

import com.frost.springular.user.data.model.UserModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "finance_transaction_groups", uniqueConstraints = @UniqueConstraint(columnNames = { "path", "user_id" }))
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionGroupModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, updatable = false, unique = true)
  private String id;

  @Column(nullable = false)
  private String path;

  @Column(nullable = false)
  private int level;

  @Column
  private String description;

  @Column(nullable = false)
  private double revenues;

  @Column(nullable = false)
  private double expenses;

  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private UserModel user;
}
