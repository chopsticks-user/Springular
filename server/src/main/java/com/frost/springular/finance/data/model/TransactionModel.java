package com.frost.springular.finance.data.model;

import java.time.Instant;

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

@Table(name = "finance_transactions", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "group_path" }))
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

  // todo: @ManyToOne(fetch = FetchType.LAZY)
  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "group_path", referencedColumnName = "path")
  private TransactionGroupModel group;

  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private UserModel user;
}
