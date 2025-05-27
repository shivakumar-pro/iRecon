package com.impacto.irecon.command.reconciliationdefinition.model;


import lombok.Data;

import java.util.List;

@Data
public abstract class CreateReconciliationRequest {
    private String reconciliationId;
    private String reconciliationDescription;
    private List<RuleRequest> ruleRequests;

}
