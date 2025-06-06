package com.impacto.irecon.command.reconciliationdefinition.model;

import com.impacto.irecon.common.enums.ReconciliationConstants;
import com.impacto.irecon.common.enums.TimeFrequency;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;

@Data
public class ReconciliationPreferencesRequest {
    private TimeFrequency frequency;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    @NotNull(message = "Reconciliation time is required")
    private Time reconciliationTime;


    private ReconciliationConstants.ExternalBalanceAction openingBalanceAction;

    private ReconciliationConstants.ExternalBalanceAction closingBalanceAction;

    private Boolean passValueDateMismatch;
}
