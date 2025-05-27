package com.impacto.irecon.command.rulemaintenance.repository;

import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleMaintenanceRepository extends JpaRepository<RuleMaintenance, UUID> {
}