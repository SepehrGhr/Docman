package dev.arusha.docman.adapter.rest.dto;

import dev.arusha.docman.domain.model.Document;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class DocumentResponse {

    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private LocalDateTime createdAt;

    public static DocumentResponse fromDomain(Document doc) {
        return DocumentResponse.builder()
                .id(doc.getId())
                .title(doc.getTitle().value())
                .content(doc.getContent().value())
                .createdAt(doc.getCreatedAt())
                .tags(doc.getTags().stream()
                        .map(tag -> tag.getName().value())
                        .sorted()
                        .collect(Collectors.toList()))
                .build();
    }
}