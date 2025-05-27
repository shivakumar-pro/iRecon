package com.impacto.irecon.command.rulemaintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RuleSummaryResponse {
    private UUID id;
    private String name;
}

