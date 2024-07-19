package com.frost.springular.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.object.model.TransactionGroupModel;
import com.frost.springular.object.request.TransactionGroupRequest;
import com.frost.springular.object.response.TransactionGroupResponse;
import com.frost.springular.object.response.TransactionResponse;
import com.frost.springular.service.FinanceService;
import com.frost.springular.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {
  private final UserService userService;
  private final FinanceService financeService;

  public FinanceController(UserService userService, FinanceService financeService) {
    this.userService = userService;
    this.financeService = financeService;
  }

  @GetMapping("/groups")
  public ResponseEntity<List<TransactionGroupResponse>> getAllTransactionGroups() {
    return ResponseEntity.ok(
        this.financeService.getAllTransactionGroups(
            this.userService.getCurrentUser())
            .stream()
            .map((groupModel) -> new TransactionGroupResponse(groupModel))
            .toList());
  }

  @PostMapping("/groups")
  public ResponseEntity<TransactionGroupResponse> createTransactionGroup(
      @Valid @RequestBody TransactionGroupRequest request) {
    return null;
  }

  @GetMapping("/transactions")
  public ResponseEntity<List<TransactionResponse>> getTransactionsBetween(
      @RequestParam String interval, @RequestParam Instant start) {
    return ResponseEntity.ok(
        this.financeService.filterTransactionsByInterval(
            interval, start.truncatedTo(ChronoUnit.MONTHS),
            this.userService.getCurrentUser())
            .stream()
            .map((transactionModel) -> new TransactionResponse(transactionModel))
            .toList());
  }
}
