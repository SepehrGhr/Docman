package dev.arusha.docman.controller;

import dev.arusha.docman.dto.DocumentRequestDTO;
import dev.arusha.docman.dto.DocumentResponseDTO;
import dev.arusha.docman.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentResponseDTO> createDocument(@Valid @RequestBody DocumentRequestDTO request) {
        DocumentResponseDTO response = documentService.createDocument(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DocumentResponseDTO>> searchDocuments(
            @RequestParam String query,
            @RequestParam(defaultValue = "all") String mode
    ) {
        List<DocumentResponseDTO> results = documentService.searchDocuments(query, mode);
        return ResponseEntity.ok(results);
    }
}