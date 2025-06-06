package com.impacto.irecon.command.reconciliationdefinition.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalStatementRequest extends CreateReconciliationRequest {
    @Valid
    private FileConfig sourceConfig;

    @Valid
    private FileConfig targetConfig;

    private Boolean ocrEnabled;
    private String ocrConfigPath;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileConfig {
        @NotBlank(message = "File type is required")
        private String fileType;

        @NotBlank(message = "File format is required")
        private String fileFormat;

        private String sheetName;

        private List<MultipartFile> file;
    }
}
