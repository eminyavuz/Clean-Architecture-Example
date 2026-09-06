package com.example.clean_architecture_example.domain.exception;

/**
 * Base type for all domain-level exceptions.
 * <p>
 * This class is framework-agnostic and can be safely used
 * in the domain and application layers. Web / HTTP mapping
 * should be handled in the adapter layer.
 */
public abstract class DomainException extends RuntimeException {

    private final String code;

    protected DomainException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

