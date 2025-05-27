package com.impacto.irecon.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    // General Statuses
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    SUCCESS("Success"),
    FAILED("Failed"),
    ERROR("Error"),
    CANCELLED("Cancelled"),
    REJECTED("Rejected"),
    APPROVED("Approved"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold"),
    RETRY("Retry"),
    TIMEOUT("Timeout"),
    NOT_FOUND("Not Found"),

    // User or access related
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    LOCKED("Locked"),
    BLOCKED("Blocked"),
    EXPIRED("Expired"),
    VERIFIED("Verified"),
    UNVERIFIED("Unverified"),

    // Upload/Download/Sync
    UPLOADED("Uploaded"),
    DOWNLOAD_READY("Download Ready"),
    SYNCED("Synced"),

    // Rule/Policy/Workflow
    ENABLED("Enabled"),
    DISABLED("Disabled"),
    ARCHIVED("Archived"),
    DELETED("Deleted");

    private final String value;
}
