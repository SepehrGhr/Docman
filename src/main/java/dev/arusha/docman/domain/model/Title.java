package dev.arusha.docman.domain.model;

import dev.arusha.docman.domain.exception.DomainValidationException;

public record Title(String value) {
    public Title {
        if (value == null || value.trim().isEmpty()) {
            throw new DomainValidationException("Document title cannot be empty.");
        }
    }
}