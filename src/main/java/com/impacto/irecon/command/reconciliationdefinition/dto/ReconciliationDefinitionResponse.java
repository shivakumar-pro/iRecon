package com.impacto.irecon.command.reconciliationdefinition.dto;

import com.impacto.irecon.common.enums.ReconciliationType;
import com.impacto.irecon.common.enums.Status;
import com.impacto.irecon.common.enums.SyncType;
import com.impacto.irecon.common.enums.TimeFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationDefinitionResponse {
//    private Long id;
    private UUID uuid;
    private String reconciliationId;
    private String description;
    private ReconciliationType reconciliationType;
    private Map<String, Object> reconciliationConfig;
    private LocalDateTime lastSynced;
    private SyncType syncType;
    private TimeFrequency frequency;
    private LocalDateTime syncStartTime;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime reconciliationTime;
    private Boolean rejectStatement;
    private Boolean treatDifferenceAsAdjustment;
    private Boolean passValueDateMismatch;
    private Status status;
    private String createdBy;
    private LocalDateTime createdOn;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedOn;
} 