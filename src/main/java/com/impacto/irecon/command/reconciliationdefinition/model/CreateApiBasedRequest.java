package com.impacto.irecon.command.reconciliationdefinition.model;

import lombok.Data;

@Data
public class CreateApiBasedRequest extends CreateReconciliationRequest {
    private ApiBasedRequest apiBased;
}
