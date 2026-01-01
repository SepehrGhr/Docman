package dev.arusha.docman.adapter.jpa;

import dev.arusha.docman.domain.model.Tag;
import dev.arusha.docman.domain.model.TagName;
import dev.arusha.docman.domain.port.out.TagRepositoryPort;
import dev.arusha.docman.adapter.jpa.entity.TagEntity;
import dev.arusha.docman.adapter.jpa.mapper.JpaMapper;
import dev.arusha.docman.adapter.jpa.repository.JpaTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagJpaAdapter implements TagRepositoryPort {

    private final JpaTagRepository jpaTagRepository;
    private final JpaMapper mapper;

    @Override
    public List<Tag> findByNames(Set<TagName> names) {
        List<String> rawNames = names.stream()
                .map(TagName::value)
                .collect(Collectors.toList());

        return jpaTagRepository.findByNameIn(rawNames).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tag> saveAll(List<Tag> tags) {
        List<TagEntity> entities = tags.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        return jpaTagRepository.saveAll(entities).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}