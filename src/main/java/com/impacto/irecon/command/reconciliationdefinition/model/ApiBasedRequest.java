package com.impacto.irecon.command.reconciliationdefinition.model;

import com.impacto.irecon.common.enums.ReconciliationType;
import com.impacto.irecon.common.enums.TimeFrequency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiBasedRequest extends CreateReconciliationRequest {

    private ReconciliationType reconciliationType = ReconciliationType.API_BASED;

    @Valid
    private ApiConfig sourceConfig;

    @Valid
    private ApiConfig targetConfig;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiConfig {
        @NotBlank(message = "API Key is required")
        private String apiKey;

        @NotBlank(message = "API Secret is required")
        private String apiSecret;

        @NotBlank(message = "API URL is required")
        private String apiUrl;

        @Valid
        private SyncConfig syncConfig;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SyncConfig {
        private LocalDateTime lastSynced;
        private Boolean isManualSync;
        private Boolean isAutomatedSync;

        @Valid
        private AutomationSettings automationSettings;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AutomationSettings {
        @NotNull(message = "Frequency is required for automated sync")
        private TimeFrequency frequency;

        @NotNull(message = "Start time is required for automated sync")
        private LocalDateTime startTime;
    }

}
