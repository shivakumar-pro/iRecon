package com.impacto.irecon.command.reconciliationdefinition.entity;

import com.impacto.irecon.common.enums.ReconciliationConstants.ReconciliationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reconciliation_run")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReconciliationRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reconciliation_id", nullable = false)
    private String reconciliationId;

    @Column(name = "reconciliation_description")
    private String reconciliationDescription;

    @Column(name = "run_start_date")
    private LocalDate runStartDate;

    @Column(name = "run_end_date")
    private LocalDate runEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReconciliationStatus status;

    @Column(name = "matched_with_tolerance_amount")
    private Boolean matchedWithToleranceAmount;

    @Column(name = "matched_manually_amount")
    private Boolean matchedManuallyAmount;

    @Column(name = "matched_with_tolerance_value_date")
    private Boolean matchedWithToleranceValueDate;

    @Column(name = "matched_manually_value_date")
    private Boolean matchedManuallyValueDate;

    @Column(name = "filter_start_date")
    private LocalDate filterStartDate;

    @Column(name = "filter_end_date")
    private LocalDate filterEndDate;

    @Column(name = "min_amount", precision = 19, scale = 4)
    private BigDecimal minAmount;

    @Column(name = "max_amount", precision = 19, scale = 4)
    private BigDecimal maxAmount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @OneToOne(mappedBy = "reconciliationRun")
    private ReconciliationDefinition reconciliationDefinition;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
