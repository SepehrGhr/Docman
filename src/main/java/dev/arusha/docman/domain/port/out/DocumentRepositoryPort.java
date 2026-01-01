package dev.arusha.docman.domain.port.out;

import dev.arusha.docman.domain.model.Document;
import java.util.List;

public interface DocumentRepositoryPort {
    Document save(Document document);
    List<Document> search(String query, String mode);
}