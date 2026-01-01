package dev.arusha.docman.domain.model;

import java.util.Objects;

public class Tag {
    private Long id;
    private TagName name;

    public Tag(Long id, TagName name) {
        this.id = id;
        this.name = name;
    }

    public Tag(TagName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TagName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}