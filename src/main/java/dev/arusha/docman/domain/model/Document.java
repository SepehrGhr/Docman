package dev.arusha.docman.domain.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Document {

    private Long id;
    private Title title;
    private Content content;
    private LocalDateTime createdAt;

    private final Set<Tag> tags = new HashSet<>();

    public Document(Title title, Content content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public Document(Long id, Title title, Content content, LocalDateTime createdAt, Set<Tag> existingTags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        if (existingTags != null) {
            this.tags.addAll(existingTags);
        }
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Title getTitle() { return title; }
    public Content getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}