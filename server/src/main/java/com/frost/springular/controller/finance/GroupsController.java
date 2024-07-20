package com.frost.springular.controller.finance;

import java.util.List;
import java.util.Map;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.model.TransactionGroupModel;
import com.frost.springular.request.TransactionGroupRequest;
import com.frost.springular.response.TransactionGroupResponse;
import com.frost.springular.service.FinanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/finance/groups")
public class GroupsController {
  private final FinanceService financeService;
  private final ConversionService conversionService;

  public GroupsController(
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

  @PatchMapping("/{id}")
  public ResponseEntity<TransactionGroupResponse> updateTransactionGroup(
      @PathVariable String id,
      @Valid @RequestBody TransactionGroupRequest request) {
    return ResponseEntity.ok(conversionService.convert(
        financeService.update(id, request),
        TransactionGroupResponse.class));
  }
}
