package com.impacto.irecon.command.rulemaintenance.service.impl;

import com.impacto.irecon.command.rulemaintenance.dto.RuleSummaryResponse;
import com.impacto.irecon.command.rulemaintenance.entity.AmountToleranceSettings;
import com.impacto.irecon.command.rulemaintenance.entity.ReferenceNumberMatchEntity;
import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
import com.impacto.irecon.command.rulemaintenance.model.RuleMaintenanceRequest;
import com.impacto.irecon.command.rulemaintenance.repository.RuleMaintenanceRepository;
import com.impacto.irecon.command.rulemaintenance.service.RuleMaintenanceService;
import com.impacto.irecon.common.enums.HttpResponseCode;
import com.impacto.irecon.common.exception.ResourceAlreadyExistException;
import com.impacto.irecon.common.exception.ResourceNotFoundException;
import com.impacto.irecon.common.response.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RuleMaintenanceServiceImpl implements RuleMaintenanceService {

    private final RuleMaintenanceRepository ruleMaintenanceRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<RuleSummaryResponse> getAllRules(Pageable pageable) {
        Page<RuleMaintenance> rulePage = ruleMaintenanceRepository.findAll(pageable);
        List<RuleSummaryResponse> rules = rulePage.getContent().stream()
                .map(rule -> modelMapper.map(rule, RuleSummaryResponse.class))
                .collect(Collectors.toList());

        return PaginationResponse.<RuleSummaryResponse>builder()
                .content(rules)
                .pageNumber(rulePage.getNumber())
                .pageSize(rulePage.getSize())
                .totalElements(rulePage.getTotalElements())
                .totalPages(rulePage.getTotalPages())
                .lastPage(rulePage.isLast())
                .firstPage(rulePage.isFirst())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<RuleSummaryResponse> searchRules(String ruleName, Pageable pageable) {
        Page<RuleMaintenance> rulePage = ruleMaintenanceRepository.findByRuleNameContainingIgnoreCase(ruleName, pageable);
        List<RuleSummaryResponse> rules = rulePage.getContent().stream()
                .map(rule -> modelMapper.map(rule, RuleSummaryResponse.class))
                .collect(Collectors.toList());

        return PaginationResponse.<RuleSummaryResponse>builder()
                .content(rules)
                .pageNumber(rulePage.getNumber())
                .pageSize(rulePage.getSize())
                .totalElements(rulePage.getTotalElements())
                .totalPages(rulePage.getTotalPages())
                .lastPage(rulePage.isLast())
                .firstPage(rulePage.isFirst())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public RuleMaintenance getRuleById(String id) {
        return ruleMaintenanceRepository.findByRuleId(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpResponseCode.RULE_NOT_FOUND,"Rule not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public RuleMaintenance getRuleByName(String ruleName) {
        return ruleMaintenanceRepository.findByRuleName(ruleName)
                .orElseThrow(() -> new ResourceNotFoundException(HttpResponseCode.RULE_NOT_FOUND,
                        String.format("Rule not found with name: '%s' (case-insensitive search)", ruleName)));
    }

    @Override
    public RuleSummaryResponse createRule(RuleMaintenanceRequest request) {
        if (ruleMaintenanceRepository.findByRuleName(request.getRuleName()).isPresent()) {
            throw new ResourceAlreadyExistException(HttpResponseCode.RULE_ALREADY_EXISTS,
                    String.format("Rule with name '%s' already exists", request.getRuleName())
            );
        }

        RuleMaintenance ruleMaintenance = modelMapper.map(request, RuleMaintenance.class);
        ruleMaintenance.setCreatedBy("System");

        // Explicitly map and set child entities to ensure they're not transient
        if (request.getAutomaticToleranceSettings() != null) {
            AmountToleranceSettings autoTol = modelMapper.map(request.getAutomaticToleranceSettings(), AmountToleranceSettings.class);
            ruleMaintenance.setAutomaticToleranceSettings(autoTol);
        }

        if (request.getManualToleranceSettings() != null) {
            AmountToleranceSettings manualTol = modelMapper.map(request.getManualToleranceSettings(), AmountToleranceSettings.class);
            ruleMaintenance.setManualToleranceSettings(manualTol);
        }

        if (request.getReferenceNumberMatch() != null) {
            ReferenceNumberMatchEntity refMatch = modelMapper.map(request.getReferenceNumberMatch(), ReferenceNumberMatchEntity.class);
            ruleMaintenance.setReferenceNumberMatch(refMatch);
        }

        RuleMaintenance savedRule = ruleMaintenanceRepository.save(ruleMaintenance);
        return modelMapper.map(savedRule, RuleSummaryResponse.class);
    }


    @Override
    public RuleSummaryResponse updateRule(String ruleId, RuleMaintenanceRequest ruleDetails) {
        RuleMaintenance existingRule = getRuleById(ruleId);

        // Check if new rule name conflicts with existing rule (excluding current rule)
        if (!existingRule.getRuleName().equals(ruleDetails.getRuleName())) {
            ruleMaintenanceRepository.findByRuleName(ruleDetails.getRuleName())
                    .ifPresent(rule -> {
                        throw new IllegalArgumentException("Rule with name " + ruleDetails.getRuleName() + " already exists");
                    });
        }

        // Update basic fields
        existingRule.setRuleName(ruleDetails.getRuleName());
        existingRule.setDescription(ruleDetails.getDescription());
        existingRule.setReconType(ruleDetails.getReconType());
        existingRule.setTransactionType(ruleDetails.getTransactionType());
        existingRule.setMatchType(ruleDetails.getMatchType());
        existingRule.setValueDateType(ruleDetails.getValueDateType());
        existingRule.setMatchAmountType(ruleDetails.getMatchAmountType());
        existingRule.setAmountType(ruleDetails.getAmountType());
        existingRule.setLastModifiedBy("System"); // TODO: Replace with actual user from security context

        // Update automatic tolerance settings
        if (ruleDetails.getAutomaticToleranceSettings() != null) {
            if (existingRule.getAutomaticToleranceSettings() == null) {
                existingRule.setAutomaticToleranceSettings(new AmountToleranceSettings());
            }
            modelMapper.map(ruleDetails.getAutomaticToleranceSettings(), existingRule.getAutomaticToleranceSettings());
        }

        // Update manual tolerance settings
        if (ruleDetails.getManualToleranceSettings() != null) {
            if (existingRule.getManualToleranceSettings() == null) {
                existingRule.setManualToleranceSettings(new AmountToleranceSettings());
            }
            modelMapper.map(ruleDetails.getManualToleranceSettings(), existingRule.getManualToleranceSettings());
        }

        // Update reference number match
        if (ruleDetails.getReferenceNumberMatch() != null) {
            if (existingRule.getReferenceNumberMatch() == null) {
                existingRule.setReferenceNumberMatch(new ReferenceNumberMatchEntity());
            }
            modelMapper.map(ruleDetails.getReferenceNumberMatch(), existingRule.getReferenceNumberMatch());
        }

        RuleMaintenance updatedRule = ruleMaintenanceRepository.save(existingRule);
        return modelMapper.map(updatedRule, RuleSummaryResponse.class);
    }

    @Override
    public void deleteRule(String id) {
        RuleMaintenance rule = getRuleById(id);
        ruleMaintenanceRepository.delete(rule);
    }
}
