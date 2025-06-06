package com.impacto.irecon.command.reconciliationdefinition.service;

import com.impacto.irecon.command.reconciliationdefinition.dto.FieldNamesResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationRunResponse;
import com.impacto.irecon.command.reconciliationdefinition.model.*;
import com.impacto.irecon.common.enums.ReconciliationType;
import com.impacto.irecon.common.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ReconciliationDefinitionService {
    // Create operations
    ReconciliationDefinitionResponse createApiBasedReconciliation(CreateApiBasedRequest request);
    ReconciliationDefinitionResponse processApiBasedReconciliation(ApiBasedRequest request);
    ReconciliationDefinitionResponse createEmailStatementReconciliation(CreateEmailStatementRequest request);
    ReconciliationDefinitionResponse processEmailStatementReconciliation(EmailStatementRequest request);
    ReconciliationDefinitionResponse createPhysicalStatementReconciliation(PhysicalStatementRequest request, MultipartFile[] sourceFiles, MultipartFile[] targetFiles);
    ReconciliationDefinitionResponse createSwiftStatementReconciliation(SwiftStatementRequest request);

    // Update operations
    ReconciliationDefinitionResponse updateApiBasedReconciliation(Long id, ApiBasedRequest request);
    ReconciliationDefinitionResponse updateApiBasedConfig(Long id, ApiBasedRequest request);
    ReconciliationDefinitionResponse updateEmailStatementReconciliation(Long id, EmailStatementRequest request);
    ReconciliationDefinitionResponse updateEmailStatementConfig(Long id, EmailStatementRequest request);
    ReconciliationDefinitionResponse updatePhysicalStatementReconciliation(Long id, PhysicalStatementRequest request, MultipartFile[] sourceFiles, MultipartFile[] targetFiles);
    ReconciliationDefinitionResponse updatePhysicalStatementConfig(Long id, PhysicalStatementRequest request);


    // Read operations
    ReconciliationDefinitionResponse getReconciliationById(Long id);
    
    // Paginated read operations
    Page<ReconciliationDefinitionResponse> getAllReconciliations(Pageable pageable);
    Page<ReconciliationDefinitionResponse> getReconciliationsByType(ReconciliationType type, Pageable pageable);
    
    // Legacy read operations (for backward compatibility)
    List<ReconciliationDefinitionResponse> getAllReconciliations();
    List<ReconciliationDefinitionResponse> getReconciliationsByType(ReconciliationType type);

    // Delete operations
    void deleteReconciliation(Long id);

    // Process operations
    void processReconciliation(Long id);
    void fetchDataFromSource(Long id);
    void fetchDataFromTarget(Long id);
    void compareData(Long id);
    ReconciliationRunResponse runReconciliation(ReconciliationRunRequest request);

    FieldNamesResponse extractFieldNames(List<Map<String, Object>> sourceData, List<Map<String, Object>> targetData);
}
