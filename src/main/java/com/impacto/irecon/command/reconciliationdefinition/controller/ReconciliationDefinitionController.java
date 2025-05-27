package com.impacto.irecon.command.reconciliationdefinition.controller;

import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.model.ApiBasedRequest;
import com.impacto.irecon.command.reconciliationdefinition.model.EmailStatementRequest;
import com.impacto.irecon.command.reconciliationdefinition.service.ReconciliationDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reconciliation/definition")
@RequiredArgsConstructor
@Tag(name = "Reconciliation Definition API", description = "API operations for managing reconciliation definitions")
public class ReconciliationDefinitionController {

    private final ReconciliationDefinitionService reconciliationDefinitionService;

    @PostMapping("/api-based")
    @Operation(
            summary = "Create API-based reconciliation definition",
            description = "Creates a new reconciliation definition using API-based configuration.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reconciliation definition created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> createApiBased(@Valid @RequestBody ApiBasedRequest request) {
        ReconciliationDefinitionResponse response = reconciliationDefinitionService.createApiBasedReconciliation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get reconciliation definition by ID",
            description = "Retrieves a reconciliation definition by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation definition retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> getReconciliationById(@PathVariable UUID id) {
        return ResponseEntity.ok(reconciliationDefinitionService.getReconciliationById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all reconciliation definitions",
            description = "Retrieves all reconciliation definitions.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation definitions retrieved successfully")
            }
    )
    public ResponseEntity<List<ReconciliationDefinitionResponse>> getAllReconciliations() {
        return ResponseEntity.ok(reconciliationDefinitionService.getAllReconciliations());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update reconciliation definition",
            description = "Updates an existing reconciliation definition.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation definition updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> updateReconciliation(
            @PathVariable UUID id,
            @Valid @RequestBody ApiBasedRequest request) {
        return ResponseEntity.ok(reconciliationDefinitionService.updateReconciliation(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete reconciliation definition",
            description = "Deletes a reconciliation definition by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Reconciliation definition deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<Void> deleteReconciliation(@PathVariable UUID id) {
        reconciliationDefinitionService.deleteReconciliation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/email-statement")
    @Operation(
            summary = "Create Email Statement configuration",
            description = "Creates a new email statement reconciliation configuration with multiple target data entries.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reconciliation definition created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<EmailStatementRequest> createEmailStatement(
            @Valid @RequestBody EmailStatementRequest request) {
        // TODO: Add service call and processing logic here
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PostMapping(value = "/physical-statement", consumes = "multipart/form-data")
    @Operation(
            summary = "Upload Physical Statement",
            description = "Uploads source and target bank statement files in PDF, Excel, or scanned image formats.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Files uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid file input or format")
            }
    )
    public ResponseEntity<String> uploadPhysicalStatement(
            @RequestPart("sourceFiles") MultipartFile[] sourceFiles,
            @RequestPart("targetFiles") MultipartFile[] targetFiles) {

        // ✅ TODO: Process files and request metadata
        for (MultipartFile file : sourceFiles) {
            System.out.println("Received Source File: " + file.getOriginalFilename());
        }

        for (MultipartFile file : targetFiles) {
            System.out.println("Received Target File: " + file.getOriginalFilename());
        }

        return new ResponseEntity<>("Physical statement files uploaded successfully", HttpStatus.CREATED);
    }
}
