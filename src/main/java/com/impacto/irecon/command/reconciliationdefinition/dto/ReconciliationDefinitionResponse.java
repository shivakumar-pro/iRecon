package com.impacto.irecon.command.reconciliationdefinition.dto;

import com.impacto.irecon.common.enums.SyncType;
import com.impacto.irecon.common.enums.TimeFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationDefinitionResponse {
    private UUID id;
    private String description;
    private String sourceApiUrl;
    private String targetApiUrl;
    private LocalDateTime lastSynced;
    private SyncType syncType;
    private TimeFrequency frequency;
    private LocalDateTime syncStartTime;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime lastModifiedOn;
    private String lastModifiedBy;
} 