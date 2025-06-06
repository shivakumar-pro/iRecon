package com.impacto.irecon.command.rulemaintenance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Entity
@Table(name = "amount_tolerance_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmountToleranceSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "positive_tolerance_percent")
    private Double positiveTolerancePercent;

    @Column(name = "negative_tolerance_percent")
    private Double negativeTolerancePercent;

    @Column(name = "positive_tolerance_amount")
    private Double positiveToleranceAmount;

    @Column(name = "negative_tolerance_amount")
    private Double negativeToleranceAmount;

    @Column(name = "value_date_plus")
    private Integer valueDatePlus;

    @Column(name = "value_date_minus")
    private Integer valueDateMinus;

    @JsonBackReference(value = "automatic-tolerance")
    @ToString.Exclude
    @OneToOne(mappedBy = "automaticToleranceSettings")
    private RuleMaintenance ruleForAutomatic;

    @JsonBackReference(value = "manual-tolerance")
    @ToString.Exclude
    @OneToOne(mappedBy = "manualToleranceSettings")
    private RuleMaintenance ruleForManual;
}
