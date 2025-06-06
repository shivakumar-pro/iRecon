package com.impacto.irecon.command.reconciliationdefinition.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwiftStatementRequest extends CreateReconciliationRequest {
    @Valid
    private SwiftConfig sourceConfig;

    @Valid
    private SwiftConfig targetConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SwiftConfig {
        @NotBlank(message = "BIC code is required")
        private String bicCode;

        @NotBlank(message = "Account number is required")
        private String accountNumber;

        @NotBlank(message = "Message type is required")
        private String messageType;

        private String messageFormat;
        private String messageVersion;
        private String securityCredentials;
    }
} 