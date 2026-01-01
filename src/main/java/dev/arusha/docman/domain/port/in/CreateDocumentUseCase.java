package dev.arusha.docman.domain.port.in;

import dev.arusha.docman.domain.model.Document;
import java.util.Set;

public interface CreateDocumentUseCase {
    Document createDocument(String title, String content, Set<String> tags);
}