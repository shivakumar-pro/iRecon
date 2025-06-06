package com.impacto.irecon.command.reconciliationdefinition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryKeyMapping {
    @JsonProperty("sourceField")
    private String sourceField;

    @JsonProperty("targetField")
    private String targetField;
}
