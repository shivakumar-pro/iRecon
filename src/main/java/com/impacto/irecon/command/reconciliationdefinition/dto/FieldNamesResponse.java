package com.impacto.irecon.command.reconciliationdefinition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldNamesResponse {
    @JsonProperty("sourceFields")
    private Set<String> sourceFields;

    @JsonProperty("targetFields")
    private Set<String> targetFields;
} 