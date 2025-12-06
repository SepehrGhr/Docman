package dev.arusha.docman.repository;

import dev.arusha.docman.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByNameIn(Collection<String> names);

    Optional<Tag> findByName(String name);
}