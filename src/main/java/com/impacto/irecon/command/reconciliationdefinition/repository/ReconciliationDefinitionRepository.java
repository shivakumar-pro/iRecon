package com.impacto.irecon.command.reconciliationdefinition.repository;

import com.impacto.irecon.command.reconciliationdefinition.entity.ReconciliationDefinition;
import com.impacto.irecon.common.enums.ReconciliationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReconciliationDefinitionRepository extends JpaRepository<ReconciliationDefinition, Long> {
    Optional<ReconciliationDefinition> findByReconciliationId(String reconciliationId);
    List<ReconciliationDefinition> findByReconciliationType(ReconciliationType type);
    Page<ReconciliationDefinition> findByReconciliationType(ReconciliationType type, Pageable pageable);
    boolean existsByReconciliationId(String reconciliationId);
    long countByReconciliationType(ReconciliationType type);
} 