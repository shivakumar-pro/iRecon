package com.impacto.irecon.command.reconciliationdefinition.model;

import com.impacto.irecon.common.enums.ReconciliationConstants.ReconciliationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ReconciliationRunRequest {
    @NotBlank(message = "Reconciliation ID cannot be blank")
    @Size(min = 3, max = 50, message = "Reconciliation ID must be between 3 and 50 characters")
    private String reconciliationId;

    @Size(max = 255, message = "Reconciliation description cannot exceed 255 characters")
    private String reconciliationDescription;

    @NotNull(message = "Run start date is required")
    private LocalDate runStartDate;

    @NotNull(message = "Run end date is required")
    private LocalDate runEndDate;

    private List<RuleRequest> rules;

    private ReconciliationStatus status;

    private Boolean matchedWithToleranceAmount;
    private Boolean matchedManuallyAmount;
    private Boolean matchedWithToleranceValueDate;
    private Boolean matchedManuallyValueDate;

    private LocalDate filterStartDate;
    private LocalDate filterEndDate;

    private BigDecimal minAmount;
    private BigDecimal maxAmount;
}
