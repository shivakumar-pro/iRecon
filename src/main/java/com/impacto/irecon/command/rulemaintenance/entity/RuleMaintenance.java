package com.impacto.irecon.command.rulemaintenance.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.impacto.irecon.common.enums.RuleConstants.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_id", nullable = false, unique = true, length = 100)
    private String ruleId;

    @Column(name = "rule_name", nullable = false, unique = true, length = 50)
    private String ruleName;

    @Column(name = "description", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "recon_type", nullable = false)
    private ReconType reconType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_type", nullable = false)
    private MatchType matchType;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_date_type", nullable = false)
    private ValueDate valueDateType;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_amount_type", nullable = false)
    private Match matchAmountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "amount_type", nullable = false)
    private Amount amountType;

    @JsonManagedReference(value = "automatic-tolerance")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "automatic_tolerance_id")
    private AmountToleranceSettings automaticToleranceSettings;

    @JsonManagedReference(value = "manual-tolerance")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "manual_tolerance_id")
    private AmountToleranceSettings manualToleranceSettings;

    @JsonManagedReference(value = "reference-number-match")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reference_number_match_id")
    private ReferenceNumberMatchEntity referenceNumberMatch;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified_by", length = 100)
    private String lastModifiedBy;

    @Column(name = "last_modified_on")
    private LocalDateTime lastModifiedOn;

    @PrePersist
    protected void onCreate() {
        if (ruleId == null) {
            ruleId = UUID.randomUUID().toString();
        }
        createdOn = LocalDateTime.now();
        lastModifiedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedOn = LocalDateTime.now();
    }
}

