package com.impacto.irecon.command.reconciliationdefinition.controller;

import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationRunResponse;
import com.impacto.irecon.command.reconciliationdefinition.model.PhysicalStatementRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/reconciliation/physical-statement")
@RequiredArgsConstructor
@Tag(name = "Physical Statement Reconciliation", description = "API operations for managing physical statement reconciliations")
public class PhysicalStatementReconciliationController {

    private final ReconciliationDefinitionService reconciliationDefinitionService;

    @PostMapping("/create")
    @Operation(
            summary = "Create Physical Statement reconciliation definition",
            description = "Creates a new reconciliation definition using physical statement configuration.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reconciliation definition created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> createPhysicalStatement(
            @Valid @RequestPart("request") PhysicalStatementRequest request,
            @RequestPart("sourceFiles") MultipartFile[] sourceFiles,
            @RequestPart("targetFiles") MultipartFile[] targetFiles) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.createPhysicalStatementReconciliation(request, sourceFiles, targetFiles);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update Physical Statement reconciliation definition",
            description = "Updates an existing physical statement reconciliation definition.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation definition updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> updatePhysicalStatement(
            @PathVariable Long id,
            @Valid @RequestPart("request") PhysicalStatementRequest request,
            @RequestPart(value = "sourceFiles", required = false) MultipartFile[] sourceFiles,
            @RequestPart(value = "targetFiles", required = false) MultipartFile[] targetFiles) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.updatePhysicalStatementReconciliation(id, request, sourceFiles, targetFiles);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/config")
    @Operation(
            summary = "Update Physical Statement reconciliation configuration",
            description = "Partially updates the configuration of a physical statement reconciliation definition.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Configuration updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> updatePhysicalStatementConfig(
            @PathVariable Long id,
            @Valid @RequestPart("request") PhysicalStatementRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.updatePhysicalStatementConfig(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/run")
    @Operation(
            summary = "Run Physical Statement reconciliation",
            description = "Initiates a physical statement reconciliation run based on the provided configuration.",
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
                "Physical statement reconciliation run initiated successfully",
                reconciliationDefinitionService.runReconciliation(request)
        );
        return ResponseEntity.ok(response);
    }
} 