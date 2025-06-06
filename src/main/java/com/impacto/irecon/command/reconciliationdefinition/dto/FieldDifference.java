package com.impacto.irecon.command.reconciliationdefinition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldDifference {
    @JsonProperty("field")
    private String field;

    @JsonProperty("sourceField")
    private String sourceField;

    @JsonProperty("targetField")
    private String targetField;

    @JsonProperty("sourceValue")
    private Object sourceValue;

    @JsonProperty("targetValue")
    private Object targetValue;

    @JsonProperty("variance")
    private Object variance;
}

