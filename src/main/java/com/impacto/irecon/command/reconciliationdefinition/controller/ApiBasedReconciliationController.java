package com.impacto.irecon.command.reconciliationdefinition.controller;

import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationRunResponse;
import com.impacto.irecon.command.reconciliationdefinition.model.ApiBasedRequest;
import com.impacto.irecon.command.reconciliationdefinition.model.CreateApiBasedRequest;
import com.impacto.irecon.command.reconciliationdefinition.model.ReconciliationRunRequest;
import com.impacto.irecon.command.reconciliationdefinition.service.ReconciliationDefinitionService;
import com.impacto.irecon.common.enums.HttpResponseCode;
import com.impacto.irecon.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/reconciliation/api-based")
@RequiredArgsConstructor
@Tag(name = "API-Based Reconciliation", description = "API operations for managing API-based reconciliations")
public class ApiBasedReconciliationController {

    private final ReconciliationDefinitionService reconciliationDefinitionService;

    @PostMapping("/process")
    @Operation(
            summary = "Process API-based reconciliation",
            description = "Process a new reconciliation using API-based configuration.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reconciliation definition created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> processApiBased(
            @Valid @RequestBody ApiBasedRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.processApiBasedReconciliation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create API-based reconciliation definition",
            description = "Creates a new reconciliation definition using API-based configuration.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reconciliation definition created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> createApiBased(
            @Valid @RequestBody CreateApiBasedRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.createApiBasedReconciliation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update API-based reconciliation definition",
            description = "Updates an existing API-based reconciliation definition.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation definition updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> updateApiBased(
            @PathVariable Long id,
            @Valid @RequestBody ApiBasedRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.updateApiBasedReconciliation(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/config")
    @Operation(
            summary = "Update API-based reconciliation configuration",
            description = "Partially updates the configuration of an API-based reconciliation definition.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Configuration updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> updateApiBasedConfig(
            @PathVariable Long id,
            @Valid @RequestBody ApiBasedRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.updateApiBasedConfig(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/run")
    @Operation(
            summary = "Run API-based reconciliation",
            description = "Initiates an API-based reconciliation run based on the provided configuration.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation run initiated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation not found"),
                    @ApiResponse(responseCode = "409", description = "Reconciliation run already exists")
            }
    )
    public ResponseEntity<SuccessResponse<ReconciliationRunResponse>> runReconciliation(
            @Valid @RequestBody ReconciliationRunRequest request) {
        SuccessResponse<ReconciliationRunResponse> response = new SuccessResponse<>(
                LocalDateTime.now(),
                HttpResponseCode.SUCCESS.getCode(),
                "API-based reconciliation run initiated successfully",
                reconciliationDefinitionService.runReconciliation(request)
        );
        return ResponseEntity.ok(response);
    }
} 