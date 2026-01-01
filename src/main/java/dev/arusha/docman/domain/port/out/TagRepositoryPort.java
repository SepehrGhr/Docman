package dev.arusha.docman.domain.port.out;

import dev.arusha.docman.domain.model.Tag;
import dev.arusha.docman.domain.model.TagName;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepositoryPort {
    List<Tag> findByNames(Set<TagName> names);
    List<Tag> saveAll(List<Tag> tags);
}