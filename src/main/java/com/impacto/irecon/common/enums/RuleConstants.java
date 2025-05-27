package com.impacto.irecon.common.enums;

public final class RuleConstants {
    private RuleConstants() {}

    public enum TransactionType {
        TWO_LEGS,
        FOUR_LEGS
    }

    public enum ReconType {
        INTERNAL,
        EXTERNAL
    }

    public enum MatchType {
        CONFIRMED,
        SUGGESTED,
        BOTH
    }

    public enum ValueDate{
        IDENTICAL,
        TOLERANCE
    }

    public enum Match{
        FULL_AMOUNT_MATCH,
        PARTIAL_AMOUNT_MATCH,
    }

    public enum Amount{
        IDENTICAL,
        TOLERANCE
    }

    public enum ReferenceNumberMatch {
        FULL,
        PARTIAL
    }


}
