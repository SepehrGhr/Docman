package dev.arusha.docman.service.impl;

import dev.arusha.docman.dto.DocumentRequestDTO;
import dev.arusha.docman.dto.DocumentResponseDTO;
import dev.arusha.docman.model.Document;
import dev.arusha.docman.model.Tag;
import dev.arusha.docman.repository.DocumentRepository;
import dev.arusha.docman.repository.TagRepository;
import dev.arusha.docman.repository.spec.DocumentSpecification;
import dev.arusha.docman.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public DocumentResponseDTO createDocument(DocumentRequestDTO request) {
        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setContent(request.getContent());

        Set<String> reqTags = request.getTags();
        if (!reqTags.isEmpty()) {
            List<Tag> existingTags = tagRepository.findByNameIn(reqTags);
            Set<String> existingTagNames = existingTags.stream()
                    .map(Tag::getName)
                    .collect(Collectors.toSet());
            List<Tag> newTags = reqTags.stream()
                    .filter(name -> !existingTagNames.contains(name))
                    .map(Tag::new)
                    .collect(Collectors.toList());
            if (!newTags.isEmpty()) {
                List<Tag> savedTags = tagRepository.saveAll(newTags);
                existingTags.addAll(savedTags);
            }

            document.setTags(new HashSet<>(existingTags));
        }
        Document savedDoc = documentRepository.save(document);
        return DocumentResponseDTO.fromEntity(savedDoc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponseDTO> searchDocuments(String query, String mode) {
        List<Document> results = documentRepository.findAll(
                DocumentSpecification.search(query, mode)
        );
        return results.stream()
                .map(DocumentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}