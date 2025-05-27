package com.impacto.irecon.command.rulemaintenance.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rule_maintenance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rule_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "rule_name", nullable = false)
    private String ruleName;

    @Column(name = "description")
    private String description;

    @Column(name = "recon_type", nullable = false)
    private String reconType;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "match_type", nullable = false)
    private String matchType;

    @Column(name = "value_date_type", nullable = false)
    private String valueDateType;

    @Column(name = "match_amount_type", nullable = false)
    private String matchAmountType;

    @Column(name = "amount_type", nullable = false)
    private String amountType;

    // Automatic tolerance details
    @Column(name = "auto_currency")
    private String autoCurrency;

    @Column(name = "auto_positive_tolerance_percent")
    private Double autoPositiveTolerancePercent;

    @Column(name = "auto_negative_tolerance_percent")
    private Double autoNegativeTolerancePercent;

    @Column(name = "auto_positive_tolerance_amount")
    private Double autoPositiveToleranceAmount;

    @Column(name = "auto_negative_tolerance_amount")
    private Double autoNegativeToleranceAmount;

    @Column(name = "auto_value_date_plus")
    private Integer autoValueDatePlus;

    @Column(name = "auto_value_date_minus")
    private Integer autoValueDateMinus;

    // Manual tolerance details
    @Column(name = "manual_currency")
    private String manualCurrency;

    @Column(name = "manual_positive_tolerance_percent")
    private Double manualPositiveTolerancePercent;

    @Column(name = "manual_negative_tolerance_percent")
    private Double manualNegativeTolerancePercent;

    @Column(name = "manual_positive_tolerance_amount")
    private Double manualPositiveToleranceAmount;

    @Column(name = "manual_negative_tolerance_amount")
    private Double manualNegativeToleranceAmount;

    @Column(name = "manual_value_date_plus")
    private Integer manualValueDatePlus;

    @Column(name = "manual_value_date_minus")
    private Integer manualValueDateMinus;

    // Reference number details
    @Column(name = "reference_number_match_type")
    private String referenceNumberMatchType;

    @Column(name = "identical_internal")
    private Boolean identicalInternal;

    @Column(name = "identical_external")
    private Boolean identicalExternal;

    @Column(name = "reference_start_position")
    private Integer referenceStartPosition;

    @Column(name = "reference_length")
    private Integer referenceLength;

    @Column(name = "report_match_as_exception")
    private Boolean reportMatchAsException;

    // Audit fields
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_on")
    private LocalDateTime lastModifiedOn;

    @PrePersist
    public void onCreate() {
        createdOn = LocalDateTime.now();
        lastModifiedOn = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        lastModifiedOn = LocalDateTime.now();
    }
}
