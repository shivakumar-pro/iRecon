package com.impacto.irecon.command.reconciliationdefinition.controller;

import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationRunResponse;
import com.impacto.irecon.command.reconciliationdefinition.model.CreateEmailStatementRequest;
import com.impacto.irecon.command.reconciliationdefinition.model.EmailStatementRequest;
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
@RequestMapping("/api/v1/reconciliation/email-statement")
@RequiredArgsConstructor
@Tag(name = "Email Statement Reconciliation", description = "API operations for managing email statement reconciliations")
public class EmailStatementReconciliationController {

    private final ReconciliationDefinitionService reconciliationDefinitionService;

    @PostMapping("/process")
    @Operation(
            summary = "Process Email Statement reconciliation",
            description = "Process a new reconciliation using email statement configuration.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reconciliation definition created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> processEmailStatement(
            @Valid @RequestBody EmailStatementRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.processEmailStatementReconciliation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create Email Statement reconciliation definition",
            description = "Creates a new reconciliation definition using email statement configuration.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reconciliation definition created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> createEmailStatement(
            @Valid @RequestBody CreateEmailStatementRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.createEmailStatementReconciliation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update Email Statement reconciliation definition",
            description = "Updates an existing email statement reconciliation definition.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation definition updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> updateEmailStatement(
            @PathVariable Long id,
            @Valid @RequestBody EmailStatementRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.updateEmailStatementReconciliation(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/config")
    @Operation(
            summary = "Update Email Statement reconciliation configuration",
            description = "Partially updates the configuration of an email statement reconciliation definition.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Configuration updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> updateEmailStatementConfig(
            @PathVariable Long id,
            @Valid @RequestBody EmailStatementRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.updateEmailStatementConfig(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/run")
    @Operation(
            summary = "Run Email Statement reconciliation",
            description = "Initiates an email statement reconciliation run based on the provided configuration.",
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
                "Email statement reconciliation run initiated successfully",
                reconciliationDefinitionService.runReconciliation(request)
        );
        return ResponseEntity.ok(response);
    }
} 