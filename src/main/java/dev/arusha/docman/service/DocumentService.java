package dev.arusha.docman.service;

import dev.arusha.docman.dto.DocumentRequestDTO;
import dev.arusha.docman.dto.DocumentResponseDTO;

import java.util.List;

public interface DocumentService {

    DocumentResponseDTO createDocument(DocumentRequestDTO request);

    List<DocumentResponseDTO> searchDocuments(String query, String mode);
}