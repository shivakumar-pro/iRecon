package com.impacto.irecon.command.reconciliationdefinition.model;

import com.impacto.irecon.common.enums.DataSourceType;
import com.impacto.irecon.common.enums.ReconciliationConstants.ExternalBalanceAction;
import com.impacto.irecon.common.enums.TimeFrequency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.aspectj.apache.bcel.generic.ObjectType;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public abstract class CreateReconciliationRequest {

    @NotBlank(message = "Reconciliation ID cannot be blank")
    @Size(min = 3, max = 50, message = "Reconciliation ID must be between 3 and 50 characters")
    private String reconciliationId;

    @NotBlank(message = "Reconciliation description cannot be blank")
    @Size(max = 255, message = "Reconciliation description cannot exceed 255 characters")
    private String reconciliationDescription;

    private List<RuleRequest> rules;

    private DataSourceType dataSourceType;

   private ReconciliationPreferencesRequest reconciliationPreferences;

}
