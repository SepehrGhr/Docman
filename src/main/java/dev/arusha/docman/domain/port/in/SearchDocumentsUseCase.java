package dev.arusha.docman.domain.port.in;

import dev.arusha.docman.domain.model.Document;
import java.util.List;

public interface SearchDocumentsUseCase {
    List<Document> search(String query, String mode);
}