package de.smava.recrt.rest;

import de.smava.recrt.rest.model.ErrorResource;
import org.springframework.validation.Errors;

/**
 * Used to throw form validation exceptions
 */
public class RecrtValidationException extends RuntimeException {

    private Errors errors;

    private ErrorResource errorResource;

    public RecrtValidationException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public RecrtValidationException(ErrorResource errorResource) {
        super(errorResource.getMessage());
        this.errorResource = errorResource;
    }

    public Errors getErrors() {
        return errors;
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }
}
