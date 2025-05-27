package com.impacto.irecon.command.rulemaintenance.controller;


import com.impacto.irecon.command.rulemaintenance.dto.RuleSummaryResponse;
import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
import com.impacto.irecon.command.rulemaintenance.model.RuleMaintenanceRequest;
import com.impacto.irecon.command.rulemaintenance.service.RuleMaintenanceService;
import com.impacto.irecon.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
public class RuleMaintenanceController {

    private final RuleMaintenanceService ruleMaintenanceService;

    @GetMapping
    public ResponseEntity<List<RuleSummaryResponse>> getAllRules() {
        return ResponseEntity.ok(ruleMaintenanceService.getAllRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleMaintenance> getRuleById(@PathVariable UUID id) {
        return ResponseEntity.ok(ruleMaintenanceService.getRuleById(id));
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<RuleSummaryResponse>> createRule(@RequestBody RuleMaintenanceRequest request) {

        SuccessResponse<RuleSummaryResponse> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rule created successfully",
                ruleMaintenanceService.createRule(request)
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<RuleSummaryResponse>> updateRule(@PathVariable UUID id, @RequestBody RuleMaintenanceRequest request) {
        SuccessResponse<RuleSummaryResponse> response = new SuccessResponse<>(
                LocalDateTime.now(),
                200,
                "Rule updated successfully",
                ruleMaintenanceService.updateRule(id, request));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id) {
        ruleMaintenanceService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}