package com.impacto.irecon.command.rulemaintenance.service.impl;

import com.impacto.irecon.command.rulemaintenance.dto.RuleSummaryResponse;
import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
import com.impacto.irecon.command.rulemaintenance.model.RuleMaintenanceRequest;
import com.impacto.irecon.command.rulemaintenance.repository.RuleMaintenanceRepository;
import com.impacto.irecon.command.rulemaintenance.service.RuleMaintenanceService;
import com.impacto.irecon.common.exception.RuleNotFoundException;
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
public class RuleMaintenanceServiceImpl implements RuleMaintenanceService {

    private final RuleMaintenanceRepository ruleMaintenanceRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional(readOnly = true)
    public List<RuleSummaryResponse> getAllRules() {
        return ruleMaintenanceRepository.findAll().stream()
                .map(rule -> modelMapper.map(rule, RuleSummaryResponse.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public RuleMaintenance getRuleById(UUID id) {
        return ruleMaintenanceRepository.findById(id)
                .orElseThrow(() -> new RuleNotFoundException("Rule not found with id: " + id));
    }

    @Override
    public RuleSummaryResponse createRule(RuleMaintenanceRequest request) {
        RuleMaintenance ruleMaintenance = modelMapper.map(request,RuleMaintenance.class);

        RuleMaintenance maintenance = ruleMaintenanceRepository.save(ruleMaintenance);
        return modelMapper.map(maintenance,RuleSummaryResponse.class);
    }

    @Override
    public RuleSummaryResponse updateRule(UUID id, RuleMaintenanceRequest ruleDetails) {
        RuleMaintenance existingRule = getRuleById(id);

        modelMapper.map(ruleDetails, existingRule);

        existingRule.setLastModifiedBy("Default Name");

        RuleMaintenance updatedRule = ruleMaintenanceRepository.save(existingRule);

        return modelMapper.map(updatedRule, RuleSummaryResponse.class);
    }


    @Override
    public void deleteRule(UUID id) {
        RuleMaintenance rule = getRuleById(id);
        ruleMaintenanceRepository.delete(rule);
    }
}
