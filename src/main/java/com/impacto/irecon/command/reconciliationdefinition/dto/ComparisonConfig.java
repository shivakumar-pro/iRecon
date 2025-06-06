package com.impacto.irecon.command.reconciliationdefinition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonConfig {
    @JsonProperty("primaryKeyMapping")
    private PrimaryKeyMapping primaryKeyMapping;

    @JsonProperty("fieldMappings")
    private List<FieldMapping> fieldMappings;

    @JsonProperty("comparisonType")
    private String comparisonType;
}
