package com.frost.springular.controller.finance;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.response.TransactionResponse;
import com.frost.springular.service.FinanceService;
import com.frost.springular.service.UserService;

@RestController
@RequestMapping("/api/finance/transactions")
public class TransactionsController {
  private final FinanceService financeService;
  private final ConversionService conversionService;

  public TransactionsController(
      final FinanceService financeService,
      final ConversionService conversionService) {
    this.financeService = financeService;
    this.conversionService = conversionService;
  }

  @GetMapping("/transactions")
  public ResponseEntity<List<TransactionResponse>> getTransactionsBetween(
      @RequestParam String interval, @RequestParam Instant start) {
    return ResponseEntity.ok(
        financeService.filterTransactionsByInterval(
            interval, start.truncatedTo(ChronoUnit.MONTHS))
            .stream()
            .map((transactionModel) -> conversionService.convert(
                transactionModel, TransactionResponse.class))
            .toList());
  }
}
