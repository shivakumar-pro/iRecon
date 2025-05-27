package com.impacto.irecon.command.rulemaintenance.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleMaintenanceRequest {

    private String ruleName;
    private String description;


    private String reconType;
    private String transactionType;

    private String matchType;
    private String valueDateType;
    private String matchAmountType;
    private String amountType;

    // Automatic tolerance
    private String autoCurrency;
    private Double autoPositiveTolerancePercent;
    private Double autoNegativeTolerancePercent;
    private Double autoPositiveToleranceAmount;
    private Double autoNegativeToleranceAmount;
    private Integer autoValueDatePlus;
    private Integer autoValueDateMinus;

    // Manual tolerance
    private String manualCurrency;
    private Double manualPositiveTolerancePercent;
    private Double manualNegativeTolerancePercent;
    private Double manualPositiveToleranceAmount;
    private Double manualNegativeToleranceAmount;
    private Integer manualValueDatePlus;
    private Integer manualValueDateMinus;

    // Reference number matching
    private ReferenceNumberMatchRequest referenceNumberMatchRequest;
}
