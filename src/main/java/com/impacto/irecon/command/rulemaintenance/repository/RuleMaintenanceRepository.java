package com.impacto.irecon.command.rulemaintenance.repository;

import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuleMaintenanceRepository extends JpaRepository<RuleMaintenance, Long> {
    Optional<RuleMaintenance> findByRuleName(String ruleName);
    Optional<RuleMaintenance> findByRuleId(String id);
    Page<RuleMaintenance> findAll(Pageable pageable);
    Page<RuleMaintenance> findByRuleNameContainingIgnoreCase(String ruleName, Pageable pageable);
    boolean existsByRuleName(String ruleName);
}