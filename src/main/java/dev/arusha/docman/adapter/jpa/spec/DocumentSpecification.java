package dev.arusha.docman.adapter.jpa.spec;

import dev.arusha.docman.adapter.jpa.entity.DocumentEntity;
import dev.arusha.docman.adapter.jpa.entity.TagEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class DocumentSpecification {

    public static Specification<DocumentEntity> search(String query, String mode) {
        return (root, criteriaQuery, cb) -> {
            if (!StringUtils.hasText(query)) {
                return cb.conjunction();
            }
            String pattern = "%" + query.toLowerCase() + "%";
            criteriaQuery.distinct(true);

            switch (mode.toLowerCase()) {
                case "title":
                    return cb.like(cb.lower(root.get("title")), pattern);
                case "content":
                    return cb.like(cb.lower(root.get("content")), pattern);
                case "tag":
                    Join<DocumentEntity, TagEntity> tagJoin = root.join("tags", JoinType.LEFT);
                    return cb.like(cb.lower(tagJoin.get("name")), pattern);
                case "all":
                default:
                    Predicate titleMatch = cb.like(cb.lower(root.get("title")), pattern);
                    Predicate contentMatch = cb.like(cb.lower(root.get("content")), pattern);
                    Join<DocumentEntity, TagEntity> allTagsJoin = root.join("tags", JoinType.LEFT);
                    Predicate tagMatch = cb.like(cb.lower(allTagsJoin.get("name")), pattern);
                    return cb.or(titleMatch, contentMatch, tagMatch);
            }
        };
    }
}