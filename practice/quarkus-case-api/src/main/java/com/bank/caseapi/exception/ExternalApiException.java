package com.bank.caseapi.exception;

/**
 * Raised whenever the call to the external Pega case API fails (non-2xx
 * response, timeout, connection error, etc). Carries the upstream HTTP
 * status code (when known) so the mapper can decide how to represent it
 * to our own callers.
 */
public class ExternalApiException extends RuntimeException {

    private final int statusCode;

    public ExternalApiException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public ExternalApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
