package com.impacto.irecon.command.rulemaintenance.service;

import com.impacto.irecon.command.rulemaintenance.dto.RuleSummaryResponse;
import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
import com.impacto.irecon.command.rulemaintenance.model.RuleMaintenanceRequest;
import com.impacto.irecon.common.response.PaginationResponse;
import org.springframework.data.domain.Pageable;

public interface RuleMaintenanceService {
    PaginationResponse<RuleSummaryResponse> getAllRules(Pageable pageable);
    PaginationResponse<RuleSummaryResponse> searchRules(String ruleName, Pageable pageable);
    RuleMaintenance getRuleById(String ruleId);
    RuleMaintenance getRuleByName(String ruleName);
    RuleSummaryResponse createRule(RuleMaintenanceRequest ruleMaintenance);
    RuleSummaryResponse updateRule(String ruleId, RuleMaintenanceRequest ruleDetails);
    void deleteRule(String ruleId);
}
