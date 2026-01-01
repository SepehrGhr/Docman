package dev.arusha.docman.adapter.jpa;

import dev.arusha.docman.domain.model.Document;
import dev.arusha.docman.domain.port.out.DocumentRepositoryPort;
import dev.arusha.docman.adapter.jpa.entity.DocumentEntity;
import dev.arusha.docman.adapter.jpa.mapper.JpaMapper;
import dev.arusha.docman.adapter.jpa.repository.JpaDocumentRepository;
import dev.arusha.docman.adapter.jpa.spec.DocumentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DocumentJpaAdapter implements DocumentRepositoryPort {

    private final JpaDocumentRepository jpaDocumentRepository;
    private final JpaMapper mapper;

    @Override
    public Document save(Document document) {
        DocumentEntity entity = mapper.toEntity(document);
        DocumentEntity savedEntity = jpaDocumentRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Document> search(String query, String mode) {
        List<DocumentEntity> entities = jpaDocumentRepository.findAll(
                DocumentSpecification.search(query, mode)
        );
        return entities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}