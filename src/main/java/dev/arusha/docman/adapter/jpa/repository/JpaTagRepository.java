package dev.arusha.docman.adapter.jpa.repository;

import dev.arusha.docman.adapter.jpa.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;

public interface JpaTagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findByNameIn(Collection<String> names);
}