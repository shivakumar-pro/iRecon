package com.impacto.irecon.command.rulemaintenance.model;

import com.impacto.irecon.common.enums.RuleConstants.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleMaintenanceRequest {

    @NotBlank(message = "Rule name cannot be blank")
    @Size(min = 3, max = 50, message = "Rule name must be between 3 and 50 characters")
    private String ruleName;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Reconciliation type must be specified")
    private ReconType reconType;

    @NotNull(message = "Transaction type must be specified")
    private TransactionType transactionType;

    @NotNull(message = "Match type must be specified")
    private MatchType matchType;

    @NotNull(message = "Value date type must be specified")
    private ValueDate valueDateType;

    @NotNull(message = "Match amount type must be specified")
    private Match matchAmountType;

    @NotNull(message = "Amount type must be specified")
    private Amount amountType;


    private AmountToleranceSettingsRequest automaticToleranceSettings;

    private AmountToleranceSettingsRequest manualToleranceSettings;

    // Reference number matching
    @Valid
    @NotNull
    private ReferenceNumberMatchRequest referenceNumberMatch;
}
