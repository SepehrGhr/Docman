package dev.arusha.docman.adapter.jpa.mapper;

import dev.arusha.docman.domain.model.*;
import dev.arusha.docman.adapter.jpa.entity.DocumentEntity;
import dev.arusha.docman.adapter.jpa.entity.TagEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JpaMapper {
    public Tag toDomain(TagEntity entity) {
        return new Tag(entity.getId(), new TagName(entity.getName()));
    }

    public TagEntity toEntity(Tag tag) {
        TagEntity entity = new TagEntity();
        entity.setId(tag.getId());
        entity.setName(tag.getName().value());
        return entity;
    }

    public Document toDomain(DocumentEntity entity) {
        return new Document(
                entity.getId(),
                new Title(entity.getTitle()),
                new Content(entity.getContent()),
                entity.getCreatedAt(),
                entity.getTags().stream().map(this::toDomain).collect(Collectors.toSet())
        );
    }

    public DocumentEntity toEntity(Document domain) {
        DocumentEntity entity = new DocumentEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle().value());
        entity.setContent(domain.getContent().value());
        entity.setCreatedAt(domain.getCreatedAt());
        if (domain.getTags() != null) {
            entity.setTags(domain.getTags().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toSet()));
        }
        return entity;
    }
}