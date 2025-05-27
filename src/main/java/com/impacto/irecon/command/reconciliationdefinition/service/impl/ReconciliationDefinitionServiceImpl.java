package com.impacto.irecon.command.reconciliationdefinition.service.impl;

import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.entity.ReconciliationDefinition;
import com.impacto.irecon.command.reconciliationdefinition.model.ApiBasedRequest;
import com.impacto.irecon.command.reconciliationdefinition.repository.ReconciliationDefinitionRepository;
import com.impacto.irecon.command.reconciliationdefinition.service.ReconciliationDefinitionService;
import com.impacto.irecon.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReconciliationDefinitionServiceImpl implements ReconciliationDefinitionService {

    private final ReconciliationDefinitionRepository reconciliationDefinitionRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReconciliationDefinitionResponse createApiBasedReconciliation(ApiBasedRequest request) {
        ReconciliationDefinition reconciliationDefinition = mapRequestToEntity(request);
        ReconciliationDefinition savedDefinition = reconciliationDefinitionRepository.save(reconciliationDefinition);
        return mapEntityToResponse(savedDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public ReconciliationDefinitionResponse getReconciliationById(UUID id) {
        ReconciliationDefinition definition = reconciliationDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reconciliation definition not found with id: " + id));
        return mapEntityToResponse(definition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReconciliationDefinitionResponse> getAllReconciliations() {
        return reconciliationDefinitionRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReconciliationDefinitionResponse updateReconciliation(UUID id, ApiBasedRequest request) {
        ReconciliationDefinition existingDefinition = reconciliationDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reconciliation definition not found with id: " + id));
        
        updateEntityFromRequest(existingDefinition, request);
        ReconciliationDefinition updatedDefinition = reconciliationDefinitionRepository.save(existingDefinition);
        return mapEntityToResponse(updatedDefinition);
    }

    @Override
    public void deleteReconciliation(UUID id) {
        if (!reconciliationDefinitionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reconciliation definition not found with id: " + id);
        }
        reconciliationDefinitionRepository.deleteById(id);
    }

    private ReconciliationDefinition mapRequestToEntity(ApiBasedRequest request) {
        ReconciliationDefinition definition = new ReconciliationDefinition();
        definition.setDescription(request.getReconciliationDescription());
        
        // Map source data
        definition.setSourceApiKey(request.getSourceData().getApiKey());
        definition.setSourceApiSecret(request.getSourceData().getApiSecret());
        definition.setSourceApiUrl(request.getSourceData().getApiUrl());
        
        // Map target data
        definition.setTargetApiKey(request.getTargetData().getApiKey());
        definition.setTargetApiSecret(request.getTargetData().getApiSecret());
        definition.setTargetApiUrl(request.getTargetData().getApiUrl());
        
        // Map sync configuration
        definition.setLastSynced(request.getSourceData().getSyncConfiguration().getLastSynced());
        definition.setSyncType(request.getSourceData().getSyncConfiguration().getSyncType());
        definition.setFrequency(request.getSourceData().getSyncConfiguration().getFrequency());
        definition.setSyncStartTime(request.getSourceData().getSyncConfiguration().getStartTime());
        
        return definition;
    }

    private void updateEntityFromRequest(ReconciliationDefinition definition, ApiBasedRequest request) {
        definition.setDescription(request.getReconciliationDescription());
        
        // Update source data
        definition.setSourceApiKey(request.getSourceData().getApiKey());
        definition.setSourceApiSecret(request.getSourceData().getApiSecret());
        definition.setSourceApiUrl(request.getSourceData().getApiUrl());
        
        // Update target data
        definition.setTargetApiKey(request.getTargetData().getApiKey());
        definition.setTargetApiSecret(request.getTargetData().getApiSecret());
        definition.setTargetApiUrl(request.getTargetData().getApiUrl());
        
        // Update sync configuration
        definition.setLastSynced(request.getSourceData().getSyncConfiguration().getLastSynced());
        definition.setSyncType(request.getSourceData().getSyncConfiguration().getSyncType());
        definition.setFrequency(request.getSourceData().getSyncConfiguration().getFrequency());
        definition.setSyncStartTime(request.getSourceData().getSyncConfiguration().getStartTime());
    }

    private ReconciliationDefinitionResponse mapEntityToResponse(ReconciliationDefinition entity) {
        return modelMapper.map(entity, ReconciliationDefinitionResponse.class);
    }
}
