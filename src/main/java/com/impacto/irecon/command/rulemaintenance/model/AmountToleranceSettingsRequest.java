package com.impacto.irecon.command.rulemaintenance.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AmountToleranceSettingsRequest {

    private String currency;

    @DecimalMin(value = "0.0", inclusive = true, message = "Positive tolerance percent must be non-negative")
    private Double positiveTolerancePercent;

    @DecimalMin(value = "0.0", inclusive = true, message = "Negative tolerance percent must be non-negative")
    private Double negativeTolerancePercent;

    @DecimalMin(value = "0.0", inclusive = true, message = "Positive tolerance amount must be non-negative")
    private Double positiveToleranceAmount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Negative tolerance amount must be non-negative")
    private Double negativeToleranceAmount;

    @Min(value = 0, message = "Value date plus must be 0 or greater")
    private Integer valueDatePlus;

    @Min(value = 0, message = "Value date minus must be 0 or greater")
    private Integer valueDateMinus;

}