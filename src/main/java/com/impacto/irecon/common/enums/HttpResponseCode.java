package com.impacto.irecon.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HttpResponseCode {

    // --- 1xx Informational ---
    CONTINUE(100, "Continue", HttpStatus.CONTINUE),
    SWITCHING_PROTOCOLS(101, "Switching Protocols", HttpStatus.SWITCHING_PROTOCOLS),
    PROCESSING(102, "Processing", HttpStatus.PROCESSING),

    // --- 2xx Success ---
    SUCCESS(200, "Success", HttpStatus.OK),
    OK(200, "OK", HttpStatus.OK),
    CREATED(201, "Created Successfully", HttpStatus.CREATED),
    ACCEPTED(202, "Accepted", HttpStatus.ACCEPTED),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information", HttpStatus.NON_AUTHORITATIVE_INFORMATION),
    NO_CONTENT(204, "No Content", HttpStatus.NO_CONTENT),
    RESET_CONTENT(205, "Reset Content", HttpStatus.RESET_CONTENT),
    PARTIAL_CONTENT(206, "Partial Content", HttpStatus.PARTIAL_CONTENT),

    // --- 3xx Redirection ---
    MULTIPLE_CHOICES(300, "Multiple Choices", HttpStatus.MULTIPLE_CHOICES),
    MOVED_PERMANENTLY(301, "Moved Permanently", HttpStatus.MOVED_PERMANENTLY),
    FOUND(302, "Found", HttpStatus.FOUND),
    SEE_OTHER(303, "See Other", HttpStatus.SEE_OTHER),
    NOT_MODIFIED(304, "Not Modified", HttpStatus.NOT_MODIFIED),
    TEMPORARY_REDIRECT(307, "Temporary Redirect", HttpStatus.TEMPORARY_REDIRECT),
    PERMANENT_REDIRECT(308, "Permanent Redirect", HttpStatus.PERMANENT_REDIRECT),

    // --- 4xx Client Errors ---
    BAD_REQUEST(400, "Bad Request", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    PAYMENT_REQUIRED(402, "Payment Required", HttpStatus.PAYMENT_REQUIRED),
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN),
    NOT_FOUND(404, "Resource Not Found", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED),
    NOT_ACCEPTABLE(406, "Not Acceptable", HttpStatus.NOT_ACCEPTABLE),
    CONFLICT(409, "Resource Already Exists", HttpStatus.CONFLICT),
    GONE(410, "Gone", HttpStatus.GONE),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    PRECONDITION_FAILED(412, "Precondition Failed", HttpStatus.PRECONDITION_FAILED),
    UNPROCESSABLE_ENTITY(422, "Validation Failed", HttpStatus.UNPROCESSABLE_ENTITY),

    // --- 5xx Server Errors ---
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_IMPLEMENTED(501, "Not Implemented", HttpStatus.NOT_IMPLEMENTED),
    BAD_GATEWAY(502, "Bad Gateway", HttpStatus.BAD_GATEWAY),
    SERVICE_UNAVAILABLE(503, "Service Unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    GATEWAY_TIMEOUT(504, "Gateway Timeout", HttpStatus.GATEWAY_TIMEOUT),

    // --- Custom Business Error Codes (1000-1999) ---
    RULE_NOT_FOUND(1001, "Rule Not Found", HttpStatus.NOT_FOUND),
    RULE_ALREADY_EXISTS(1002, "Rule Already Exists", HttpStatus.CONFLICT),
    INVALID_RULE_DATA(1003, "Invalid Rule Data", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(1004, "Resource Not Found", HttpStatus.NOT_FOUND),
    BUSINESS_RULE_VIOLATION(1005, "Business Rule Violation", HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT(1006, "Invalid Argument", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(1007, "Validation Error", HttpStatus.BAD_REQUEST),
    BUSINESS_RULE_FAILURE(1008, "Business Rule Failure", HttpStatus.BAD_REQUEST),

    // --- Validation Error Codes (2000-2999) ---
    VALIDATION_FAILED(2001, "Validation Failed", HttpStatus.BAD_REQUEST),
    INVALID_INPUT(2002, "Invalid Input Data", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD(2003, "Missing Required Field", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT(2004, "Invalid Data Format", HttpStatus.BAD_REQUEST),

    // --- Database Error Codes (3000-3999) ---
    DATABASE_ERROR(3001, "Database Error", HttpStatus.INTERNAL_SERVER_ERROR),
    DATA_INTEGRITY_VIOLATION(3002, "Data Integrity Violation", HttpStatus.CONFLICT),
    TRANSACTION_FAILED(3003, "Transaction Failed", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    HttpResponseCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
