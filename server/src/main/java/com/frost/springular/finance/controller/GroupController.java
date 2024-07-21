package com.frost.springular.finance.controller;

import java.util.List;
import java.util.Map;

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

  @GetMapping("")
  public ResponseEntity<List<TransactionGroupResponse>> getAllTransactionGroups() {
    List<TransactionGroupModel> groupModels = financeService.getAllGroups();
    Map<String, String> groupPaths = financeService.getGroupPaths(groupModels);

    return ResponseEntity.ok(groupModels.stream()
        .map((groupModel) -> {
          var response = conversionService
              .convert(groupModel, TransactionGroupResponse.class);
          response.setPath(groupPaths.get(groupModel.getId()));
          return response;
        })
        .toList());
  }

  @PostMapping("")
  public ResponseEntity<TransactionGroupResponse> createTransactionGroup(
      @Valid @RequestBody TransactionGroupRequest request) {
    return ResponseEntity.ok(conversionService.convert(
        financeService.create(request),
        TransactionGroupResponse.class));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionGroupResponse> getTransactionGroup(
      @PathVariable String id) {
    return ResponseEntity.ok(
        conversionService.convert(
            financeService.findGroupById(id).orElseThrow(
                () -> new FinanceException(
                    "Could not find transaction group")),
            TransactionGroupResponse.class));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<TransactionGroupResponse> updateTransactionGroup(
      @PathVariable String id,
      @Valid @RequestBody TransactionGroupRequest request) {
    return ResponseEntity.ok(conversionService.convert(
        financeService.update(id, request),
        TransactionGroupResponse.class));
  }

  @DeleteMapping("/{id}")
  public void deleteTransactionGroup(@PathVariable String id) {
    financeService.deleteTransactionGroup(id);
  }
}
