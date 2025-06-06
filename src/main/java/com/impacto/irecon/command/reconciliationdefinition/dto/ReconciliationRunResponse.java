package com.impacto.irecon.command.reconciliationdefinition.dto;

import com.impacto.irecon.common.enums.ReconciliationConstants.ReconciliationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationRunResponse {
    private String reconciliationId;
    private String reconciliationDescription;
    private LocalDate runStartDate;
    private LocalDate runEndDate;
    private ReconciliationStatus status;
    private Boolean matchedWithToleranceAmount;
    private Boolean matchedManuallyAmount;
    private Boolean matchedWithToleranceValueDate;
    private Boolean matchedManuallyValueDate;
    private LocalDate filterStartDate;
    private LocalDate filterEndDate;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private LocalDateTime createdAt;
    private String createdBy;
} 