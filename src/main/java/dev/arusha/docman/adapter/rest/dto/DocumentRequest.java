package dev.arusha.docman.adapter.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
public class DocumentRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private Set<String> tags;

    public Set<String> getTags() {
        return tags == null ? Collections.emptySet() : tags;
    }
}