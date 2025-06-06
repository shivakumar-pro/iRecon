package com.impacto.irecon.command.reconciliationdefinition.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impacto.irecon.command.reconciliationdefinition.dto.FieldNamesResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationDefinitionResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ReconciliationRunResponse;
import com.impacto.irecon.command.reconciliationdefinition.entity.*;
import com.impacto.irecon.command.reconciliationdefinition.model.*;
import com.impacto.irecon.command.reconciliationdefinition.repository.ReconciliationDefinitionRepository;
import com.impacto.irecon.command.reconciliationdefinition.service.ReconciliationDefinitionService;
import com.impacto.irecon.command.rulemaintenance.repository.RuleMaintenanceRepository;
import com.impacto.irecon.common.enums.HttpResponseCode;
import com.impacto.irecon.common.enums.ReconciliationConstants;
import com.impacto.irecon.common.enums.ReconciliationType;
import com.impacto.irecon.common.enums.Status;
import com.impacto.irecon.common.exception.ResourceAlreadyExistException;
import com.impacto.irecon.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReconciliationDefinitionServiceImpl implements ReconciliationDefinitionService {

    private final ReconciliationDefinitionRepository reconciliationDefinitionRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final RuleMaintenanceRepository ruleRepository;

    @Override
    @Transactional
    public ReconciliationDefinitionResponse createApiBasedReconciliation(CreateApiBasedRequest request) {
        validateReconciliationId(request.getReconciliationId());

        ReconciliationDefinition reconciliation = new ReconciliationDefinition();
        setBaseFields(reconciliation, request);
        reconciliation.setReconciliationType(ReconciliationType.API_BASED);
        
        try {
            reconciliation.setReconciliationConfigJson(objectMapper.writeValueAsString(request.getApiBased()));
        } catch (JsonProcessingException e) {
            log.error("Error converting API configuration to JSON", e);
            throw new RuntimeException("Error processing API configuration", e);
        }

        ReconciliationDefinition saved = reconciliationDefinitionRepository.save(reconciliation);
        return modelMapper.map(saved, ReconciliationDefinitionResponse.class);
    }

    @Override
    @Transactional
    public ReconciliationRunResponse runReconciliation(ReconciliationRunRequest request) {
        // Validate date ranges
        if (request.getRunEndDate().isBefore(request.getRunStartDate())) {
            throw new IllegalArgumentException("Run end date cannot be before run start date");
        }

        if (request.getFilterEndDate() != null && request.getFilterStartDate() != null 
            && request.getFilterEndDate().isBefore(request.getFilterStartDate())) {
            throw new IllegalArgumentException("Filter end date cannot be before filter start date");
        }

        // Find the reconciliation definition
        ReconciliationDefinition reconciliationDefinition = reconciliationDefinitionRepository
            .findByReconciliationId(request.getReconciliationId())
            .orElseThrow(() -> new ResourceNotFoundException(HttpResponseCode.NOT_FOUND,
                "Reconciliation not found with ID: " + request.getReconciliationId()));

        // Validate rules if provided
        List<RuleRequest> rules = request.getRules();
        if (rules != null && !rules.isEmpty()) {
            List<String> notFoundRules = rules.stream()
                .filter(rule -> !ruleRepository.existsByRuleName(rule.getRuleId()))
                .map(RuleRequest::getRuleId)
                .collect(Collectors.toList());

            if (!notFoundRules.isEmpty()) {
                throw new ResourceNotFoundException(HttpResponseCode.NOT_FOUND, 
                    "The following rules were not found: " + String.join(", ", notFoundRules));
            }
        }

        // Map request to entity
        ReconciliationRun run = modelMapper.map(request, ReconciliationRun.class);
        run.setCreatedAt(LocalDateTime.now());
        run.setStatus(request.getStatus() != null ? request.getStatus() : 
                     ReconciliationConstants.ReconciliationStatus.UN_RECONCILED);

        // Set bidirectional relationship
        run.setReconciliationDefinition(reconciliationDefinition);
        reconciliationDefinition.setReconciliationRun(run);

        // Save the updated reconciliation definition (will cascade save the run)
        ReconciliationDefinition saved = reconciliationDefinitionRepository.save(reconciliationDefinition);


        // Map entity to response
        return modelMapper.map(saved.getReconciliationRun(), ReconciliationRunResponse.class);
    }

    @Override
    @Transactional
    public ReconciliationDefinitionResponse processApiBasedReconciliation(ApiBasedRequest request) {
        validateReconciliationId(request.getReconciliationId());

        ReconciliationDefinition reconciliation = new ReconciliationDefinition();
        setBaseFields(reconciliation, request);
        reconciliation.setReconciliationType(ReconciliationType.API_BASED);
        
        try {
            reconciliation.setReconciliationConfigJson(objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error("Error converting API configuration to JSON", e);
            throw new RuntimeException("Error processing API configuration", e);
        }

        processReconciliation(reconciliation);
        ReconciliationDefinition saved = reconciliationDefinitionRepository.save(reconciliation);
        return modelMapper.map(saved, ReconciliationDefinitionResponse.class);
    }

    @Override
    @Transactional
    public ReconciliationDefinitionResponse createEmailStatementReconciliation(CreateEmailStatementRequest request) {
        validateReconciliationId(request.getReconciliationId());

        ReconciliationDefinition reconciliation = new ReconciliationDefinition();
        setBaseFields(reconciliation, request);
        reconciliation.setReconciliationType(ReconciliationType.EMAIL_STATEMENT);
        
        try {
            reconciliation.setReconciliationConfigJson(objectMapper.writeValueAsString(request.getEmailStatement()));
        } catch (JsonProcessingException e) {
            log.error("Error converting Email configuration to JSON", e);
            throw new RuntimeException("Error processing Email configuration", e);
        }

        ReconciliationDefinition saved = reconciliationDefinitionRepository.save(reconciliation);
        return modelMapper.map(saved, ReconciliationDefinitionResponse.class);
    }

    @Override
    @Transactional
    public ReconciliationDefinitionResponse processEmailStatementReconciliation(EmailStatementRequest request) {
        // Check if reconciliation ID already exists
        if (reconciliationDefinitionRepository.existsByReconciliationId(request.getReconciliationId())) {
            throw new ResourceAlreadyExistException(HttpResponseCode.NOT_FOUND,"Reconciliation with ID '" + request.getReconciliationId() + "' already exists");
        }

        ReconciliationDefinition reconciliation = new ReconciliationDefinition();
        reconciliation.setReconciliationId(request.getReconciliationId());
        reconciliation.setDescription(request.getReconciliationDescription());
        reconciliation.setReconciliationType(ReconciliationType.EMAIL_STATEMENT);

        // Set preferences from base request
        setReconciliationPreferences(reconciliation, request.getReconciliationPreferences());

        // Convert Email configuration to JSON string
        try {
            String configJson = objectMapper.writeValueAsString(request);
            reconciliation.setReconciliationConfigJson(configJson);
        } catch (JsonProcessingException e) {
            log.error("Error converting Email configuration to JSON", e);
            throw new RuntimeException("Error processing Email configuration", e);
        }

        // Process the reconciliation
        processReconciliation(reconciliation);

        ReconciliationDefinition saved = reconciliationDefinitionRepository.save(reconciliation);
        return modelMapper.map(saved, ReconciliationDefinitionResponse.class);
    }

    @Override
    public ReconciliationDefinitionResponse createPhysicalStatementReconciliation(PhysicalStatementRequest request, MultipartFile[] sourceFiles, MultipartFile[] targetFiles) {
        return null;
    }




    @Override
    public ReconciliationDefinitionResponse createSwiftStatementReconciliation(SwiftStatementRequest request) {
        return null;
    }

    @Override
    @Transactional
    public ReconciliationDefinitionResponse updateApiBasedReconciliation(Long id, ApiBasedRequest request) {
        ReconciliationDefinition existing = findReconciliationById(id);
        validateReconciliationType(existing, ReconciliationType.API_BASED);

        updateBaseFields(existing, request);
        try {
            existing.setReconciliationConfigJson(objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error("Error converting API configuration to JSON", e);
            throw new RuntimeException("Error processing API configuration", e);
        }

        ReconciliationDefinition updated = reconciliationDefinitionRepository.save(existing);
        return modelMapper.map(updated, ReconciliationDefinitionResponse.class);
    }

    @Override
    @Transactional
    public ReconciliationDefinitionResponse updateApiBasedConfig(Long id, ApiBasedRequest request) {
        ReconciliationDefinition existing = findReconciliationById(id);
        validateReconciliationType(existing, ReconciliationType.API_BASED);

        try {
            existing.setReconciliationConfigJson(objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error("Error converting API configuration to JSON", e);
            throw new RuntimeException("Error processing API configuration", e);
        }
        existing.setLastModifiedOn(LocalDateTime.now());

        ReconciliationDefinition updated = reconciliationDefinitionRepository.save(existing);
        return modelMapper.map(updated, ReconciliationDefinitionResponse.class);
    }

    @Override
    public ReconciliationDefinitionResponse updateEmailStatementReconciliation(Long id, EmailStatementRequest request) {
        return null;
    }

    @Override
    public ReconciliationDefinitionResponse updateEmailStatementConfig(Long id, EmailStatementRequest request) {
        return null;
    }

    @Override
    public ReconciliationDefinitionResponse updatePhysicalStatementReconciliation(Long id, PhysicalStatementRequest request, MultipartFile[] sourceFiles, MultipartFile[] targetFiles) {
        return null;
    }

    @Override
    public ReconciliationDefinitionResponse updatePhysicalStatementConfig(Long id, PhysicalStatementRequest request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ReconciliationDefinitionResponse getReconciliationById(Long id) {
        ReconciliationDefinition reconciliation = findReconciliationById(id);
        return modelMapper.map(reconciliation, ReconciliationDefinitionResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReconciliationDefinitionResponse> getAllReconciliations(Pageable pageable) {
        // Get total count first
        long totalElements = reconciliationDefinitionRepository.count();
        
        // Calculate max pages (0-based)
        int maxPages = (int) Math.ceil((double) totalElements / pageable.getPageSize()) - 1;
        if (maxPages < 0) maxPages = 0;
        
        // Validate and adjust page number if needed
        Pageable validatedPageable = validateAndAdjustPageable(pageable, maxPages);
        
        return reconciliationDefinitionRepository.findAll(validatedPageable)
                .map(reconciliation -> modelMapper.map(reconciliation, ReconciliationDefinitionResponse.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReconciliationDefinitionResponse> getReconciliationsByType(ReconciliationType type, Pageable pageable) {
        // Get total count for the type first
        long totalElements = reconciliationDefinitionRepository.countByReconciliationType(type);
        
        // Calculate max pages (0-based)
        int maxPages = (int) Math.ceil((double) totalElements / pageable.getPageSize()) - 1;
        if (maxPages < 0) maxPages = 0;
        
        // Validate and adjust page number if needed
        Pageable validatedPageable = validateAndAdjustPageable(pageable, maxPages);
        
        return reconciliationDefinitionRepository.findByReconciliationType(type, validatedPageable)
                .map(reconciliation -> modelMapper.map(reconciliation, ReconciliationDefinitionResponse.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReconciliationDefinitionResponse> getAllReconciliations() {
        return reconciliationDefinitionRepository.findAll().stream()
                .map(reconciliation -> modelMapper.map(reconciliation, ReconciliationDefinitionResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReconciliationDefinitionResponse> getReconciliationsByType(ReconciliationType type) {
        return reconciliationDefinitionRepository.findByReconciliationType(type).stream()
                .map(reconciliation -> modelMapper.map(reconciliation, ReconciliationDefinitionResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReconciliation(Long id) {
        if (!reconciliationDefinitionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reconciliation not found with id: " + id);
        }
        reconciliationDefinitionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void processReconciliation(Long id) {
        ReconciliationDefinition reconciliation = reconciliationDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reconciliation not found with id: " + id));

        // TODO: Implement reconciliation processing logic
        // 1. Fetch data from source and target
        // 2. Compare data based on rules
        // 3. Generate match summary
        // 4. Update status
    }

    @Override
    @Transactional
    public void fetchDataFromSource(Long id) {
        // TODO: Implement source data fetching logic based on reconciliation type
    }

    @Override
    @Transactional
    public void fetchDataFromTarget(Long id) {
        // TODO: Implement target data fetching logic based on reconciliation type
    }

    @Override
    @Transactional
    public void compareData(Long id) {
        // TODO: Implement data comparison logic
        // 1. Load source and target data
        // 2. Apply field mappings
        // 3. Apply reconciliation rules
        // 4. Generate match summary
    }

    @Override
    public FieldNamesResponse extractFieldNames(List<Map<String, Object>> sourceData, List<Map<String, Object>> targetData) {
        if (sourceData == null || sourceData.isEmpty() || targetData == null || targetData.isEmpty()) {
            throw new IllegalArgumentException("Source and target data cannot be empty");
        }
        Set<String> sourceFields = new HashSet<>(sourceData.get(0).keySet());

        Set<String> targetFields = new HashSet<>(targetData.get(0).keySet());
        return FieldNamesResponse.builder()
                .sourceFields(sourceFields)
                .targetFields(targetFields)
                .build();
    }

    private ReconciliationDefinition findReconciliationById(Long id) {
        return reconciliationDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reconciliation not found with id: " + id));
    }

    private void validateReconciliationId(String reconciliationId) {
        if (reconciliationDefinitionRepository.existsByReconciliationId(reconciliationId)) {
            throw new ResourceAlreadyExistException(HttpResponseCode.CONFLICT,
                    "Reconciliation with ID '" + reconciliationId + "' already exists");
        }
    }

    private void validateReconciliationType(ReconciliationDefinition reconciliation, ReconciliationType expectedType) {
        if (reconciliation.getReconciliationType() != expectedType) {
            throw new IllegalArgumentException(
                    "Invalid reconciliation type. Expected: " + expectedType +
                            ", Found: " + reconciliation.getReconciliationType());
        }
    }

    private void setBaseFields(ReconciliationDefinition reconciliation, CreateReconciliationRequest request) {
        reconciliation.setReconciliationId(request.getReconciliationId());
        reconciliation.setDescription(request.getReconciliationDescription());
        reconciliation.setStatus(Status.ACTIVE);
        reconciliation.setCreatedOn(LocalDateTime.now());
        reconciliation.setLastModifiedOn(LocalDateTime.now());
        
        if (request.getReconciliationPreferences() != null) {
            ReconciliationPreferencesRequest prefs = request.getReconciliationPreferences();
            reconciliation.setFrequency(prefs.getFrequency());
            reconciliation.setStartDate(prefs.getStartDate());
            reconciliation.setEndDate(prefs.getEndDate());
            reconciliation.setReconciliationTime(prefs.getReconciliationTime().toLocalTime().atDate(prefs.getStartDate().toLocalDate()));
            reconciliation.setRejectStatement(prefs.getOpeningBalanceAction() == ReconciliationConstants.ExternalBalanceAction.REJECT_STATEMENT);
            reconciliation.setTreatDifferenceAsAdjustment(prefs.getOpeningBalanceAction() == ReconciliationConstants.ExternalBalanceAction.ADJUSTMENT_ENTRY);
            reconciliation.setPassValueDateMismatch(prefs.getPassValueDateMismatch());
        }
    }

    private void updateBaseFields(ReconciliationDefinition reconciliation, CreateReconciliationRequest request) {
        reconciliation.setDescription(request.getReconciliationDescription());
        reconciliation.setLastModifiedOn(LocalDateTime.now());
        
        if (request.getReconciliationPreferences() != null) {
            ReconciliationPreferencesRequest prefs = request.getReconciliationPreferences();
            reconciliation.setFrequency(prefs.getFrequency());
            reconciliation.setStartDate(prefs.getStartDate());
            reconciliation.setEndDate(prefs.getEndDate());
            reconciliation.setReconciliationTime(prefs.getReconciliationTime().toLocalTime().atDate(prefs.getStartDate().toLocalDate()));
            reconciliation.setRejectStatement(prefs.getOpeningBalanceAction() == ReconciliationConstants.ExternalBalanceAction.REJECT_STATEMENT);
            reconciliation.setTreatDifferenceAsAdjustment(prefs.getOpeningBalanceAction() == ReconciliationConstants.ExternalBalanceAction.ADJUSTMENT_ENTRY);
            reconciliation.setPassValueDateMismatch(prefs.getPassValueDateMismatch());
        }
    }

    private void setReconciliationPreferences(ReconciliationDefinition reconciliation, ReconciliationPreferencesRequest preferences) {
        if (preferences != null) {
            reconciliation.setFrequency(preferences.getFrequency());
            reconciliation.setStartDate(preferences.getStartDate());
            reconciliation.setEndDate(preferences.getEndDate());
            reconciliation.setReconciliationTime(preferences.getReconciliationTime().toLocalTime().atDate(preferences.getStartDate().toLocalDate()));
            reconciliation.setRejectStatement(preferences.getOpeningBalanceAction() == ReconciliationConstants.ExternalBalanceAction.REJECT_STATEMENT);
            reconciliation.setTreatDifferenceAsAdjustment(preferences.getOpeningBalanceAction() == ReconciliationConstants.ExternalBalanceAction.ADJUSTMENT_ENTRY);
            reconciliation.setPassValueDateMismatch(preferences.getPassValueDateMismatch());
        }
    }

    private void processReconciliation(ReconciliationDefinition reconciliation) {
        // TODO: Implement the actual reconciliation processing logic
        // 1. Extract configuration from JSON
        // 2. Fetch data from source and target systems
        // 3. Compare data based on rules
        // 4. Generate match summary
        // 5. Update status
        reconciliation.setStatus(Status.IN_PROGRESS);
        reconciliation.setLastSynced(LocalDateTime.now());
    }

    // Helper method to validate and adjust pageable
    private Pageable validateAndAdjustPageable(Pageable pageable, int maxPages) {
        int requestedPage = pageable.getPageNumber();
        
        // If requested page is greater than max pages, return last page
        if (requestedPage > maxPages) {
            return PageRequest.of(
                maxPages,
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "id"))
            );
        }
        
        // If no sort is specified, add default sort by id desc
        if (!pageable.getSort().isSorted()) {
            return PageRequest.of(
                requestedPage,
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
            );
        }
        
        return pageable;
    }
}
