package com.impacto.irecon.command.reconciliationdefinition.model;

import lombok.Data;

@Data
public class CreateEmailStatementRequest extends CreateReconciliationRequest {
    private EmailStatementRequest emailStatement;
}
