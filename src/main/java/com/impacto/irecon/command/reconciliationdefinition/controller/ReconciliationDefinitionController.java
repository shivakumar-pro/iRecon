package com.impacto.irecon.command.reconciliationdefinition.controller;

import com.impacto.irecon.command.reconciliationdefinition.dto.ComparisonResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.FieldNamesResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationRunResponse;
import com.impacto.irecon.command.reconciliationdefinition.model.*;
import com.impacto.irecon.command.reconciliationdefinition.service.ReconciliationDefinitionService;
import com.impacto.irecon.command.reconciliationdefinition.service.impl.DataComparisonService;
import com.impacto.irecon.common.enums.HttpResponseCode;
import com.impacto.irecon.common.enums.ReconciliationType;
import com.impacto.irecon.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reconciliation")
@RequiredArgsConstructor
@Tag(name = "Reconciliation Definition API", description = "Common operations for managing reconciliation definitions")
public class ReconciliationDefinitionController {

    private final ReconciliationDefinitionService reconciliationDefinitionService;
    private final DataComparisonService comparisonService;


    @GetMapping("/{id}")
    @Operation(
            summary = "Get reconciliation definition by ID",
            description = "Retrieves a reconciliation definition by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation definition retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<ReconciliationDefinitionResponse> getReconciliationById(@PathVariable Long id) {
        return ResponseEntity.ok(reconciliationDefinitionService.getReconciliationById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all reconciliation definitions",
            description = "Retrieves all reconciliation definitions with optional type filter and pagination support. " +
                    "Use page and size parameters for pagination, and sort for sorting.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reconciliation definitions retrieved successfully")
            }
    )
    public ResponseEntity<Page<ReconciliationDefinitionResponse>> getAllReconciliations(
            @RequestParam(required = false) ReconciliationType type,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        if (type != null) {
            return ResponseEntity.ok(reconciliationDefinitionService.getReconciliationsByType(type, pageable));
        }
        return ResponseEntity.ok(reconciliationDefinitionService.getAllReconciliations(pageable));
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete reconciliation definition",
            description = "Permanently deletes a reconciliation definition by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Reconciliation definition deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Reconciliation definition not found")
            }
    )
    public ResponseEntity<Void> deleteReconciliation(@PathVariable Long id) {
        reconciliationDefinitionService.deleteReconciliation(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/compare")
    public ResponseEntity<ComparisonResponse> compareData(@RequestBody @Validated ComparisonRequest request) {
        try {
            ComparisonResponse response = comparisonService.compareData(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Data Comparison API is running");
    }


    @PostMapping("/fields")
    @Operation(
            summary = "Get field names from source and target data",
            description = "Extracts and returns field names from provided source and target data lists",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Field names retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    public ResponseEntity<FieldNamesResponse> getFieldNames(@RequestBody Map<String, List<Map<String, Object>>> requestData) {
        try {
            List<Map<String, Object>> sourceData = requestData.get("sourceData");
        List<Map<String, Object>> targetData = requestData.get("targetData");
            FieldNamesResponse response = reconciliationDefinitionService.extractFieldNames(
                    sourceData,
                    targetData
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
