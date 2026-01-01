package dev.arusha.docman.application.service;

import dev.arusha.docman.domain.model.*;
import dev.arusha.docman.domain.port.in.CreateDocumentUseCase;
import dev.arusha.docman.domain.port.in.SearchDocumentsUseCase;
import dev.arusha.docman.domain.port.out.DocumentRepositoryPort;
import dev.arusha.docman.domain.port.out.TagRepositoryPort;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentApplicationService implements CreateDocumentUseCase, SearchDocumentsUseCase {

    private final DocumentRepositoryPort documentRepository;
    private final TagRepositoryPort tagRepository;

    public DocumentApplicationService(DocumentRepositoryPort documentRepository, TagRepositoryPort tagRepository) {
        this.documentRepository = documentRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Document createDocument(String titleStr, String contentStr, Set<String> tagNamesStr) {
        Title title = new Title(titleStr);
        Content content = new Content(contentStr);
        Document document = new Document(title, content);

        if (tagNamesStr != null && !tagNamesStr.isEmpty()) {
            Set<TagName> tagNames = tagNamesStr.stream()
                    .map(TagName::new)
                    .collect(Collectors.toSet());

            List<Tag> existingTags = tagRepository.findByNames(tagNames);
            Set<TagName> existingTagNames = existingTags.stream()
                    .map(Tag::getName)
                    .collect(Collectors.toSet());

            List<Tag> newTags = tagNames.stream()
                    .filter(name -> !existingTagNames.contains(name))
                    .map(Tag::new)
                    .collect(Collectors.toList());
            if (!newTags.isEmpty()) {
                List<Tag> savedTags = tagRepository.saveAll(newTags);
                existingTags.addAll(savedTags);
            }
            for (Tag tag : existingTags) {
                document.addTag(tag);
            }
        }
        return documentRepository.save(document);
    }

    @Override
    public List<Document> search(String query, String mode) {
        return documentRepository.search(query, mode);
    }
}