package dev.arusha.docman.domain.model;

import dev.arusha.docman.domain.exception.DomainValidationException;

public record TagName(String value) {
    public TagName {
        if (value == null || value.trim().isEmpty()) {
            throw new DomainValidationException("Tag name cannot be empty.");
        }
        value = value.trim().toLowerCase();
    }
}