package com.impacto.irecon.common.enums;

public final class ReconciliationConstants {

    public enum ExternalBalanceAction {
        REJECT_STATEMENT,
        ADJUSTMENT_ENTRY
    }

    public enum ReconciliationStatus {
        CONFIRMED,
        UN_RECONCILED,
        SUSPICIOUS,
        MANUAL_MATCHED,
        SUGGESTED
    }


}
