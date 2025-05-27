package com.impacto.irecon.command.reconciliationdefinition.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class EmailStatementRequest extends CreateReconciliationRequest{

    private EmailData targetData;
    private EmailData sourceData;

    @Data
    public static class EmailData {
        @NotBlank
        private String emailUserNameId;

        @NotBlank
        private String outputDirectory;

        @NotBlank
        private String configFilePath;

        @NotBlank
        private String mailboxOptions;

        @NotBlank
        private String scenario;

        @NotBlank
        private String timeInterval;
    }
}
