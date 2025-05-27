package com.impacto.irecon.command.reconciliationdefinition.repository;

import com.impacto.irecon.command.reconciliationdefinition.entity.ReconciliationDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReconciliationDefinitionRepository extends JpaRepository<ReconciliationDefinition, UUID> {
} 