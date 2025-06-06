package com.impacto.irecon.command.reconciliationdefinition.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComparisonResponse {
    @JsonProperty("comparisonId")
    private String comparisonId;

    @JsonProperty("totalRecords")
    private int totalRecords;

    @JsonProperty("matchedRecords")
    private int matchedRecords;

    @JsonProperty("mismatchedRecords")
    private int mismatchedRecords;

    @JsonProperty("missingInTarget")
    private int missingInTarget;

    @JsonProperty("missingInSource")
    private int missingInSource;

    @JsonProperty("results")
    private List<ComparisonResult> results;

    @JsonProperty("processedAt")
    private String processedAt;

    @JsonProperty("processingTimeMs")
    private long processingTimeMs;

    @JsonProperty("summary")
    private ComparisonSummary summary;
}