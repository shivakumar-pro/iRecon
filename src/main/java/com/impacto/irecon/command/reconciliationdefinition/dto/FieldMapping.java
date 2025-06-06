package com.impacto.irecon.command.reconciliationdefinition.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.impacto.irecon.command.rulemaintenance.model.ReferenceNumberMatchRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldMapping {
    @JsonProperty("sourceField")
    private String sourceField;

    @JsonProperty("targetField")
    private String targetField;

//    @JsonProperty("usePartialMatch")
//    private boolean usePartialMatch;
//
//    @JsonProperty("startPosition")
//    @Min(value = 0, message = "Start position must be >= 0")
//    @Max(value = 1000, message = "Start position seems too large")
//    private Integer startPosition;
//
//    @JsonProperty("length")
//    @Min(value = 1, message = "Length must be at least 1")
//    @Max(value = 1000, message = "Length seems too large")
//    private Integer length;

    private ReferenceNumberMatchRequest referenceNumberMatch;
}

