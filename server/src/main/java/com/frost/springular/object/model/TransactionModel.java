package com.frost.springular.object.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "finance_transactions")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, updatable = false, unique = true)
  private String id;

  @Column(nullable = false)
  private Instant time;

  @Column(nullable = false)
  private String note;

  @Column(nullable = false)
  private double revenues;

  @Column(nullable = false)
  private double expenses;

  @ManyToOne
  @JoinColumn(name = "finance_transaction_groups", referencedColumnName = "id", nullable = false)
  private TransactionGroupModel transactionGroupModel;
}
