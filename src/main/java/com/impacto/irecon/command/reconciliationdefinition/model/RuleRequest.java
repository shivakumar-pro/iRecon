package com.impacto.irecon.command.reconciliationdefinition.model;

import com.impacto.irecon.common.enums.DataSourceType;
import com.impacto.irecon.common.enums.RuleType;
import lombok.Data;

@Data
public class RuleRequest {

    private String ruleId;
    private String ruleDescription;
    private RuleType ruleType;
}
