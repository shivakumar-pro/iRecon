package com.impacto.irecon.command.rulemaintenance.dto;

import com.impacto.irecon.common.enums.RuleConstants.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleSummaryResponse {
    // private Long id;
    private String ruleName;
    private String ruleId;
//    private ReconType reconType;
//    private TransactionType transactionType;
//    private MatchType matchType;
//    private ValueDate valueDateType;
//    private Match matchAmountType;
//    private Amount amountType;
    private String createdBy;
    private LocalDateTime createdOn;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedOn;
}

