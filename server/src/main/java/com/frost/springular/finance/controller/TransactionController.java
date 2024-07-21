package com.frost.springular.finance.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.finance.data.model.TransactionModel;
import com.frost.springular.finance.data.request.TransactionRequest;
import com.frost.springular.finance.data.response.TransactionResponse;
import com.frost.springular.finance.service.FinanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/finance/transactions")
class TransactionController {
  private final FinanceService financeService;
  private final ConversionService conversionService;

  public TransactionController(
      final FinanceService financeService,
      final ConversionService conversionService) {
    this.financeService = financeService;
    this.conversionService = conversionService;
  }

  @GetMapping
  public ResponseEntity<List<TransactionResponse>> getTransactionsBetween(
      @RequestParam String interval, @RequestParam Instant start) {
    return ResponseEntity.ok(financeService.filterTransactionsByInterval(
        interval, start.truncatedTo(ChronoUnit.MONTHS))
        .stream()
        .map((transactionModel) -> conversionService.convert(
            transactionModel, TransactionResponse.class))
        .toList());
  }

  @PostMapping
  ResponseEntity<TransactionResponse> createTransaction(
      @Valid @RequestBody TransactionRequest transactionRequest) {
    return ResponseEntity.ok(conversionService.convert(
        financeService.create(transactionRequest),
        TransactionResponse.class));
  }

  @PatchMapping("/{id}")
  ResponseEntity<TransactionResponse> updateTransaction(
      @PathVariable String id,
      @Valid @RequestBody TransactionRequest transactionRequest) {
    return ResponseEntity.ok(conversionService.convert(
        financeService.update(id, transactionRequest),
        TransactionResponse.class));
  }

  @DeleteMapping("/{id}")
  void deleteTransaction(@PathVariable String id) {
    financeService.delete(id, TransactionModel.class);
  }
}
