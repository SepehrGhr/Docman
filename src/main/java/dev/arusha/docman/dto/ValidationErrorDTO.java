package dev.arusha.docman.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidationErrorDTO {
    private String field;
    private String message;
}