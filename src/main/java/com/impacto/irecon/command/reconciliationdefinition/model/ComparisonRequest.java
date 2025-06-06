package com.impacto.irecon.command.reconciliationdefinition.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.impacto.irecon.command.reconciliationdefinition.dto.ComparisonConfig;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonRequest {
    @JsonProperty("sourceData")
    private List<Map<String, Object>> sourceData;

    @JsonProperty("targetData")
    private List<Map<String, Object>> targetData;

    @JsonProperty("fieldMapping")
    private Map<String, String> fieldMapping;

    @JsonProperty("comparisonConfig")
    private ComparisonConfig comparisonConfig;
}

