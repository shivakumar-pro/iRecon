package com.impacto.irecon.command.reconciliationdefinition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComparisonSummary {
    @JsonProperty("accuracyPercentage")
    private double accuracyPercentage;

    @JsonProperty("mostCommonDifferences")
    private List<String> mostCommonDifferences;

    @JsonProperty("fieldAccuracy")
    private Map<String, Double> fieldAccuracy;
}

