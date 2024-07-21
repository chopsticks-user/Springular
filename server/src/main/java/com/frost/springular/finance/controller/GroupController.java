package com.frost.springular.finance.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.finance.data.model.TransactionGroupModel;
import com.frost.springular.finance.data.request.TransactionGroupRequest;
import com.frost.springular.finance.data.response.TransactionGroupResponse;
import com.frost.springular.finance.exception.FinanceException;
import com.frost.springular.finance.service.FinanceService;
import com.frost.springular.shared.util.stream.StreamHelper;
import com.frost.springular.shared.util.tuple.Pair;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/finance/groups")
class GroupController {
  private final FinanceService financeService;
  private final ConversionService conversionService;

  public GroupController(
      final FinanceService financeService,
      final ConversionService conversionService) {
    this.financeService = financeService;
    this.conversionService = conversionService;
  }

  @GetMapping
  public ResponseEntity<List<TransactionGroupResponse>> getAllTransactionGroups() {
    List<TransactionGroupModel> ascSortedGroups = financeService.getAllGroups();

    return ResponseEntity.ok(StreamHelper.zip(
        financeService.getAllGroups().stream(),
        financeService.getActualRevenuesAndExpenses(ascSortedGroups).stream())
        .map((pair) -> {
          TransactionGroupResponse response = conversionService
              .convert(pair.getFirst(), TransactionGroupResponse.class);

          pair.getSecond().apply((revenues, expenses) -> {
            response.setRevenues(revenues);
            response.setExpenses(expenses);
          });

          return response;
        })
        .toList());
  }

  @PostMapping
  public ResponseEntity<TransactionGroupResponse> createTransactionGroup(
      @Valid @RequestBody TransactionGroupRequest request) {
    TransactionGroupResponse groupResponse = conversionService.convert(
        financeService.create(request),
        TransactionGroupResponse.class);

    financeService.getActualRevenuesAndExpenses(groupResponse.getId())
        .apply((revenues, expenses) -> {
          groupResponse.setRevenues(revenues);
          groupResponse.setExpenses(expenses);
        });

    return ResponseEntity.ok(groupResponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionGroupResponse> getTransactionGroup(
      @PathVariable String id) {
    TransactionGroupResponse groupResponse = conversionService.convert(
        financeService.findGroupByIdThrowIfNot(id),
        TransactionGroupResponse.class);

    financeService.getActualRevenuesAndExpenses(id).apply(
        (revenues, expenses) -> {
          groupResponse.setRevenues(revenues);
          groupResponse.setExpenses(expenses);
        });

    return ResponseEntity.ok(groupResponse);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<TransactionGroupResponse> updateTransactionGroup(
      @PathVariable String id,
      @Valid @RequestBody TransactionGroupRequest request) {
    TransactionGroupResponse groupResponse = conversionService.convert(
        financeService.update(id, request),
        TransactionGroupResponse.class);

    financeService.getActualRevenuesAndExpenses(id).apply(
        (revenues, expenses) -> {
          groupResponse.setRevenues(revenues);
          groupResponse.setExpenses(expenses);
        });

    return ResponseEntity.ok(groupResponse);
  }

  @DeleteMapping("/{id}")
  public void deleteTransactionGroup(@PathVariable String id) {
    financeService.delete(id, TransactionGroupModel.class);
  }
}
