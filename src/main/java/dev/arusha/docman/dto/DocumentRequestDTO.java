package dev.arusha.docman.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@Schema(description = "Payload for creating a new document")
public class DocumentRequestDTO {

    @NotBlank(message = "Title is required")
    @Schema(description = "The title of the document", example = "Machine Learning Intro")
    private String title;

    @NotBlank(message = "Content is required")
    @Schema(description = "The main body content of the document", example = "Deep learning models such as XGBoost, SVM...")
    private String content;

    @Schema(description = "List of tags associated with the document. New tags will be created automatically.", example = "[\"AI\", \"Machine Learning\"]")
    private Set<String> tags;

    public Set<String> getTags() {
        return tags == null ? Collections.emptySet() : tags;
    }
}