package com.impacto.irecon.command.reconciliationdefinition.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impacto.irecon.common.enums.ReconciliationType;
import com.impacto.irecon.common.enums.Status;
import com.impacto.irecon.common.enums.SyncType;
import com.impacto.irecon.common.enums.TimeFrequency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "reconciliation_definition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReconciliationDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reconciliation_id", nullable = false, unique = true)
    private String reconciliationId;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "reconciliation_type", nullable = false)
    private ReconciliationType reconciliationType;

    @Column(name = "reconciliation_config", columnDefinition = "TEXT")
    private String reconciliationConfigJson;

    @Transient
    private Map<String, Object> reconciliationConfig;

    @Column(name = "last_synced")
    private LocalDateTime lastSynced;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_type")
    private SyncType syncType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_frequency")
    private TimeFrequency frequency;

    @Column(name = "sync_start_time")
    private LocalDateTime syncStartTime;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "reconciliation_time")
    private LocalDateTime reconciliationTime;

    @Column(name = "reject_statement")
    private Boolean rejectStatement;

    @Column(name = "treat_difference_as_adjustment")
    private Boolean treatDifferenceAsAdjustment;

    @Column(name = "pass_value_date_mismatch")
    private Boolean passValueDateMismatch;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "last_modified_by", length = 100)
    private String lastModifiedBy;

    @Column(name = "last_modified_on")
    private LocalDateTime lastModifiedOn;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reconciliation_run_id")
    private ReconciliationRun reconciliationRun;

    @PostLoad
    private void loadReconciliationConfig() {
        if (reconciliationConfigJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                reconciliationConfig = mapper.readValue(reconciliationConfigJson, new TypeReference<Map<String, Object>>() {});
            } catch (JsonProcessingException e) {
                reconciliationConfig = new HashMap<>();
            }
        } else {
            reconciliationConfig = new HashMap<>();
        }
    }

    @PrePersist
    @PreUpdate
    private void onSaveOrUpdate() {
        // Handle config serialization
        if (reconciliationConfig != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                reconciliationConfigJson = mapper.writeValueAsString(reconciliationConfig);
            } catch (JsonProcessingException e) {
                reconciliationConfigJson = "{}";
            }
        } else {
            reconciliationConfigJson = "{}";
        }

        // Handle timestamps and status
        LocalDateTime now = LocalDateTime.now();
        if (createdOn == null) {
            createdOn = now;
        }
        lastModifiedOn = now;
        
        if (status == null) {
            status = Status.PENDING;
        }
    }
} 