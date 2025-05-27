package com.impacto.irecon.command.reconciliationdefinition.service;

import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.model.ApiBasedRequest;

import java.util.List;
import java.util.UUID;

public interface ReconciliationDefinitionService {
    ReconciliationDefinitionResponse createApiBasedReconciliation(ApiBasedRequest request);
    ReconciliationDefinitionResponse getReconciliationById(UUID id);
    List<ReconciliationDefinitionResponse> getAllReconciliations();
    ReconciliationDefinitionResponse updateReconciliation(UUID id, ApiBasedRequest request);
    void deleteReconciliation(UUID id);
}
