package dev.arusha.docman.domain.model;

import dev.arusha.docman.domain.exception.DomainValidationException;

public record Content(String value) {
    public Content {
        if (value == null || value.trim().isEmpty()) {
            throw new DomainValidationException("Document content cannot be empty.");
        }
    }
}