package com.impacto.irecon.command.rulemaintenance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.impacto.irecon.common.enums.RuleConstants.ReferenceNumberMatch;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Entity
@Table(name = "reference_number_match")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenceNumberMatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_type", nullable = false)
    private ReferenceNumberMatch matchType;

    @Column(name = "identical_internal")
    private Boolean identicalInternal;

    @Column(name = "identical_external")
    private Boolean identicalExternal;

    @Column(name = "start_position")
    private Integer startPosition;

    @Column(name = "length")
    private Integer length;

    @Column(name = "report_match_as_exception")
    private Boolean reportMatchAsException;

    @JsonBackReference(value = "reference-number-match")
    @ToString.Exclude
    @OneToOne(mappedBy = "referenceNumberMatch")
    private RuleMaintenance rule;
}
