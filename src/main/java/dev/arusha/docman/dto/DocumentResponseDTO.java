package dev.arusha.docman.dto;

import dev.arusha.docman.model.Document;
import dev.arusha.docman.model.Tag;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class DocumentResponseDTO {

    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private LocalDateTime createdAt;

    public static DocumentResponseDTO fromEntity(Document doc) {
        return DocumentResponseDTO.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .content(doc.getContent())
                .createdAt(doc.getCreatedAt())
                .tags(doc.getTags().stream()
                        .map(Tag::getName)
                        .sorted()
                        .collect(Collectors.toList()))
                .build();
    }
}