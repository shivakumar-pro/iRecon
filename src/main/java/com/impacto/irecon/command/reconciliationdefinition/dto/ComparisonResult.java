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
public class ComparisonResult {
    @JsonProperty("sourceId")
    private String sourceId;

    @JsonProperty("targetId")
    private String targetId;

    @JsonProperty("status")
    private String status; // "match", "mismatch", "missing_in_target", "missing_in_source"

    @JsonProperty("matchedFields")
    private List<String> matchedFields;

    @JsonProperty("differences")
    private List<FieldDifference> differences;
}
