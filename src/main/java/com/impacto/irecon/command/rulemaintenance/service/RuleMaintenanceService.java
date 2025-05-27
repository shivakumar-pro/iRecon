package com.impacto.irecon.command.rulemaintenance.service;


import com.impacto.irecon.command.rulemaintenance.dto.RuleSummaryResponse;
import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
import com.impacto.irecon.command.rulemaintenance.model.RuleMaintenanceRequest;

import java.util.List;
import java.util.UUID;

public interface RuleMaintenanceService {
    List<RuleSummaryResponse> getAllRules();
    RuleMaintenance getRuleById(UUID id);
    RuleSummaryResponse createRule(RuleMaintenanceRequest ruleMaintenance);
    RuleSummaryResponse updateRule(UUID id, RuleMaintenanceRequest ruleDetails);
    void deleteRule(UUID id);
}
