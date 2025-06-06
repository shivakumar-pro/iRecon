package com.impacto.irecon.command.reconciliationdefinition.model;

import com.impacto.irecon.common.enums.DataSourceType;
import com.impacto.irecon.common.enums.RuleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RuleRequest {
    @NotBlank(message = "Rule ID cannot be blank")
    @Size(min = 3, max = 50, message = "Rule ID must be between 3 and 50 characters")
    private String ruleId;

    @NotNull(message = "Rule type must be specified")
    private RuleType executionType;
}
