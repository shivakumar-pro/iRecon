package com.impacto.irecon.command.reconciliationdefinition.entity;

import com.impacto.irecon.common.enums.SyncType;
import com.impacto.irecon.common.enums.TimeFrequency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reconciliation_definition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReconciliationDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reconciliation_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "source_api_key")
    private String sourceApiKey;

    @Column(name = "source_api_secret")
    private String sourceApiSecret;

    @Column(name = "source_api_url")
    private String sourceApiUrl;

    @Column(name = "target_api_key")
    private String targetApiKey;

    @Column(name = "target_api_secret")
    private String targetApiSecret;

    @Column(name = "target_api_url")
    private String targetApiUrl;

    @Column(name = "last_synced")
    private LocalDateTime lastSynced;

    @Column(name = "sync_type")
    @Enumerated(EnumType.STRING)
    private SyncType syncType;

    @Column(name = "sync_frequency")
    @Enumerated(EnumType.STRING)
    private TimeFrequency frequency;

    @Column(name = "sync_start_time")
    private LocalDateTime syncStartTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_on")
    private LocalDateTime lastModifiedOn;

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        lastModifiedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedOn = LocalDateTime.now();
    }
} 