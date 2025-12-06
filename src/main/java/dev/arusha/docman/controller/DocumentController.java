package dev.arusha.docman.controller;

import dev.arusha.docman.dto.DocumentRequestDTO;
import dev.arusha.docman.dto.DocumentResponseDTO;
import dev.arusha.docman.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Tag(name = "Documents", description = "Operations related to Document Management")
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "Create a new document", description = "Creates a document with tags. If tags do not exist, they are created.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Document created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input (e.g. missing title)")
    })
    @PostMapping
    public ResponseEntity<DocumentResponseDTO> createDocument(@Valid @RequestBody DocumentRequestDTO request) {
        DocumentResponseDTO response = documentService.createDocument(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Search documents", description = "Search documents by title, content, tag, or all fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully")
    })
    @GetMapping("/search")
    public ResponseEntity<List<DocumentResponseDTO>> searchDocuments(
            @Parameter(description = "The search keyword", example = "Deep Learning")
            @RequestParam String query,

            @Parameter(description = "Search mode: 'title', 'content', 'tag', or 'all'", example = "all")
            @RequestParam(defaultValue = "all") String mode
    ) {
        List<DocumentResponseDTO> results = documentService.searchDocuments(query, mode);
        return ResponseEntity.ok(results);
    }
}