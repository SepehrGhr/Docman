package dev.arusha.docman.adapter.rest.controller;

import dev.arusha.docman.domain.model.Document;
import dev.arusha.docman.domain.port.in.CreateDocumentUseCase;
import dev.arusha.docman.domain.port.in.SearchDocumentsUseCase;
import dev.arusha.docman.adapter.rest.dto.DocumentRequest;
import dev.arusha.docman.adapter.rest.dto.DocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Tag(name = "Documents", description = "Operations related to Document Management")
public class DocumentController {

    private final CreateDocumentUseCase createDocumentUseCase;
    private final SearchDocumentsUseCase searchDocumentsUseCase;

    @PostMapping
    @Operation(summary = "Create a new document")
    public ResponseEntity<DocumentResponse> createDocument(@Valid @RequestBody DocumentRequest request) {
        Document createdDoc = createDocumentUseCase.createDocument(
                request.getTitle(),
                request.getContent(),
                request.getTags()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DocumentResponse.fromDomain(createdDoc));
    }

    @GetMapping("/search")
    @Operation(summary = "Search documents")
    public ResponseEntity<List<DocumentResponse>> searchDocuments(
            @RequestParam String query, @RequestParam(defaultValue = "all") String mode
    ) {
        List<Document> results = searchDocumentsUseCase.search(query, mode);
        List<DocumentResponse> response = results.stream()
                .map(DocumentResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}