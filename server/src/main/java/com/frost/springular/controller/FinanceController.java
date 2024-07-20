package com.frost.springular.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.exception.FinanceException;
import com.frost.springular.model.TransactionGroupModel;
import com.frost.springular.request.TransactionGroupRequest;
import com.frost.springular.response.TransactionGroupResponse;
import com.frost.springular.response.TransactionResponse;
import com.frost.springular.service.FinanceService;
import com.frost.springular.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {
  private final UserService userService;
  private final FinanceService financeService;
  private final ConversionService conversionService;

  public FinanceController(
      final UserService userService,
      final FinanceService financeService,
      final ConversionService conversionService) {
    this.userService = userService;
    this.financeService = financeService;
    this.conversionService = conversionService;
  }

  @GetMapping("/groups")
  public ResponseEntity<List<TransactionGroupResponse>> getAllTransactionGroups() {
    return ResponseEntity.ok(
        financeService.getAllTransactionGroups(
            userService.getCurrentUser())
            .stream()
            .map((groupModel) -> conversionService.convert(
                groupModel, TransactionGroupResponse.class))
            .toList());
  }

  @PostMapping("/groups")
  public ResponseEntity<TransactionGroupResponse> createTransactionGroup(
      @Valid @RequestBody TransactionGroupRequest request) {
    return ResponseEntity.ok(conversionService.convert(
        financeService.save(
            TransactionGroupModel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .revenues(request.getRevenues())
                .expenses(request.getExpenses())
                .parentId(request.getParentId())
                .build(),
            userService.getCurrentUser()),
        TransactionGroupResponse.class));
  }

  @PatchMapping("/groups/{id}")
  public ResponseEntity<TransactionGroupResponse> updateTransactionGroup(
      @PathVariable String id,
      @Valid @RequestBody TransactionGroupRequest request) {
    // todo: implement an update method in FinanceService
    TransactionGroupModel model = financeService.findTransactionGroupById(id)
        .orElseThrow(() -> new FinanceException(
            "Could not find transaction group"));

    model.setName(request.getName());
    model.setDescription(request.getDescription());
    model.setRevenues(request.getRevenues());
    model.setExpenses(request.getExpenses());
    model.setParentId(request.getParentId());

    return ResponseEntity.ok(conversionService.convert(financeService.save(model),
        TransactionGroupResponse.class));
  }

  @GetMapping("/transactions")
  public ResponseEntity<List<TransactionResponse>> getTransactionsBetween(
      @RequestParam String interval, @RequestParam Instant start) {
    return ResponseEntity.ok(
        financeService.filterTransactionsByInterval(
            interval, start.truncatedTo(ChronoUnit.MONTHS),
            userService.getCurrentUser())
            .stream()
            .map((transactionModel) -> conversionService.convert(
                transactionModel, TransactionResponse.class))
            .toList());
  }
}
