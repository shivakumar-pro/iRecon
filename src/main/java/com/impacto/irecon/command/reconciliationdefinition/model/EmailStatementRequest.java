package com.impacto.irecon.command.reconciliationdefinition.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailStatementRequest extends CreateReconciliationRequest {
    @Valid
    private EmailConfig sourceConfig;

    @Valid
    private EmailConfig targetConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailConfig {
        @NotBlank(message = "Email username is required")
        private String emailUsername;

        @NotBlank(message = "Output directory is required")
        private String outputDirectory;

        @NotBlank(message = "Config file path is required")
        private String configFilePath;

        @NotBlank(message = "Mailbox options are required")
        private String mailboxOptions;

        private String scenario;
        private String timeInterval;
    }
}
