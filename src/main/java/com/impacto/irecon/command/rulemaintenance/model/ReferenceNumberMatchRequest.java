package com.impacto.irecon.command.rulemaintenance.model;


import com.impacto.irecon.common.enums.RuleConstants.ReferenceNumberMatch;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReferenceNumberMatchRequest {

    @NotNull(message = "Match type must be provided")
    private ReferenceNumberMatch matchType;

    // Applicable when matchType == FULL
    private boolean identicalInternal;
    private boolean identicalExternal;
    private boolean reportMatchAsException;

    // Applicable when matchType == PARTIAL
    @Min(value = 0, message = "Start position must be >= 0")
    @Max(value = 1000, message = "Start position seems too large")
    private Integer startPosition;

    @Min(value = 1, message = "Length must be at least 1")
    @Max(value = 1000, message = "Length seems too large")
    private Integer length;


    // ---------- VALIDATION LOGIC ----------
    @AssertTrue(message = "Start position and length are required when match type is PARTIAL")
    private boolean isPartialValid() {
        if (matchType == ReferenceNumberMatch.PARTIAL) {
            return startPosition != null && length != null;
        }
        return true;
    }

    @AssertTrue(message = "Start position and length must be null when match type is FULL")
    private boolean isFullValid() {
        if (matchType == ReferenceNumberMatch.FULL) {
            return startPosition == null && length == null;
        }
        return true;
    }

}
