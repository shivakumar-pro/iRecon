package com.impacto.irecon.command.rulemaintenance.controller;

import com.impacto.irecon.command.rulemaintenance.dto.RuleSummaryResponse;
import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
import com.impacto.irecon.command.rulemaintenance.model.RuleMaintenanceRequest;
import com.impacto.irecon.command.rulemaintenance.service.RuleMaintenanceService;
import com.impacto.irecon.common.response.PaginationResponse;
import com.impacto.irecon.common.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
public class RuleMaintenanceController {

    private final RuleMaintenanceService ruleMaintenanceService;

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<RuleSummaryResponse>>> getAllRules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ruleName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        SuccessResponse<PaginationResponse<RuleSummaryResponse>> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rules retrieved successfully",
                ruleMaintenanceService.getAllRules(pageRequest)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<PaginationResponse<RuleSummaryResponse>>> searchRules(
            @RequestParam String ruleName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ruleName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        SuccessResponse<PaginationResponse<RuleSummaryResponse>> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rules retrieved successfully",
                ruleMaintenanceService.searchRules(ruleName, pageRequest)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ruleId}")
    public ResponseEntity<SuccessResponse<RuleMaintenance>> getRuleById(@PathVariable String ruleId) {
        SuccessResponse<RuleMaintenance> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rule retrieved successfully",
                ruleMaintenanceService.getRuleById(ruleId)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{ruleName}")
    public ResponseEntity<SuccessResponse<RuleMaintenance>> getRuleByName(@PathVariable String ruleName) {
        SuccessResponse<RuleMaintenance> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rule retrieved successfully",
                ruleMaintenanceService.getRuleByName(ruleName)
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<RuleSummaryResponse>> createRule(@Valid @RequestBody RuleMaintenanceRequest request) {
        SuccessResponse<RuleSummaryResponse> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rule created successfully",
                ruleMaintenanceService.createRule(request)
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{ruleId}")
    public ResponseEntity<SuccessResponse<RuleSummaryResponse>> updateRule(
            @PathVariable String ruleId,
            @Valid @RequestBody RuleMaintenanceRequest request) {
        SuccessResponse<RuleSummaryResponse> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rule updated successfully",
                ruleMaintenanceService.updateRule(ruleId, request)
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{ruleId}")
    public ResponseEntity<SuccessResponse<Void>> deleteRule(@PathVariable String ruleId) {
        ruleMaintenanceService.deleteRule(ruleId);
        SuccessResponse<Void> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rule deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }
}