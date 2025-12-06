package dev.arusha.docman.dto;

import dev.arusha.docman.model.Document;
import dev.arusha.docman.model.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Schema(description = "Response object containing document details")
public class DocumentResponseDTO {

    @Schema(description = "Unique identifier of the document", example = "1")
    private Long id;

    @Schema(description = "The title of the document", example = "Machine Learning Intro")
    private String title;

    @Schema(description = "The content of the document", example = "Deep learning models such as XGBoost, SVM...")
    private String content;

    @Schema(description = "List of associated tags", example = "[\"AI\", \"Tech\"]")
    private List<String> tags;

    @Schema(description = "Timestamp when the document was created")
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